package uj.wmii.jwzp.hardwarerental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.wmii.jwzp.hardwarerental.data.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}