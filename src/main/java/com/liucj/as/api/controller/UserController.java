package com.liucj.as.api.controller;

import com.liucj.as.api.entity.ResponseCode;
import com.liucj.as.api.entity.ResponseEntity;
import com.liucj.as.api.entity.UserEntity;
import com.liucj.as.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Api(tags = {"Account"})
public class UserController {
    @Autowired
    private UserService mUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//加密，每次返回都不一样

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam(value = "userName") @ApiParam("账号或手机")  String userName,
                                @RequestParam(value = "password")  @ApiParam("密码") String password){
        List<UserEntity>list = mUserService.findUser(userName);
        if(list==null||list.isEmpty()){
            return ResponseEntity.of(ResponseCode.RC_ACCOUNT_INVALID);
        }
        UserEntity userEntity = null;
        for (UserEntity entity:list){
            //判断密码是否正确
            if(bCryptPasswordEncoder.matches(password,entity.pwd));
            userEntity = entity;
            break;
        }
        if(userEntity==null){
            return ResponseEntity.of(ResponseCode.RC_PWD_INVALID);
        }
        if("1".equals(userEntity.forbid)){
            return ResponseEntity.of(ResponseCode.RC_USER_FORBID);
        }
        return ResponseEntity.success(userEntity).setMessage("登录成功");
    }



    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam(value = "userName") @ApiParam("账号或手机")  String userName, @RequestParam(value = "password")  @ApiParam("密码") String password
            , @RequestParam(value = "imoocId")  @ApiParam("慕课ID") String imoocId, @RequestParam(value = "orderId")  @ApiParam("订单ID") String orderId){
        try {
            mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), imoocId, orderId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.errorMessage("moocID有相同，请检查");
        }
        return ResponseEntity.successMessage("注册成功");
    }
}
