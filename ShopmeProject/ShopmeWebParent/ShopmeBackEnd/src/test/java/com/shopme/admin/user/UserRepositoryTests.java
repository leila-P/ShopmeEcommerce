package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userAmirReza = new User("mohamadpor.l@gmail.com","123","Leila","Mohammadpour");
		userAmirReza.addRole(roleAdmin);
		
		User savedUser = repo.save(userAmirReza);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		User userMali = new User("mm_mohammadpour@yahoo.com","1234","malihe","Mohammadpour");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userMali.addRole(roleEditor);
		userMali.addRole(roleAssistant);
		
		User savedUser = repo.save(userMali);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userName = repo.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails( ) {
		User userName = repo.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("mohammadpoumasti.l@gmail.com");
		
		repo.save(userName);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userMali = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userMali.getRoles().remove(roleEditor);
		userMali.addRole(roleSalesperson);
		
		repo.save(userMali);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "mohamadpor.l@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 26;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 26;
		repo.updateEnabledStatus(id, true);
    }
    
  @Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
  
  @Test
  public void testListFirstPage() {
	  int pageNumber = 0;
	  int pageSize = 4;
	  
	  Pageable pageable = PageRequest.of(pageNumber, pageSize);
	  //Page<User> page = repo.findAll(pageable);
	  
//	  List<User> listUsers = page.getContent();
//	  
//	  listUsers.forEach(user -> System.out.println(user));
//	  
//	  assertThat(listUsers.size()).isEqualTo(pageSize);
  }
}
