package jp.co.worksap.roster.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import jp.co.worksap.roster.ejb.UserEJB;
import jp.co.worksap.roster.entity.User;

@Named
@RequestScoped
public class UserController {
	@EJB
	private UserEJB userEJB;

	public List<User> getUsers(int page, int size) {
		List<User> users = userEJB.findAllUsers(page, size);
		return users;
	}

	public void createUser() {

	}
}
