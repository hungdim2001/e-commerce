package com.example.core.service;

import com.example.core.entity.Address;
import com.example.core.repository.AddressRepository;
import com.example.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> saveOrUpdate(Address address) {
        addressRepository.save(address);
        return addressRepository.findAddressByUserId(address.getUserId());
    }

    public List<Address> getByUserId(Long userId) {
        if (Utils.isNull(userId))
            return null;
        return addressRepository.findAddressByUserId(userId);
    }
}
