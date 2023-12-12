package com.example.core.service;

import com.example.core.dto.ProductDTO;
import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.*;
import com.example.core.exceptions.IllegalArgumentException;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.*;
import com.example.core.util.UserUtil;
import com.example.core.util.Utils;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSpecCharUseRepository productSpecCharUseRepository;
    @Autowired
    private ProductCharUseRepository productCharUseRepository;

    @Autowired
    private ProductSpecCharRepository productSpecCharRepository;
    @Autowired
    private ProductCharValueRepository productCharValueRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private FtpService ftpService;
    @Value("${ftp.product.images}")
    private String imagesFolder;
    @Value("${ftp.product.thumbnail}")
    private String thumbnailFolder;
    @Value("${api.file.endpoint}")
    private String apiFileEndpoint;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO create(Long id,
                             MultipartFile thumbnail,
                             List<MultipartFile> images,
                             Long productTypeId,
                             String name,
                             Long quantity,
                             Long price,
                             Boolean status,
                             String description,
                             List<ProductSpecCharValueDTO> productCharValues) throws Exception {
        // check productType nul
        if (Utils.isNull(productTypeId)) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST, "product Type is null");
        }
        // check productType exist
        productTypeRepository.findById(productTypeId).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Product Type: " + productTypeId)
        );

        // save thumbnail
        String thumbnailName = System.currentTimeMillis() + thumbnail.getOriginalFilename();
        boolean upFileThumbnailSuccess = ftpService.uploadFile(thumbnailFolder, thumbnail, thumbnailName);
        if (!upFileThumbnailSuccess) {
            throw new IOException("cant not upload thumbnail file: " + thumbnail.getOriginalFilename());
        }
        // save product
        Product productSave = productRepository.save(Product.builder().
                productTypeId(productTypeId).
                createDatetime(new Date()).
                thumbnail(thumbnailName).
                createUser(UserUtil.getUserId()).
                status(status).
                name(name).
                price(price).
                description(description).
                quantity(quantity)
                .build());
        // save images
        images.forEach((image -> {
            String imageName = System.currentTimeMillis() + image.getOriginalFilename();
            try {
                ftpService.uploadFile(imagesFolder, image, imageName);
            } catch (Exception e) {
                throw new RuntimeException("cant not upload thumbnail file: " + image.getOriginalFilename());
            }
            productImageRepository.save(ProductImage.builder().
                    image(imageName).
                    productId(productSave.getId()).
                    status(true).
                    createUser(UserUtil.getUserId()).
                    createDatetime(new Date()).build());
        }));
        //check char value
        if (!Utils.isNull(productCharValues)) {
            productCharValues.stream().forEach(charValue -> {
                //check exist product char
                if (!productCharValueRepository.existsById(charValue.getId())) {
                    throw new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Product Char Value: " + charValue.getId());
                }
            })
            ;
        }
        // save char value
        List<ProductCharUse> productCharUses = new ArrayList<>();
        productCharValues.forEach(charValue -> {

            ProductSpecCharUse productSpecCharUse = productSpecCharUseRepository.findByProductSpecCharValueID(charValue.getId());
            ProductCharUse productCharUse = ProductCharUse.builder().productSpecCharUseId(productSpecCharUse.getId())
                    .productId(productSave.getId())
                    .createUser(UserUtil.getUserId())
                    .createDatetime(new Date()).build();
            productCharUses.add(productCharUse);
        });
        productCharUseRepository.saveAll(productCharUses);
        return null;
    }

    public List<ProductDTO>  get(String baseUrl, Long id) {
        //get Product
        List<Product> resultProduct = productRepository.findByIdCus(id);
        if (Utils.isListEmpty(resultProduct)) {
            return null;
        }
        List<ProductDTO> resultProductDTO = resultProduct.stream().map(item -> modelMapper.map(item, ProductDTO.class)).collect(Collectors.toList());

        resultProductDTO.stream().forEach(item -> {
            item.setProductType(productTypeRepository.findById(item.getProductTypeId()).get());
            item.setThumbnail(baseUrl + apiFileEndpoint + thumbnailFolder + "/" + item.getThumbnail());
            item.setImages(productImageRepository.findByProductId(item.getId()).stream().map(image -> baseUrl + apiFileEndpoint + imagesFolder + "/" + image.getImage()).collect(Collectors.toList()));
            List<ProductSpecCharUse> productSpecCharUses = productSpecCharUseRepository.findByIds(
                    productCharUseRepository.findByProductId(item.getId()).stream().map(charUse ->
                            charUse.getProductSpecCharUseId()).collect(Collectors.toList()));
            Map<Long, List<Long>> charUseMap = new HashMap<>();
            productSpecCharUses.forEach(specCharUse -> {
                if (charUseMap.containsKey(specCharUse.getProductSpecCharID())) {
                    charUseMap.get(specCharUse.getProductSpecCharID()).add(specCharUse.getProductSpecCharValueID());
                } else {
                    charUseMap.put(specCharUse.getProductSpecCharID(), Lists.newArrayList(specCharUse.getProductSpecCharValueID()));
                }
            });
            List<ProductSpecCharDTO>productSpecCharDTOs = new ArrayList<>();
            charUseMap.forEach((key, value) -> {
                ProductSpecCharDTO productSpecCharDTO = modelMapper.map(productSpecCharRepository.getById(key), ProductSpecCharDTO.class);
                List<ProductSpecCharValueDTO> productSpecCharValueDTO = productCharValueRepository.findAllById(value).stream()
                        .map(charValue-> modelMapper.map(charValue,ProductSpecCharValueDTO.class)).collect(Collectors.toList());
                productSpecCharDTO.setProductSpecCharValueDTOS(productSpecCharValueDTO);
                productSpecCharDTOs.add(productSpecCharDTO);
            });
            item.setProductSpecChars(productSpecCharDTOs);

        });

        return resultProductDTO;
    }
}
