package com.swiftcart.swiftcartproductservice.services;

import com.swiftcart.swiftcartproductservice.dtos.ProductRequestDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductResponseDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductUpdateDTO;
import com.swiftcart.swiftcartproductservice.exceptions.InsufficientStock;
import com.swiftcart.swiftcartproductservice.exceptions.ProductNotFoundException;
import com.swiftcart.swiftcartproductservice.mappers.ProductMapper;
import com.swiftcart.swiftcartproductservice.models.Product;
import com.swiftcart.swiftcartproductservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {

        Product entity = productMapper.toEntity(productRequestDTO);

        Product savedProduct = productRepository.save(entity);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        List<Product> allProducts = productRepository.findAll();

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for(Product product : allProducts){
            ProductResponseDTO prod = productMapper.toResponseDTO(product);
            productResponseDTOList.add(prod);
        }
        return productResponseDTOList;
    }

    @Override
    public ProductResponseDTO updateProductById(Long id, ProductRequestDTO productRequestDTO) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id + " to update"));

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(productRequestDTO.getCategory());
        product.setPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStockQuantity());

        LocalDateTime localDateTime = LocalDateTime.now();

        product.setUpdatedAt(localDateTime);

        Product save = productRepository.save(product);

        return productMapper.toResponseDTO(save);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found with id " + id + " to partially update"));

        if(productUpdateDTO.getName() != null){
            product.setName(productUpdateDTO.getName());
        }
        if(productUpdateDTO.getDescription() != null){
            product.setDescription(productUpdateDTO.getDescription());
        }
        if(productUpdateDTO.getCategory() != null){
            product.setCategory(productUpdateDTO.getCategory());
        }
        if(productUpdateDTO.getPrice() != null){
            product.setPrice(productUpdateDTO.getPrice());
        }
        if(productUpdateDTO.getStockQuantity() != null){
            product.setStockQuantity(productUpdateDTO.getStockQuantity());
        }

        Product save = productRepository.save(product);

        return productMapper.toResponseDTO(save);
    }

    @Override
    public ProductResponseDTO deleteProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found to delete"));

        ProductResponseDTO responseDTO = productMapper.toResponseDTO(product);

        productRepository.delete(product);

        return responseDTO;
    }

    @Override
    @Transactional
    public void reduceStock(Long id, Integer quantity) {

        int updatedRows = productRepository.reduceStock(
                id,
                quantity,
                LocalDateTime.now()
        );

        if (updatedRows == 0) {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException("Product not found");
            }

            throw new InsufficientStock("Insufficient stock");
        }
    }


    @Override
    @Transactional
    public void restoreStock(Long id, Integer quantity) {

        int updatedRows = productRepository.restoreStock(
                id,
                quantity,
                LocalDateTime.now()
        );

        if (updatedRows == 0) {
            throw new RuntimeException("Product not found");
        }
    }
}
