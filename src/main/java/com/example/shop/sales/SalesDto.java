package com.example.shop.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SalesDto {
    private String itemName;
    private Integer price;
    private String userName;
    private LocalDateTime created;
    private Integer count;
    private Integer sum;
}
