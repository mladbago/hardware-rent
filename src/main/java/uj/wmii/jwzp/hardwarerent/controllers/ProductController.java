package uj.wmii.jwzp.hardwarerent.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uj.wmii.jwzp.hardwarerent.data.ArchivedProducts;
import uj.wmii.jwzp.hardwarerent.dtos.ProductDto;
import uj.wmii.jwzp.hardwarerent.exceptions.AlreadyExistsException;
import uj.wmii.jwzp.hardwarerent.exceptions.NotFoundException;
import uj.wmii.jwzp.hardwarerent.services.interfaces.ProductService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(required = false)String companyName,
                                           @RequestParam(required = false)BigDecimal price,
                                           @RequestParam(required = false)String categoryName) {
        return productService.getAllProducts(companyName, price, categoryName);
    }

    @GetMapping("{id}")
    public ProductDto getProductById(@PathVariable(name = "id") Long id) {

        return productService.getProductById(id).orElseThrow(
                () -> new NotFoundException("Failed to find product with id: " + id));

    }

    @GetMapping("/archived")
    public List<ArchivedProducts> getAllArchived() {
        return productService.getAllArchivedProducts();
    }

    @PostMapping
    public ResponseEntity createNewProduct(@Valid @RequestBody ProductDto productDto) {
        if (productService.getProductByModel(productDto.getModel()).isPresent())
            throw new AlreadyExistsException("Product with such name already exists!");
        Optional<ProductDto> savedProduct = productService.createNewProduct(productDto);
        if (savedProduct.isEmpty())
            throw new NotFoundException("No category with name: " + productDto.getCategoryName());

        return ResponseEntity.created(URI.create("/products/" + savedProduct.get().getId()))
                .body("Product has been successfully created!");

    }

    @PutMapping("{id}")
    public ResponseEntity updateWholeProductById(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto) {
        if (productService.getProductById(id).isEmpty())
            throw new NotFoundException("Failed to find product with id: " + id);
        if (productService.getProductByModel(productDto.getModel()).isPresent())
            throw new AlreadyExistsException("Product with such name already exists!");
        if (productService.updateWholeProductById(id, productDto).isEmpty())
            throw new NotFoundException("Category cannot be changed!");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {

        if (!productService.deleteProductById(id)) {
            throw new NotFoundException("Failed to find product with id: " + id);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}")
    public ResponseEntity updatePartOfProductById(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        if (productService.getProductById(id).isEmpty())
            throw new NotFoundException("Failed to find product with id: " + id);
        if (productService.getProductByModel(productDto.getModel()).isPresent())
            throw new AlreadyExistsException("Product with such name already exists!");
        if (productService.updatePartOfProductById(id, productDto).isEmpty())
            throw new NotFoundException("Category cannot be changed!");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
