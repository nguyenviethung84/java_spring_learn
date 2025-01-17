package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @SuppressWarnings({ "unchecked", "null" })
    Product save(Product product);

    @SuppressWarnings("null")
    List<Product> findAll();

    Optional<Product> findById(long id);

    void deleteById(long id);

    @SuppressWarnings({ "null" })
    Page<Product> findAll(Pageable page);

    @SuppressWarnings("null")
    Page<Product> findAll(Specification<Product> specification, Pageable page);
}
