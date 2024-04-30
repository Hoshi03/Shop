package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    @GetMapping("/notice")
    String notice(Model model){
        List<Notification> notifications = notificationRepository.findAll();
        model.addAttribute("notice", notifications);

        System.out.println(notifications);
        return "notice.html";
    }
}
