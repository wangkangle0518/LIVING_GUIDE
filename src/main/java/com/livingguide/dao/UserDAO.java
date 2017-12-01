package com.livingguide.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UserPO;
import com.livingguide.vo.query.UserQueryModel;

@Repository
public interface UserDAO extends BaseDAO<UserPO, UserQueryModel> {

    public UserPO createUser(UserPO user);
    public void updateUser(UserPO user);
    public void deleteUser(Long userId);

    public void correlationRoles(Long userId, Long... roleIds);
    public void uncorrelationRoles(Long userId, Long... roleIds);

    public UserPO findOne(Long userId);

    public UserPO findByUsername(String username);

    public Set<String> findRoles(String username);

    public Set<String> findPermissions(String username);

}
