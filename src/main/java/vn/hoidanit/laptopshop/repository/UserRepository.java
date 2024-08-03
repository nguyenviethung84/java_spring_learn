package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @SuppressWarnings({ "unchecked", "null" })
    User save(User user);

    User findByEmail(String email);

    List<User> findByEmailAndPhone(String email, String phone);

    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

    User findById(long id);

    boolean existsByEmail(String email);
}
