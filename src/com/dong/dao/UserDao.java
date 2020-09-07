package com.dong.dao;

import com.dong.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的DAO层
 */
public interface UserDao {

    /**
     * 查询所有用户信息
     * @return
     */
    public abstract List<User> findAll();

    /**
     * 登录查询用户的用户名和密码
     * @param username
     * @param password
     * @return
     */
    public abstract User findUserByUsernameAndPassword(String username, String password);

    /**
     * 添加新的User
     * @param user
     */
    void add(User user);

    /**
     * 删除当前id对应的用户User
     * @param id
     */
    void delete(int id);

    /**
     * 根据当前id查询到用户User信息
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 修改用户User信息
     * @param user
     */
    void update(User user);

    /**
     * (条件)查询总记录数
     * @return
     * @param condition
     */
    int findTotalCount(Map<String, String[]> condition);

    /**
     * 分页(条件)查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
