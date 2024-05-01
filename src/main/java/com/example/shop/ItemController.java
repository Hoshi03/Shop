package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    String hello(Model model){
        List<Item> result = itemService.find();
        model.addAttribute("items",result);
        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price){
        if (title == null || price == null){
            return "redirect:/write";
        }
        itemService.saveItem(title,price);
        return "redirect:/list";
    }

    //URL 파라미터로 상품번호로 상세페이지 조회하기
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) throws Exception{
        Optional<Item> result = itemService.findOne(id);
        Item item;
        if (result.isPresent()){
            item = result.get();
            model.addAttribute("item", item);
            return "detail.html";
        }
        else return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model){
        Optional<Item> result = itemService.findOne(id);
        if (result.isPresent()){
            model.addAttribute("item", result.get());
            return "edit.html";
        }
        else return "redirect:/list";
    }

    @PostMapping("/edit")
    String editItem(Long id, String title, Integer price){
        itemService.updateItem(id,title,price);
        return "redirect:/list";
    }

}
