package com.example.core.service;

import com.example.core.dto.AreaDTO;
import com.example.core.entity.Area;
import com.example.core.repository.AreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<AreaDTO> getArea(String arg)  {
        List<Area> areas = areaRepository.getArea(arg);
        return areas.stream().map(area -> modelMapper.map(area, AreaDTO.class))
                .collect(Collectors.toList());
    }

}
