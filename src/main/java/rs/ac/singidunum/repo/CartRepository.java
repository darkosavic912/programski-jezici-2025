package rs.ac.singidunum.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
 List<Cart> findAllByDeletedAtIsNull();

 Optional<Cart> findByIdAndDeletedAtIsNull(Integer id);

 List<Cart> findAllByCustomerIdAndDeletedAtIsNull(Integer customerId);
 
 Boolean existsByIdAndDeletedAtIsNull(Integer id);

 Optional<Cart> findByCustomerIdAndProductIdAndDeletedAtIsNull(Integer customerId, Integer productId); 

}
