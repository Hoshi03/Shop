package com.example.shop.sales;

import com.example.shop.member.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        sales.setItemName(itemName);
        sales.setPrice(price);
        Member member = new Member();
        member.setId(user.userId);
        sales.setMember(member);
        sales.setCount(count);
        salesRepository.save(sales);
/*        MemberDto memberInfo = memberService.getMemberInfo(member.get().getId());
        model.addAttribute("memberInfo", memberInfo);*/
        return "redirect:/salesList";
    }

    @GetMapping("/salesList")
    String salesList(Model model) {
        List<Sales> salesList = salesRepository.customFindAll();
        List<SalesDto> salesDtoList = new ArrayList<>();

        for (Sales sales : salesList) {
            SalesDto salesDto = new SalesDto();
            salesDto.setItemName(sales.getItemName());
            salesDto.setPrice(sales.getPrice());
            salesDto.setCreated(sales.getCreated());
            salesDto.setCount(sales.getCount());
            salesDto.setSum(sales.getCount() * salesDto.getPrice());
            salesDto.setUserName(sales.getMember().getUserName());
            salesDtoList.add(salesDto);
        }

        model.addAttribute("salesList", salesDtoList);
        return "salesList";
    }

    @GetMapping("/order/all")
    String getMember(){
        Optional<Member> member = memberRepository.findById(3L);
        System.out.println(member.get().getSalesList());
        return "index";
    }
}
