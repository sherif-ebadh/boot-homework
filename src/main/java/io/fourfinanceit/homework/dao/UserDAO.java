package io.fourfinanceit.homework.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import io.fourfinanceit.homework.entity.User;

@Transactional
public interface UserDAO extends CrudRepository<User, Integer> {
	public User findByUserName(String userName);
}