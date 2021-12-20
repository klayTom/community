package com.kaly.controller;

import com.kaly.dto.AccessTokenDto;
import com.kaly.dto.GithubUser;
import com.kaly.mapper.UserMapper;
import com.kaly.model.User;
import com.kaly.provider.GithubProvider;
import com.kaly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value(("${github.client.secret}"))
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callBack (@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletResponse response) {

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());

        if (githubUser != null && githubUser.getId()!=null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userService.createOrUpdate(user);
            // 登陆成功 将用户保存到 session 域中
            Cookie cookie = new Cookie("token", token);
            /*cookie.setMaxAge(60 * 60 * 1);*/
            response.addCookie(cookie);
            return "redirect:/";
        }else {
            // 登陆失败 ， 重新登录
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
