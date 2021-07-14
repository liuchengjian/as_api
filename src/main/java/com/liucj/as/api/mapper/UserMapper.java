package com.liucj.as.api.mapper;

import com.liucj.as.api.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    //添加用户
    void  addUser(String userName,String passWord,String imoocId,String orderId,String createTime);
    //查找用户列表
    List<UserEntity> findUser(String userName);
}
