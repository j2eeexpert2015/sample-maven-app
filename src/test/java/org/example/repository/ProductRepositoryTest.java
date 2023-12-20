package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.example.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        productRepository.save(product);
    }

    @Test
    void shouldFindAllProducts() {
        assertThat(productRepository.findAll()).isNotEmpty();
    }

    @Test
    void shouldSaveProduct() {
        Product product = new Product();
        product.setName("New Product");
        product.setPrice(29.99);
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
    }

    /*
    @Test
    void shouldNotSaveProductWithNullName() {
        Product product = new Product();
        product.setPrice(29.99);
        assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(product));
    }
    */
    
    @Test
    @Transactional
    void shouldNotSaveProductWithNullName() {
        assertThrows(ConstraintViolationException.class, () -> {
            Product product = new Product();
            product.setPrice(29.99);
            productRepository.saveAndFlush(product); // Use saveAndFlush to immediately commit the transaction
        });
    }
}
