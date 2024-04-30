package com.example.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT distinct MAX(id) FROM Item")
    Long findMaxId();
}
