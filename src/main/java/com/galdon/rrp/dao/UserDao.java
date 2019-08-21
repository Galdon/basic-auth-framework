package com.galdon.rrp.dao;

import com.galdon.rrp.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: rrp
 * @description: 用户 DAO
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public interface UserDao {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User queryUserByUsername(@Param("username") String username);

    /**
     * 获取所有用户
     * @return
     */
    public List<User> queryAllUser();
}
