package com.purv.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



import com.purv.model.UserInfo;
@Repository
@Transactional
public interface UserDAO extends CrudRepository<UserInfo,Integer>{

	UserInfo getUserInfoByUserName(String username);

	List<UserInfo> findAllByEnabled(short s);

	UserInfo findByUserNameAndEnabled(String username, short enabled);

}
