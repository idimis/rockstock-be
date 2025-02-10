package com.rockstock.backend.usecase.stock;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.stock.Stock;
import com.rockstock.backend.entity.stock.StockJournal;
import com.rockstock.backend.entity.warehouse.Warehouse;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.stock.dto.StockTransferRequestDTO;
import com.rockstock.backend.infrastructure.stock.repository.StockRepository;
import com.rockstock.backend.infrastructure.stockChangeType.repository.StockChangeTypeRepository;
import com.rockstock.backend.infrastructure.stockJournal.repository.StockJournalRepository;
import com.rockstock.backend.infrastructure.stockStatus.repository.StockStatusRepository;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockJournalRepository stockJournalRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockChangeTypeRepository stockChangeTypeRepository;
    private final StockStatusRepository stockStatusRepository;

    public void transferStockService(StockTransferRequestDTO requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Warehouse originWarehouse = warehouseRepository.findById(requestDTO.getOriginWarehouseId())
                .orElseThrow(() -> new RuntimeException("Origin warehouse not found"));

        Warehouse destinationWarehouse = warehouseRepository.findById(requestDTO.getDestinationWarehouseId())
                .orElseThrow(() -> new RuntimeException("Destination warehouse not found"));

        Stock originStock = stockRepository.findByProductAndWarehouse(product, originWarehouse)
                .orElseThrow(() -> new RuntimeException("Stock not found in origin warehouse"));

        // Cek stok cukup atau tidak
        if (originStock.getStockQuantity() < requestDTO.getStockQuantity()) {
            throw new RuntimeException("Not enough stock in origin warehouse");
        }

        // Simpan transaksi di journal, tetapi stok belum diubah
        StockJournal journal = new StockJournal();
        journal.setStock(originStock); // Simpan stok yang diminta
        journal.setMutationQuantity(requestDTO.getStockQuantity());
        journal.setPreviousStockQuantity(originStock.getStockQuantity());
        journal.setNewStockQuantity(originStock.getStockQuantity() - requestDTO.getStockQuantity()); // Perubahan stok nanti
        journal.setOriginWarehouse(originWarehouse);
        journal.setDestinationWarehouse(destinationWarehouse);
        journal.setStockChangeType(stockChangeTypeRepository.findByChangeType("TRANSFER")
                .orElseThrow(() -> new RuntimeException("Stock change type not found")));
        journal.setStockStatus(stockStatusRepository.findByStatus("PENDING")
                .orElseThrow(() -> new RuntimeException("Stock status not found")));

        stockJournalRepository.save(journal);
    }

    public void processStockMutationService(Long journalId, boolean isApproved) {
        StockJournal journal = stockJournalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Stock journal not found"));.

        Stock originStock = stockRepository.findByProductAndWarehouse(
                        journal.getStock().getProduct(), journal.getOriginWarehouse())
                .orElseThrow(() -> new RuntimeException("Stock not found in origin warehouse"));

        // Pastikan stok masih cukup sebelum dipindahkan
        if (originStock.getStockQuantity() < journal.getMutationQuantity()) {
            throw new RuntimeException("Not enough stock in origin warehouse");
        }

        if (!journal.getStockStatus().getStatus().equals("PENDING")) {
            throw new RuntimeException("Stock mutation is not pending");
        }

        if (isApproved) {
            // Dapatkan stok dari warehouse asal & tujuan

            Stock destinationStock = stockRepository.findByProductAndWarehouse(
                            journal.getStock().getProduct(), journal.getDestinationWarehouse())
                    .orElseGet(() -> {
                        Stock newStock = new Stock();
                        newStock.setProduct(journal.getStock().getProduct());
                        newStock.setWarehouse(journal.getDestinationWarehouse());
                        newStock.setStockQuantity(0L);
                        return stockRepository.save(newStock);
                    });

            // Ubah stok di warehouse asal & tujuan
            originStock.setStockQuantity(originStock.getStockQuantity() - journal.getMutationQuantity());
            destinationStock.setStockQuantity(destinationStock.getStockQuantity() + journal.getMutationQuantity());

            stockRepository.save(originStock);
            stockRepository.save(destinationStock);

            // Ubah status jurnal menjadi "COMPLETED"
            journal.setStockStatus(stockStatusRepository.findByStatus("COMPLETED")
                    .orElseThrow(() -> new RuntimeException("Stock status not found")));
        } else {
            // Jika dibatalkan, ubah status menjadi "CANCELED"
            journal.setStockStatus(stockStatusRepository.findByStatus("CANCELED")
                    .orElseThrow(() -> new RuntimeException("Stock status not found")));
        }

        stockJournalRepository.save(journal);
    }
}