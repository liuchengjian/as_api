package com.liucj.as.api.service;

import com.liucj.as.api.mapper.UserMapper;
import com.liucj.as.api.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {
    @Autowired
    private UserMapper mUserMapper;
    public void  addUser(String userName,String passWord,String imoocId,String orderId){
        mUserMapper.addUser(userName,passWord,imoocId,orderId, DateUtil.currentDate());
    }
}
