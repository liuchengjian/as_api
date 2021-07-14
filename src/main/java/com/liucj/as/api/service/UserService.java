package com.liucj.as.api.service;

import com.liucj.as.api.entity.UserEntity;
import com.liucj.as.api.mapper.UserMapper;
import com.liucj.as.api.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService {
    @Autowired
    private UserMapper mUserMapper;
    public void  addUser(String userName,String passWord,String imoocId,String orderId){
        mUserMapper.addUser(userName,passWord,imoocId,orderId, DateUtil.currentDate());
    }

    /**
     * 查找用户
     * @param userName
     */

    public List<UserEntity> findUser(String userName){
        return mUserMapper.findUser(userName);
    }
    /**
     * 查找用户列表
     */
    public List<UserEntity> getUserList(){
        return mUserMapper.getUserList();
    }
}
