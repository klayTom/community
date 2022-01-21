package com.klay.provider;

import com.alibaba.fastjson.JSON;
import com.klay.dto.AccessTokenDto;
import com.klay.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken (AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&" +
                        "code=" + accessTokenDto.getCode() + "&" +
                        "client_id=" + accessTokenDto.getClient_id() + "&" +
                        "redirect_uri=" + accessTokenDto.getRedirect_uri() + "&" +
                        "client_secret=" + accessTokenDto.getClient_secret())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("\"")[3];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GiteeUser getUser (String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

