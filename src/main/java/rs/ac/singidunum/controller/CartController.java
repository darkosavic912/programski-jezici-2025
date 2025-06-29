package rs.ac.singidunum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.entity.Cart;
import rs.ac.singidunum.service.CartService;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/cart")
public class CartController {

    private final CartService service;

    @GetMapping
    public List<Cart> getCarts() {
        return service.getCarts();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getCartById(id));
    }

    @GetMapping("/customer/{id}")
    public List<Cart> getCartForCustomer(@PathVariable Integer id) {
    return service.getCartsByCustomerId(id);
   }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createCart(@RequestBody Cart item) {
        service.createCart(item);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCart(@PathVariable Integer id, @RequestBody Cart item) {
        service.updateCart(id, item);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable Integer id) {
        service.deleteCart(id);
    }
}
