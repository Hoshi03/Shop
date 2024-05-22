package com.example.shop.Item;

import com.example.shop.comment.Comment;
import com.example.shop.comment.CommentRepository;
import com.example.shop.comment.CommentService;
import com.example.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/list";
    }

    @GetMapping("/list")
    String hello(Model model){
        // 정렬 조건: ID 역순
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // PageRequest 생성 시 정렬 조건 추가
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Item> page = itemRepository.findPageBy(pageRequest);

        model.addAttribute("items", page);
        model.addAttribute("pages", page.getTotalPages());

        return "list.html";
    }


    @GetMapping("/list/page/{id}")
    String getListPage(Model model, @PathVariable Integer id){
        //n번째 페이지에서 m개 가져온다
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // PageRequest 생성 시 정렬 조건 추가
        PageRequest pageRequest = PageRequest.of(id-1, 5, sort);
        Page<Item> page = itemRepository.findPageBy(pageRequest);
        model.addAttribute("items",page);
        model.addAttribute("pages", page.getTotalPages());
        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    String addPost(String title, Integer price, String imgurl, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        if (title == null || price == null){
            return "redirect:/write";
        }
        itemService.saveItem(title,price,imgurl, user.userId);
        return "redirect:/list";
    }

    //URL 파라미터로 상품번호로 상세페이지 조회하기
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) throws Exception{
        Optional<Item> result = itemService.findOne(id);
        List<Comment> comments = commentRepository.findAllByParentId(id);
        model.addAttribute("comments",comments);
        Item item;
        if (result.isPresent()){
            item = result.get();
            model.addAttribute("item", item);
            return "detail.html";
        }
        else return "redirect:/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        Optional<Item> result = itemService.findOne(id);
        if (result.get().getMemberId().equals(user.userId)){
            model.addAttribute("item", result.get());
            return "edit.html";
        }
        else return "redirect:/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    String editItem(Long id, String title, Integer price, String imgurl, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        itemService.updateItem(id,title,price, imgurl, user.userId);
        return "redirect:/list";
    }

    //db에서 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        if (itemService.findOne(id).get().getMemberId().equals(user.userId)){
            itemService.delete(id);
        }
        return "redirect:/list";
    }

    //Ajax 쿼리 스트링을 이용한 삭제 기능
    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id){
        itemService.delete(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    //s3 url 테스트용
    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var url = s3Service.createPreSignedUrl("test/" + filename);
        System.out.println(url);
        return url;
    }

    @GetMapping("/search")
    String postSearch(@RequestParam String searchText, Model model){
        List<Item> itemList = itemRepository.rawQuery1(searchText);
        if (!itemList.isEmpty()){
            model.addAttribute("items", itemList);
        }
        return "search.html";
    }
}
