package com.shopme.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

	@Query("SELECT A FROM User A WHERE A.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("UPDATE User s SET s.enabled = ?2 WHERE s.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean status);
  
	public Long countById(Integer id);
	

}
