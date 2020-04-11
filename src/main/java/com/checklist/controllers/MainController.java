package com.checklist.controllers;

import com.checklist.domain.Product;
import com.checklist.repo.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MainController {
    private final ProductRepo productRepo;

    @PostMapping("/buyProduct")
    private List<Product> buyProduct(@RequestBody Map<String, String> buyData) {
        System.out.println(buyData);
        String name = buyData.get("name");

        if (name != null && !name.isEmpty())
        {
            Integer price = Integer.parseInt(buyData.get("price"));
            Product product = new Product(name.trim(), price);
            productRepo.save(product);
            return productRepo.findAll();
        }
        return new ArrayList<>();
    }

    @PostMapping("/completeProducts")
    private List<Product> completeProducts(@RequestBody ArrayList<Integer> completed) {
        for (Integer productID : completed) {
            Product product = productRepo.findByProductID(productID);
            productRepo.delete(product);
        }
        return productRepo.findAll();
    }

    @GetMapping("/getProducts")
    private List<Product> getProducts() {
        return productRepo.findAll();
    }
}
