package com.example.core.service;

import com.example.core.dto.ProductDTO;
import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.Product;
import com.example.core.entity.ProductCharUse;
import com.example.core.entity.ProductImage;
import com.example.core.entity.ProductSpecCharUse;
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
    @Value("${ftp.product.description}")
    private String descriptionFolder;

    @Value("${api.file.endpoint}")
    private String apiFileEndpoint;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(List<Long> ids) throws Exception {

        List<Product> products = productRepository.findAllById(ids);
        products.stream().forEach(item -> {
            //delete thumbnail
            try {
                ftpService.deleteFile(thumbnailFolder, item.getThumbnail());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //deleted description
            try {
                ftpService.deleteFile(descriptionFolder, item.getDescription());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // delete images
            List<ProductImage> productImages = productImageRepository.findByProductId(item.getId());
            productImages.stream().forEach(productImage -> {
                try {
                    ftpService.deleteFile(imagesFolder, productImage.getImage());
                    productImageRepository.deleteById(productImage.getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            //delete product char value
             productCharUseRepository.deleteAllByProductId(item.getId());
        });
        productRepository.deleteAllById(ids);
    }

    public ProductDTO create(Long id,
                             MultipartFile thumbnail,
                             MultipartFile[] images,
                             String[] oldImages,
                             Long productTypeId,
                             String name,
                             Long quantity,
                             Long price,
                             Boolean status,
                             MultipartFile description,
                             List<String> productCharValues) throws Exception {
        // check productType nul
        if (Utils.isNull(productTypeId)) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST, "product Type is null");
        }
        //check char value
        if (!Utils.isNull(productCharValues)) {
            productCharValues.stream().forEach(charValue -> {
                //check exist product char
                if (!productCharValueRepository.existsById(Long.parseLong(charValue))) {
                    throw new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Product Char Value: " + charValue);
                }
            })
            ;
        }
        // check productType exist
        productTypeRepository.findById(productTypeId).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Product Type: " + productTypeId)
        );
        if (!Utils.isNull(id)) {
            Product oldProduct = productRepository.getById(id);

            if (!Utils.isNull(thumbnail)) {
                ftpService.deleteFile(thumbnailFolder, oldProduct.getThumbnail());
                String thumbnailName = System.currentTimeMillis() + thumbnail.getOriginalFilename();
                boolean upFileThumbnailSuccess = ftpService.uploadFile(thumbnailFolder, thumbnail, thumbnailName);
                if (!upFileThumbnailSuccess) {
                    throw new IOException("cant not upload thumbnail file: " + thumbnail.getOriginalFilename());
                }
                oldProduct.setThumbnail(thumbnailName);
            }
            if (!Utils.isNull(description)) {
                ftpService.deleteFile(descriptionFolder, oldProduct.getDescription());
                String descriptionName = System.currentTimeMillis() + description.getOriginalFilename();
                boolean upFileDescriptionSuccess = ftpService.uploadFile(descriptionFolder, description, descriptionName);
                if (!upFileDescriptionSuccess) {
                    throw new IOException("cant not upload thumbnail file: " + description.getOriginalFilename());
                }
                oldProduct.setDescription(descriptionName);
            }
            if (!Utils.isNullOrEmpty(oldImages)) {
                List<String> oldImageFileName = Arrays.stream(oldImages).map(Utils::getFileName).collect(Collectors.toList());
                List<ProductImage> oldProductImages = productImageRepository.findByProductId(id);
                List<String> deleteImages = oldProductImages.stream().map(ProductImage::getImage).filter(image -> !oldImageFileName.contains(image)).collect(Collectors.toList());
                deleteImages.forEach(item -> {
                    try {
                        ftpService.deleteFile(imagesFolder, item);
                        productImageRepository.deleteByImage(item);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            if (!Utils.isNullOrEmpty(images)) {
                Arrays.asList(images).forEach((image -> {
                    String imageName = System.currentTimeMillis() + image.getOriginalFilename();
                    try {
                        ftpService.uploadFile(imagesFolder, image, imageName);
                    } catch (Exception e) {
                        throw new RuntimeException("cant not upload thumbnail file: " + image.getOriginalFilename());
                    }
                    productImageRepository.save(ProductImage.builder().
                            image(imageName).
                            productId(oldProduct.getId()).
                            status(true).
                            createUser(UserUtil.getUserId()).
                            createDatetime(new Date()).build());
                }));
            }
            List<ProductCharUse> productCharUses = new ArrayList<>();
            productCharUseRepository.deleteAllByProductId(oldProduct.getId());
            productCharValues.forEach(charValue -> {
                ProductSpecCharUse productSpecCharUse = productSpecCharUseRepository.findByProductSpecCharValueID(Long.parseLong(charValue));
                ProductCharUse productCharUse = ProductCharUse.builder().productSpecCharUseId(productSpecCharUse.getId())
                        .productId(oldProduct.getId())
                        .build();
                productCharUses.add(productCharUse);
            });
            productCharUseRepository.saveAll(productCharUses);
            oldProduct.setUpdateDatetime(new Date());
            oldProduct.setUpdateUser(UserUtil.getUserId());
            oldProduct.setStatus(status);
            oldProduct.setName(name);
            oldProduct.setProductTypeId(productTypeId);
            oldProduct.setPrice(price);
            oldProduct.setQuantity(quantity);
            productRepository.save(oldProduct);
            return null;
        }
//        // save thumbnail
        String thumbnailName = System.currentTimeMillis() + thumbnail.getOriginalFilename();
        boolean upFileThumbnailSuccess = ftpService.uploadFile(thumbnailFolder, thumbnail, thumbnailName);
        if (!upFileThumbnailSuccess) {
            throw new IOException("cant not upload thumbnail file: " + thumbnail.getOriginalFilename());
        }
//        // save description
        String descriptionName = System.currentTimeMillis() + description.getOriginalFilename();
        boolean upFileDescriptionSuccess = ftpService.uploadFile(descriptionFolder, description, descriptionName);
        if (!upFileDescriptionSuccess) {
            throw new IOException("cant not upload thumbnail file: " + description.getOriginalFilename());
        }
//        // save product
        Product productSave = productRepository.save(Product.builder().
                productTypeId(productTypeId).
                createDatetime(new Date()).
                thumbnail(thumbnailName).
                createUser(UserUtil.getUserId()).
                status(status).
                name(name).
                price(price).
                description(descriptionName).
                quantity(quantity)
                .build());
//        // save images
        Arrays.asList(images).forEach((image -> {
            String imageName = System.currentTimeMillis() + image.getOriginalFilename();
            try {
                ftpService.uploadFile(imagesFolder, image, imageName);
            } catch (Exception e) {
                throw new RuntimeException("cant not upload thumbnail file: " + image.getOriginalFilename());
            }
            productImageRepository.save(ProductImage.builder().
                    image(imageName).
                    productId(productSave.getId()).
                    status(true)
                    .build());
        }));

        // save char value
        List<ProductCharUse> productCharUses = new ArrayList<>();
        productCharValues.forEach(charValue -> {

            ProductSpecCharUse productSpecCharUse = productSpecCharUseRepository.findByProductSpecCharValueID(Long.parseLong(charValue));
            ProductCharUse productCharUse = ProductCharUse.builder().productSpecCharUseId(productSpecCharUse.getId())
                    .productId(productSave.getId())
                    .createUser(UserUtil.getUserId())
                    .createDatetime(new Date()).build();
            productCharUses.add(productCharUse);
        });
        productCharUseRepository.saveAll(productCharUses);
        return null;
    }

    public List<ProductDTO> get(String baseUrl, Long id) {
        //get Product
        List<Product> resultProduct = productRepository.findByIdCus(id);
        if (Utils.isNullOrEmpty(resultProduct)) {
            return null;
        }
        List<ProductDTO> resultProductDTO = resultProduct.stream().map(item -> modelMapper.map(item, ProductDTO.class)).collect(Collectors.toList());

        resultProductDTO.stream().forEach(item -> {
            item.setProductType(productTypeRepository.findById(item.getProductTypeId()).get());
            item.setThumbnail(baseUrl + apiFileEndpoint + thumbnailFolder + "/" + item.getThumbnail());
            item.setDescription(baseUrl + apiFileEndpoint + descriptionFolder + "/" + item.getDescription());
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
            List<ProductSpecCharDTO> productSpecCharDTOs = new ArrayList<>();
            charUseMap.forEach((key, value) -> {
                ProductSpecCharDTO productSpecCharDTO = modelMapper.map(productSpecCharRepository.getById(key), ProductSpecCharDTO.class);
                List<ProductSpecCharValueDTO> productSpecCharValueDTO = productCharValueRepository.findAllById(value).stream()
                        .map(charValue -> modelMapper.map(charValue, ProductSpecCharValueDTO.class)).collect(Collectors.toList());
                productSpecCharDTO.setProductSpecCharValueDTOS(productSpecCharValueDTO);
                productSpecCharDTOs.add(productSpecCharDTO);
            });
            item.setProductSpecChars(productSpecCharDTOs);

        });

        return resultProductDTO;
    }
}
