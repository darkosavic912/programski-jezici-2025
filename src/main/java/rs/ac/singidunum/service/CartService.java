package rs.ac.singidunum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.entity.Cart;
import rs.ac.singidunum.entity.Customer;
import rs.ac.singidunum.repo.CartRepository;
import rs.ac.singidunum.repo.CustomerRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    public List<Cart> getCarts() {
        return cartRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Cart> getCartById(Integer id) {
        return cartRepository.findByIdAndDeletedAtIsNull(id);
    }

    //VRACA SAMO JEDNOG KORISNIKA
   public List<Cart> getCartsByCustomerId(Integer customerId) {
    return cartRepository.findAllByCustomerIdAndDeletedAtIsNull(customerId);
   }


  public void createCart(Cart model) {
        Cart cart = new Cart();
        cart.setProductId(model.getProductId());
        cart.setProductName(model.getProductName());
        cart.setPrice(model.getPrice());

        if (!customerRepository.existsByIdAndDeletedAtIsNull(model.getCustomer().getId()))
            throw new RuntimeException("CUSTOMER_NOT_FOUND");

            //MENJANO
            List<Cart> existingItems = cartRepository.findAllByCustomerIdAndDeletedAtIsNull(model.getCustomer().getId());

            for (Cart c : existingItems) {
             if (c.getProductId().equals(model.getProductId())) {
            // Ako već postoji isti proizvod, možeš samo ignorisati ili obraditi drugačije
            return; // ili throw new RuntimeException("PROIZVOD_VEĆ_U_KORPI");
        }
    }  

        Customer customer = new Customer();
        customer.setId(model.getCustomer().getId());
        cart.setCustomer(customer);


        cart.setCreatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    public void updateCart(Integer id, Cart model) {
        Cart cart = this.getCartById(id).orElseThrow();
        cart.setProductId(model.getProductId());
        cart.setProductName(model.getProductName());
        cart.setPrice(model.getPrice());
        
        Customer customer = new Customer();
        customer.setId(model.getCustomer().getId());
        cart.setCustomer(customer);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    public void deleteCart(Integer id) {
        Cart cart = this.getCartById(id).orElseThrow();
        cart.setDeletedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }
}


