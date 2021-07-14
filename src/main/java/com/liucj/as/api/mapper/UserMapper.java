package com.liucj.as.api.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    void  addUser(String userName,String passWord,String imoocId,String orderId,String createTime);
}
