package rs.ac.singidunum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import rs.ac.singidunum.entity.Orders;
import rs.ac.singidunum.service.OrdersService;

import java.util.List;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders")
public class OrdersController {

    private final OrdersService service;

    @GetMapping
    public List<Orders> getOrders() {
        return service.getOrders();
    }

     //DODATOOO
     @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        List<Orders> orders = service.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getOrderById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrder(@RequestBody Orders orders) {
        service.createOrder(orders);
    }

    @PutMapping(path = "/{id}/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payOrder(@PathVariable Integer id) {
        service.payOrder(id);
    }

    @PostMapping("/from-cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void createOrdersFromCart(@RequestParam Integer customerId) {
    service.createOrdersFromCart(customerId);
     }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrder(@PathVariable Integer id, @RequestBody Orders orders) {
        service.updateOrder(id, orders);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
    }
}
