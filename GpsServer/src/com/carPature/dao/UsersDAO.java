package com.carPature.dao;

import java.util.List;

import com.carPature.entity1.Users;

public interface UsersDAO {
	//查找指定的用户
	public  List search(Users condition);
	//增加一个用户信息
	public void addUser(Users condition);
	//删除一个用户的名字
	public void delUser(Users user);
	//更新一个用户的信息
	public void updateUser(Users dondition);
	//筛选用户信息
	public Users findUserBy(Users condition);
}
