package com.example.shop.sales;

import com.example.shop.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Sales {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String itemName;
    @Column
    private Integer price;
    @Column
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;
    @CreationTimestamp
    private LocalDateTime created;
}
