package com.checklist.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productID;

    private String name;

    private Integer price;

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
