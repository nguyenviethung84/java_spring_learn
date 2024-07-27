package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @SuppressWarnings({ "unchecked" })
    User save(User user);

    List<User> findByEmail(String email);

    List<User> findByEmailAndPhone(String email, String phone);

    List<User> findAll();

    User findById(long id);

    boolean existsByEmail(String email);
}
