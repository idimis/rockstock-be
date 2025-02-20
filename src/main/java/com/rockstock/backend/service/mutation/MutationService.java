package com.rockstock.backend.service.mutation;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.stock.MutationJournal;
import com.rockstock.backend.entity.stock.WarehouseStock;
import com.rockstock.backend.entity.warehouse.Warehouse;
import com.rockstock.backend.infrastructure.mutation.dto.MutationRequestDTO;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.warehouseStock.repository.WarehouseStockRepository;
import com.rockstock.backend.infrastructure.mutation.repository.StockChangeTypeRepository;
import com.rockstock.backend.infrastructure.mutationJournal.repository.MutationJournalRepository;
import com.rockstock.backend.infrastructure.mutation.repository.MutationStatusRepository;
import com.rockstock.backend.infrastructure.warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MutationService {

    private final WarehouseStockRepository warehouseStockRepository;
    private final MutationJournalRepository mutationJournalRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockChangeTypeRepository stockChangeTypeRepository;
    private final MutationStatusRepository mutationStatusRepository;

    public void mutationRequestService(MutationRequestDTO requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Warehouse originWarehouse = warehouseRepository.findById(requestDTO.getOriginWarehouseId())
                .orElseThrow(() -> new RuntimeException("Origin warehouse not found"));

        Warehouse destinationWarehouse = warehouseRepository.findById(requestDTO.getDestinationWarehouseId())
                .orElseThrow(() -> new RuntimeException("Destination warehouse not found"));

        WarehouseStock originStock = warehouseStockRepository.findByProductAndWarehouse(product, originWarehouse)
                .orElseThrow(() -> new RuntimeException("Stock not found in origin warehouse"));


        if (originStock.getStockQuantity() < requestDTO.getStockQuantity()) {
            throw new RuntimeException("Not enough stock in origin warehouse");
        }


        MutationJournal journal = new MutationJournal();
        journal.setWarehouseStock(originStock);
        journal.setMutationQuantity(requestDTO.getStockQuantity());
        journal.setPreviousStockQuantity(originStock.getStockQuantity());
        journal.setNewStockQuantity(originStock.getStockQuantity() - requestDTO.getStockQuantity());
        journal.setOriginWarehouse(originWarehouse);
        journal.setDestinationWarehouse(destinationWarehouse);
        journal.setStockChangeType(stockChangeTypeRepository.findByChangeType("TRANSFER")
                .orElseThrow(() -> new RuntimeException("Stock change type not found")));
        journal.setMutationStatus(mutationStatusRepository.findByStatus("PENDING")
                .orElseThrow(() -> new RuntimeException("Stock status not found")));

        mutationJournalRepository.save(journal);
    }

    public void processMutationRequestService(Long journalId, boolean isApproved) {
        MutationJournal journal = mutationJournalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Stock journal not found"));

        WarehouseStock originStock = warehouseStockRepository.findByProductAndWarehouse(
                        journal.getWarehouseStock().getProduct(), journal.getOriginWarehouse())
                .orElseThrow(() -> new RuntimeException("Stock not found in origin warehouse"));


        if (originStock.getStockQuantity() < journal.getMutationQuantity()) {
            throw new RuntimeException("Not enough stock in origin warehouse");
        }

        if (!journal.getMutationStatus().getStatus().equals("PENDING")) {
            throw new RuntimeException("Stock mutation is not pending");
        }

        if (isApproved) {


            WarehouseStock destinationStock = warehouseStockRepository.findByProductAndWarehouse(
                            journal.getWarehouseStock().getProduct(), journal.getDestinationWarehouse())
                    .orElseGet(() -> {
                        WarehouseStock newStock = new WarehouseStock();
                        newStock.setProduct(journal.getWarehouseStock().getProduct());
                        newStock.setWarehouse(journal.getDestinationWarehouse());
                        newStock.setStockQuantity(0L);
                        return warehouseStockRepository.save(newStock);
                    });


            originStock.setStockQuantity(originStock.getStockQuantity() - journal.getMutationQuantity());
            destinationStock.setStockQuantity(destinationStock.getStockQuantity() + journal.getMutationQuantity());

            warehouseStockRepository.save(originStock);
            warehouseStockRepository.save(destinationStock);


            journal.setMutationStatus(mutationStatusRepository.findByStatus("COMPLETED")
                    .orElseThrow(() -> new RuntimeException("Stock status not found")));
        } else {

            journal.setMutationStatus(mutationStatusRepository.findByStatus("CANCELED")
                    .orElseThrow(() -> new RuntimeException("Stock status not found")));
        }

        mutationJournalRepository.save(journal);
    }
}