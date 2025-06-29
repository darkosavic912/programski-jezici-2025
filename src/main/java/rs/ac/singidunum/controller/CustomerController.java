package rs.ac.singidunum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.entity.Customer;
import rs.ac.singidunum.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customer")
@CrossOrigin
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getCustomerById(id));
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer model) {
        return service.createCustomer(model);
    }

    @PutMapping(path = "/{id}")
    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer model) {
        return service.updateCustomer(id, model);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) {
        service.deleteCustomer(id);
    }
}