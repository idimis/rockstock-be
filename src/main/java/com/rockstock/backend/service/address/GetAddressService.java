package com.rockstock.backend.service.address;

import com.rockstock.backend.entity.geolocation.Address;
import com.rockstock.backend.entity.geolocation.SubDistrict;

import java.util.List;
import java.util.Optional;

public interface GetAddressService {
    List<Address> getAddressesByUserId(Long userId);
    List<Address> getAddressesByUserIdAndProvinceId(Long userId, Long provinceId);
    List<Address> getAddressesByUserIdAndProvinceName(Long userId, String name);
    List<Address> getAddressesByUserIdAndCityId(Long userId, Long cityId);
    List<Address> getAddressesByUserIdAndCityName(Long userId, String name);
    List<Address> getAddressesByUserIdAndDistrictId(Long userId, Long districtId);
    List<Address> getAddressesByUserIdAndDistrictName(Long userId, String name);
    List<Address> getAddressesByUserIdAndSubDistrictId(Long userId, Long subDistrictId);
    List<Address> getAddressesByUserIdAndSubDistrictName(Long userId, String name);
    Optional<Address> getAddressByUserIdAndAddressId(Long userId, Long addressId);
    Optional<Address> getAddressByUserIdAndLabel(Long userId, String label);
    Optional<Address> getMainAddressByUserId(Long userId);
    List<Address> getAllDeletedAddressesByUserId(Long userId);
}
