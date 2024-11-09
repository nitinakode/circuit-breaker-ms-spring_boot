package com.example4444.demo444.a;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class AddressController {
    @Autowired
    private OrderServiceImpl addressService;
    @GetMapping("/{orderNumber}")
    public Type getAddressByPostalCode(@PathVariable("orderNumber") String postalCode) {
        return addressService.getOrderByPostCode(postalCode);
    }
}