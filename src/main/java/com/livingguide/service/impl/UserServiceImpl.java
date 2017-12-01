package com.livingguide.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UserDAO;
import com.livingguide.service.UserService;
import com.livingguide.util.PasswordHelper;
import com.livingguide.vo.UserPO;
import com.livingguide.vo.query.UserQueryModel;

@Service("userService")
public class UserServiceImpl extends BaseService<UserPO, UserQueryModel> implements UserService {

	// 使用DAO时放开注释即可
	private UserDAO userDAO;
	private PasswordHelper passwordHelper;

	@Autowired
	public void setDAO(UserDAO userDAO) {
		super.setDAO(userDAO);
		this.userDAO = userDAO;
	}

	public void setPasswordHelper(PasswordHelper passwordHelper) {
		this.passwordHelper = passwordHelper;
	}

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	@Override
	public UserPO createUser(UserPO user) {
		// 加密密码
		passwordHelper.encryptPassword(user);
		return userDAO.createUser(user);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	@Override
	public void changePassword(Long userId, String newPassword) {
		UserPO user = userDAO.getById(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDAO.updateUser(user);
	}

	/**
	 * 添加用户-角色关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		userDAO.correlationRoles(userId, roleIds);
	}

	/**
	 * 移除用户-角色关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		userDAO.uncorrelationRoles(userId, roleIds);
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public UserPO findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public Set<String> findRoles(String username) {
		return userDAO.findRoles(username);
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public Set<String> findPermissions(String username) {
		return userDAO.findPermissions(username);
	}

}
