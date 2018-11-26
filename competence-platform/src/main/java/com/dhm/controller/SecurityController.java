package com.dhm.controller;

import com.dhm.encrypt.CipherUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2018/11/26 16:05
 */
@RestController
@RequestMapping("/security")
public class SecurityController {
    @PostMapping("/login")
    public Map<String,String> decrypt(String userName,String password,
                          String userNameEncrypt,String passwordEncrypt) throws Exception {
        LinkedHashMap<String,String> dataMap = new LinkedHashMap<>();
        dataMap.put("userName",userName);
        dataMap.put("password",password);
        dataMap.put("userNameEncrypt",userNameEncrypt);
        dataMap.put("passwordEncrypt",passwordEncrypt);
        String userNameDecrypt = CipherUtil.decryptRSA(userNameEncrypt);
        String passwordDecrypt = CipherUtil.decryptRSA(passwordEncrypt);
        dataMap.put("userNameDecrypt",userNameDecrypt);
        dataMap.put("passwordDecrypt",passwordDecrypt);
        return dataMap;
    }
}
