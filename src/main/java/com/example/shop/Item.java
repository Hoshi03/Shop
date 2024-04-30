package com.example.shop;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String title;
    private Integer price;

    public Item(String title, String price, Long id){
        this.title = title;
        this.price = Integer.parseInt(price);
        this.id = id;
    }
}