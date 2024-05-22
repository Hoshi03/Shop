package com.example.shop.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    //페이지에서 일부만 가져오는 방법
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

    // 검색어 포함된 상품을 가져온다
    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    List<Item> rawQuery1(String searchText);
}
