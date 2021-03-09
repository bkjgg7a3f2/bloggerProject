package com.guolong.service;

import com.guolong.po.User;

public interface UserService {
	
	User checkUser(String username,String password);
}
