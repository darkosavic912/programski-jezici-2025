package rs.ac.singidunum.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
     List<Orders> findAllByDeletedAtIsNull();

     List<Orders> findByCustomerId(Integer customerId);

     Optional<Orders> findByIdAndDeletedAtIsNull(Integer id);

     List<Orders> findAllByCustomerIdAndDeletedAtIsNull(Integer customerId); 

  
   

}
