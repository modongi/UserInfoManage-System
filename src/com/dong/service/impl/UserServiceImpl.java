package com.dong.service.impl;

import com.dong.dao.UserDao;
import com.dong.dao.impl.UserDaoImpl;
import com.dong.domain.PageBean;
import com.dong.domain.User;
import com.dong.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * 业务接口的实现类
 */
public class UserServiceImpl implements UserService {

    //创建Dao层对象
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {
        //调用Dao的方法，查询登录用户的信息
        return dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        //健壮性判断，防止空指针异常
        if (ids != null && ids.length > 0) {
            //1.遍历数组
            for (String id : ids) {
                //2.调用dao的方法删除
                dao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        //1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<>();
        //2.设置参数
        pb.setRows(rows);

        //3.调用dao查询总记录数
        //int totalCount = dao.findTotalCount();
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        //4.计算总页码
        int totalPage = (totalCount % rows) == 0 ? totalCount / rows : (totalCount / rows + 1);
        pb.setTotalPage(totalPage);
        //使用后台代码优化：上一页、下一页边界禁用
        if (currentPage <= 0) {
            currentPage = 1;
        } else if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        pb.setCurrentPage(currentPage);

        //5.调用dao查询List集合
        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        //List<User> list = dao.findByPage(start, rows);
        List<User> list = dao.findByPage(start, rows, condition);
        pb.setList(list);

        return pb;
    }
}
