package com.livingguide.service;

import java.util.Set;

import com.livingguide.common.service.IBaseService;
import com.livingguide.vo.UserPO;
import com.livingguide.vo.query.UserQueryModel;

public interface UserService extends IBaseService<UserPO, UserQueryModel>{

    /**
     * 创建用户
     * @param user
     */
    public UserPO createUser(UserPO user);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, Long... roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public UserPO findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
}
