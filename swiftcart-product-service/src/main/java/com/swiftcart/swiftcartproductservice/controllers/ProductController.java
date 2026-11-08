package com.swiftcart.swiftcartproductservice.controllers;

import com.swiftcart.swiftcartproductservice.dtos.ProductRequestDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductResponseDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductUpdateDTO;
import com.swiftcart.swiftcartproductservice.dtos.StockUpdateDTO;
import com.swiftcart.swiftcartproductservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "PRODUCT SERVICE APIs")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @Operation(summary = "To add product")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.addProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "To retrieve a product using product id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all products")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @Operation(summary = "To update product using its id")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductbyId(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.updateProductById(id, productRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "To partially update the product")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return new ResponseEntity<>(productService.updateProduct(id, productUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "To delete a product using its unique id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<String> reduceStock(@PathVariable Long id, @Valid @RequestBody StockUpdateDTO requestDTO) {

        productService.reduceStock(id, requestDTO.getStockQuantity());

        return ResponseEntity.ok("Stock reduced successfully");
    }


    @PutMapping("/{id}/restore-stock")
    public ResponseEntity<String> restoreStock(
            @PathVariable Long id,
            @Valid @RequestBody StockUpdateDTO requestDTO) {

        productService.restoreStock(id, requestDTO.getStockQuantity());

        return ResponseEntity.ok("Stock restored successfully");
    }
}
