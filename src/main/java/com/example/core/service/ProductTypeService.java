package com.example.core.service;

import com.example.core.entity.ProductType;
import com.example.core.exceptions.DuplicateException;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.ProductTypeRepository;
import com.example.core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private FtpService ftpService;

    private String remoteDirPath = "/home/hungdz/ftp";

    public ProductType create(Long id, String name, MultipartFile icon) throws IOException {
        // if id not null => update
        if (productTypeRepository.existsByNameAndId(name, id) > 0) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate product type: " + name);
        }

        boolean changeDirSuccess = ftpService.getFtpClient().changeWorkingDirectory(remoteDirPath);
        if (changeDirSuccess) {
            System.out.println("Changed working directory to: " + remoteDirPath);
        } else {
            System.out.println("Failed to change working directory.");
        }
        if (id != null) {
            ProductType oldProductType = productTypeRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(HttpStatus.NOT_FOUND, "not found product type with id: " + id)
            );

        }

//        ProductType productTypeToSave = ProductType.builder().icon("sht").name(name).build();
//        ProductType.
//                productTypeRepository.save(P)

        return null;
    }
}
