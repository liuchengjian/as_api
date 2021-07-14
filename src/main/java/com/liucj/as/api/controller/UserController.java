package com.liucj.as.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liucj.as.api.config.NeedLogin;
import com.liucj.as.api.entity.ResponseCode;
import com.liucj.as.api.entity.ResponseEntity;
import com.liucj.as.api.entity.UserEntity;
import com.liucj.as.api.service.UserService;
import com.liucj.as.api.utils.UserRedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Api(tags = {"Account"})
public class UserController {
    @Autowired
    private UserService mUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//加密，每次返回都不一样
    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam(value = "userName") @ApiParam("账号或手机") String userName,
                                @RequestParam(value = "password") @ApiParam("密码") String password,
                                HttpServletRequest request) {
        List<UserEntity> list = mUserService.findUser(userName);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.of(ResponseCode.RC_ACCOUNT_INVALID);
        }
        UserEntity userEntity = null;
        for (UserEntity entity : list) {
            //判断密码是否正确
            if (bCryptPasswordEncoder.matches(password, entity.pwd)) ;
            userEntity = entity;
            break;
        }
        if (userEntity == null) {
            return ResponseEntity.of(ResponseCode.RC_PWD_INVALID);
        }
        if ("1".equals(userEntity.forbid)) {
            return ResponseEntity.of(ResponseCode.RC_USER_FORBID);
        }
        //保存到redis缓存中
        UserRedisUtil.addUser(redisTemplate, request.getSession(), userEntity);
        return ResponseEntity.success(UserRedisUtil.getKey(request.getSession())).setMessage("登录成功");
    }


    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam(value = "userName") @ApiParam("账号或手机") String userName, @RequestParam(value = "password") @ApiParam("密码") String password
            , @RequestParam(value = "imoocId") @ApiParam("慕课ID") String imoocId, @RequestParam(value = "orderId") @ApiParam("订单ID") String orderId) {
        try {
            mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), imoocId, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.errorMessage("moocID有相同，请检查");
        }
        return ResponseEntity.successMessage("注册成功");
    }

    @NeedLogin
    @ApiOperation(value = "注销登录")
    @RequestMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserRedisUtil.removeUser(redisTemplate, session);
        }
        return ResponseEntity.successMessage("退出登录成功");
    }


    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUserList(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页") int pageIndex,
                                      @RequestParam(value = "pageSize", required = true, defaultValue = "10")
                                      @ApiParam("每页显示的数量") int pageSize) {
        try {
            PageHelper.startPage(pageIndex,pageSize);
            List<UserEntity> userList = mUserService.getUserList();
            PageInfo<UserEntity>pageInfo = new PageInfo<>(userList);
            return ResponseEntity.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.errorMessage("查找用户列表失败");
        }

    }
}
