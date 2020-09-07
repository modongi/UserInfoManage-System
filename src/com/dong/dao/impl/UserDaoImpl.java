package com.dong.dao.impl;

import com.dong.dao.UserDao;
import com.dong.domain.User;
import com.dong.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * dao层接口的实现类
 */
public class UserDaoImpl implements UserDao {

    //创建JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 实现查询所有用户信息
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        //JDBC操作数据库
        //1.定义sql
        String sql = "select * from user";
        //2.使用JdbcTemplate方法，执行sql
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    /**
     * 查询登录用户的用户名和密码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加新的User
     *
     * @param user
     */
    @Override
    public void add(User user) {
        //1.定义sql
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        //2.执行sql
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    /**
     * 删除当前id对应的用户User
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        //1.定义sql
        String sql = "delete from user where id = ?";
        //2.执行sql
        template.update(sql, id);

    }

    /**
     * 根据当前id查询到用户User信息
     *
     * @param id
     * @return
     */
    @Override
    public User findById(int id) {
        //1.定义sql
        String sql = "select * from user where id = ?";
        //2.执行sql
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    /**
     * 修改用户User信息
     *
     * @param user
     */
    @Override
    public void update(User user) {
        //1.定义sql
        String sql = "update user set name = ?,gender = ?,age = ? ,address = ?,qq = ?,email = ? where id = ?";
        //2.执行sql
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    /**
     * (条件)查询总记录数
     * @return
     * @param condition
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        /*
        String sql = "select count(*) from user";
        return template.queryForObject(sql,Integer.class);//返回一个整数类型
         */

        //1.定义模板初始化sql
        String sql = "select count(*) from user where 1 = 1 ";//注意空格，需要符合sql语法
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义sql参数的集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {

            //复杂条件查询优化：排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                //跳过本次循环
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                //复杂查询优化：注意模糊查询语法
                params.add("%"+value+"%");//添加 ？ 条件的值
            }
        }
        //拼接后完整的sql语句
        sql = sb.toString();

        System.out.println(sql);//测试
        System.out.println(params);//测试

        return template.queryForObject(sql,Integer.class,params.toArray());//返回一个整数类型
    }

    /**
     * 分页(条件)查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        /*
        * 分页查询
        * 第一个?:开始查询记录的索引
        * 第二个?:查询的记录数
        * */
        //String sql = "select * from user limit ?,?";

        //1.定义模板初始化sql
        String sql = "select * from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义sql参数的集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {

            //复杂条件查询优化：排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                //跳过本次循环
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                //复杂查询优化：注意模糊查询语法
                params.add("%"+value+"%");//添加 ？ 条件的值
            }
        }
        //添加分页的查询语法
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);

        //拼接后完整的sql语句
        sql = sb.toString();

        System.out.println(sql);//测试
        System.out.println(params);//测试
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());//可变参数本质是一个数组
    }
}
