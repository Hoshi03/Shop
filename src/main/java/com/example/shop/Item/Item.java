package com.example.shop.Item;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@ToString
@Table(indexes = @Index(columnList = "title", name = "itemTitleIDX"))
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String title;
    @Column
    private Integer price;
    @Column
    private String imgUrl;
    @Column
    private Long memberId;
}