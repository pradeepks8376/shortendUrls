package com.neueda.shortenedurl.services.user;

import com.neueda.shortenedurl.dao.user.UserDao;
import com.neueda.shortenedurl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User save(User user) {
		return userDao.save(user);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
}
