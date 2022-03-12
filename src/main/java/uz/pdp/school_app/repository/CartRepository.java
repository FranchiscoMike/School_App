package uz.pdp.school_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.school_app.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
