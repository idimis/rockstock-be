package com.rockstock.backend.service.geolocation.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.Province;
import com.rockstock.backend.infrastructure.geolocation.dto.GetProvinceResponseDTO;
import com.rockstock.backend.infrastructure.geolocation.repository.ProvinceRepository;
import com.rockstock.backend.service.geolocation.GetProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetProvinceServiceImpl implements GetProvinceService {
    private final ProvinceRepository provinceRepository;

    @Override
    public List<GetProvinceResponseDTO> getProvinceByName(String name) {
        List<Province> foundProvinces = provinceRepository.findByNameContainingIgnoreCase(name);
        if (foundProvinces.isEmpty()){
            throw new DataNotFoundException("Province(s) with query " + name + " not found !");
        }
        return foundProvinces.stream().map(GetProvinceResponseDTO::new).toList();
    }

    @Override
    public List<GetProvinceResponseDTO> getAllProvinces() {
        return provinceRepository.findAll()
                .stream()
                .map(province -> new GetProvinceResponseDTO(province.getId(), province.getName()))
                .collect(Collectors.toList());
    }


}
