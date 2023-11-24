package com.example.core.service;

import com.example.core.entity.ProductType;
import com.example.core.exceptions.DuplicateException;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.ProductTypeRepository;
import com.example.core.repository.RoleRepository;
import com.example.core.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private FtpService ftpService;
    @Value("${ftp.icon.folder}")
    private String workFolder = "icon";


    public ProductType create(Long id, String name, String description, boolean status, MultipartFile icon) throws IOException {
        if (productTypeRepository.existsByNameAndId(name, id) > 0) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate product type: " + name);
        }
        // if id not null => update
        if (id != null) {
            ProductType oldProductType = productTypeRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(HttpStatus.NOT_FOUND, "not found product type with id: " + id)
            );
            //delete old icon
            ftpService.deleteFile(workFolder, oldProductType.getIcon());
            String fileName = System.currentTimeMillis() + icon.getOriginalFilename();
            boolean upFileSuccess = ftpService.uploadFile(workFolder, icon, fileName);
            if (!upFileSuccess) {
                throw new IOException("cant not upload icon file ");
            }
            oldProductType.setIcon(fileName);
            oldProductType.setUpdateDatetime(new Date());
            oldProductType.setUpdateUser(UserUtil.getUserId());
            oldProductType.setName(name);
            oldProductType.setStatus(status);
            oldProductType.setDescription(description);
            return productTypeRepository.save(oldProductType);
        }
        String fileName = System.currentTimeMillis() + icon.getOriginalFilename();
        boolean upFileSuccess = ftpService.uploadFile(workFolder, icon, fileName);
        if (!upFileSuccess) {
            throw new IOException("cant not upload icon file ");
        }
        ProductType productType = ProductType.builder().createUser(UserUtil.getUserId()).name(name).
                createDatetime(new Date()).status(status).description(description).icon(fileName)
                .build();
        return productTypeRepository.save(productType);

    }
}
