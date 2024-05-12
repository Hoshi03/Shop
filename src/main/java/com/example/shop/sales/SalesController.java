package com.example.shop.sales;

import com.example.shop.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    String postSales(@RequestParam Integer count, @RequestParam Integer price,
                       @RequestParam String itemName, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal(); //로그인 된 사용자 정보 가져오기!
        Sales sales = new Sales();
        Optional<Member> member = memberRepository.findById(user.userId);
        sales.setItemName(itemName);
        sales.setPrice(price);
        sales.setCount(count);
        salesRepository.save(sales);
        return "redirect:/salesList";
    }

    @GetMapping("/salesList")
    String salesList(Model model){
        List<Sales> salesList = salesRepository.findAll();
        model.addAttribute("salesList",salesList);
        return "salesList.html";
    }
}
