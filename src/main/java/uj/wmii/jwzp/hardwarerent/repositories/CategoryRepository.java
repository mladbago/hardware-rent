package uj.wmii.jwzp.hardwarerent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uj.wmii.jwzp.hardwarerent.models.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
