package com.example.shop.member;

import com.example.shop.sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, unique = true)
    private String userName;
    @Column
    private String passWord;
    @Column
    private String displayName;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    List<Sales> salesList = new ArrayList<>();
}
