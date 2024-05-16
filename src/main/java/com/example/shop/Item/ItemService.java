package com.example.shop.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public void saveItem(String title, Integer price, String imgurl, Long memberID){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setImgUrl(imgurl);
        item.setMemberId(memberID);
        itemRepository.save(item);
    }

    public void updateItem(Long id, String title, Integer price, String imgurl, Long memberID) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setTitle(title);
            item.setPrice(price);
            item.setImgUrl(imgurl);
            item.setMemberId(memberID);
            itemRepository.save(item);
        }
    }


    public void delete(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            itemRepository.delete(item);
        }
    }


    public List<Item> find(){
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(Long id){
        return itemRepository.findById(id);
    }
}
