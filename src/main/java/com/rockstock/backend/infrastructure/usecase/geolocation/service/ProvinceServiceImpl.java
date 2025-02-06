package com.rockstock.backend.infrastructure.usecase.geolocation.service;

import com.rockstock.backend.entity.geolocation.Province;
import com.rockstock.backend.infrastructure.usecase.geolocation.dto.ProvinceResponse;
import com.rockstock.backend.infrastructure.usecase.geolocation.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Override
    public List<ProvinceResponse> getAllProvinces() {
        return provinceRepository.findAll()
                .stream()
                .map(province -> new ProvinceResponse(province.getId(), province.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public ProvinceResponse createProvince(CreateProvinceRequest request) {
        Province province = new Province();
        province.setName(request.getName());

        Province savedProvince = provinceRepository.save(province);
        return new ProvinceResponse(savedProvince.getId(), savedProvince.getName());
    }
}
