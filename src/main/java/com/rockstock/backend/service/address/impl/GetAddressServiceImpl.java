package com.rockstock.backend.service.address.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.geolocation.SubDistrict;
import com.rockstock.backend.infrastructure.address.repository.AddressRepository;
import com.rockstock.backend.service.address.GetAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAddressServiceImpl implements GetAddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public List<Address> getAddressesByUserId(Long userId) {
        List<Address> userAddresses = addressRepository.findByUserId(userId);
        if (userAddresses.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return userAddresses;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndProvinceId(Long userId, Long provinceId) {
        List<Address> addressesByProvinceId = addressRepository.findByUserIdAndProvinceId(userId, provinceId);
        if (addressesByProvinceId.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByProvinceId;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndProvinceName(Long userId, String name) {
        List<Address> addressesByProvinceName = addressRepository.findByUserIdAndProvinceName(userId, name);
        if (addressesByProvinceName.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByProvinceName;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndCityId(Long userId, Long cityId) {
        List<Address> addressesByCityId = addressRepository.findByUserIdAndCityId(userId, cityId);
        if (addressesByCityId.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByCityId;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndCityName(Long userId, String name) {
        List<Address> addressesByCityName = addressRepository.findByUserIdAndCityName(userId, name);
        if (addressesByCityName.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByCityName;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndDistrictId(Long userId, Long districtId) {
        List<Address> addressesByDistrictId = addressRepository.findByUserIdAndDistrictId(userId, districtId);
        if (addressesByDistrictId.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByDistrictId;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndDistrictName(Long userId, String name) {
        List<Address> addressesByDistrictName = addressRepository.findByUserIdAndDistrictName(userId, name);
        if (addressesByDistrictName.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesByDistrictName;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndSubDistrictId(Long userId, Long subDistrictId) {
        List<Address> addressesBySubDistrictId = addressRepository.findByUserIdAndSubDistrictId(userId, subDistrictId);
        if (addressesBySubDistrictId.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesBySubDistrictId;
    }

    @Override
    @Transactional
    public List<Address> getAddressesByUserIdAndSubDistrictName(Long userId, String name) {
        List<Address> addressesBySubDistrictName = addressRepository.findByUserIdAndSubDistrictName(userId, name);
        if (addressesBySubDistrictName.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return addressesBySubDistrictName;
    }

    @Override
    @Transactional
    public Optional<Address> getAddressByUserIdAndAddressId(Long userId, Long addressId) {
        Optional<Address> userAddress = addressRepository.findByUserIdAndAddressId(userId, addressId);
        if (userAddress.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return userAddress;
    }

    @Override
    @Transactional
    public Optional<Address> getAddressByUserIdAndLabel(Long userId, String label) {
        Optional<Address> userAddressByLabel = addressRepository.findByUserIdAndLabel(userId, label);
        if (userAddressByLabel.isEmpty()){
            throw new DataNotFoundException("Address not found !");
        }
        return userAddressByLabel;
    }

    @Override
    @Transactional
    public Optional<Address> getMainAddressByUserId(Long userId) {
        Optional<Address> userMainAddress = addressRepository.findByUserIdAndIsMainTrue(userId);
        if (userMainAddress.isEmpty()){
            throw new DataNotFoundException("Main address not found ! or User still do not have any address !");
        }
        return userMainAddress;
    }

    @Override
    @Transactional
    public List<Address> getAllDeletedAddressesByUserId(Long userId) {
        List<Address> deletedAddresses = addressRepository.findAllDeletedAddressesByUserId(userId);
        if (deletedAddresses.isEmpty()) {
            throw new DataNotFoundException("No deleted addresses found!");
        }
        return deletedAddresses;
    }
}
