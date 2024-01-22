package com.example.core.service;

import com.example.core.dto.AddressDTO;
import com.example.core.entity.Address;
import com.example.core.entity.Area;
import com.example.core.repository.AddressRepository;
import com.example.core.repository.AreaRepository;
import com.example.core.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Address> saveOrUpdate(Address address) {
        addressRepository.save(address);
        return addressRepository.findAddressByUserId(address.getUserId());
    }

    public List<AddressDTO> getByUserId(Long userId) {
        if (Utils.isNull(userId))
            return null;
        List<Address> addresses = addressRepository.findAddressByUserId(userId);
        List<AddressDTO> addressDTOS = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class)).collect(Collectors.toList());
        addressDTOS.forEach(addressDTO -> {
            Area area = areaRepository.findByAreaCode(addressDTO.getAreaCode());
            addressDTO.setProvince(area.getProvince());
            addressDTO.setDistrict(area.getDistrict());
            addressDTO.setFullName(area.getFullName());
            addressDTO.setPrecinct(area.getDistrict());
            addressDTO.setStreetBlock(area.getStreetBlock());
        });
        return  addressDTOS;
    }
}
