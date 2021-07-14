package com.liucj.as.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)//不展示为空的字段
public class UserEntity {

    /** 用户ID */
    public String uid ;
    /** imooc用户ID */
    public String imoocId ;
    /** 订单ID */
    public String orderId ;
    /** 用户名 */
    public String userName ;
    /** 密码 */
    public String pwd ;
    /** 创建时间 */
    public String createTime ;
    /** 是否被禁用 */
    public String forbid ;
}
