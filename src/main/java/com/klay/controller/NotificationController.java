package com.klay.controller;

import com.klay.dto.NotificationDto;
import com.klay.enums.NotificationTypeEnum;
import com.klay.model.User;
import com.klay.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile (HttpServletRequest request,
                           @PathVariable("id") Long id) {
        User user =(User) request.getSession().getAttribute("user");
        if (user==null) {
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                ||NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()) {
            return "redirect:/question/" + notificationDto.getOuterId();
        }else {
            return "redirect:/";
        }

    }
}
