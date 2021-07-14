package com.liucj.as.api.controller;

import com.liucj.as.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService mUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//加密，每次返回都不一样


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Object register(@RequestParam(value = "userName")String userName,@RequestParam(value = "password")String password
            ,@RequestParam(value = "imoocId")String imoocId,@RequestParam(value = "orderId")String orderId){
        try {
            mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), imoocId, orderId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "hello";
    }
}
