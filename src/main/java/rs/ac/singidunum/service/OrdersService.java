package rs.ac.singidunum.service;

import lombok.RequiredArgsConstructor;
import rs.ac.singidunum.entity.Cart;
import rs.ac.singidunum.entity.Customer;
import rs.ac.singidunum.entity.Orders;
import rs.ac.singidunum.repo.CustomerRepository;
import rs.ac.singidunum.repo.OrdersRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.singidunum.repo.CartRepository;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
  
    public List<Orders> getOrders() {
        return ordersRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Orders> getOrderById(Integer id) {
        return ordersRepository.findByIdAndDeletedAtIsNull(id);
    }

    public List<Orders> getOrdersByCustomerId(Integer customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }

    public void createOrdersFromCart(Integer customerId) {
     List<Cart> cartItems = cartRepository.findAllByCustomerIdAndDeletedAtIsNull(customerId);
      if (cartItems.isEmpty()) {
        throw new RuntimeException("Korpa je prazna");
     }

    for (Cart cart : cartItems) {
        Orders order = new Orders();
        order.setProductId(cart.getProductId());
        order.setProductName(cart.getProductName());
        order.setTotalPrice(cart.getPrice());
        order.setQuantity(1);
        Customer customer = new Customer();
        customer.setId(customerId);
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());

        ordersRepository.save(order);

        cart.setDeletedAt(LocalDateTime.now());
        cartRepository.save(cart);
        }
    }

    public void createOrder(Orders model) {
        Orders orders = new Orders();
       
        orders.setProductId(model.getProductId());
        orders.setProductName(model.getProductName());
        orders.setTotalPrice(model.getTotalPrice());
        orders.setQuantity(model.getQuantity());

        if (!customerRepository.existsByIdAndDeletedAtIsNull(model.getCustomer().getId()))
            throw new RuntimeException("CUSTOMER_NOT_FOUND");

        Customer customer = new Customer();
        customer.setId(model.getCustomer().getId());
        orders.setCustomer(customer);

        orders.setCreatedAt(LocalDateTime.now());
        ordersRepository.save(orders);

           //ADDED
        cartRepository.findByCustomerIdAndProductIdAndDeletedAtIsNull(model.getCustomer().getId(),model.getProductId())
        .ifPresent(cart -> {    
        cart.setDeletedAt(LocalDateTime.now());
        cartRepository.save(cart);
        });
      }

    public void updateOrder(Integer id, Orders model) {
        Orders orders = this.getOrderById(id).orElseThrow();
     
        Customer customer = new Customer();
        customer.setId(model.getCustomer().getId());
        orders.setCustomer(customer);

        orders.setUpdatedAt(LocalDateTime.now());
        ordersRepository.save(orders);
    }

    public void payOrder(Integer id) {
        Orders orders = this.getOrderById(id).orElseThrow();

        if (orders.getPaidAt() != null)
            throw new RuntimeException("TICKET_ALREADY_PAYED");

        orders.setPaidAt(LocalDateTime.now());
        ordersRepository.save(orders);
    }

    public void deleteOrder(Integer id) {                          
        Orders orders = this.getOrderById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Narudžbina sa id " + id + " nije pronađena"));
    
        if (orders.getDeletedAt() != null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Narudžbina je već obrisana.");
      }
 
      orders.setDeletedAt(LocalDateTime.now());
      ordersRepository.save(orders);
    }
}   

