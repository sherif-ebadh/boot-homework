package daoTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.fourfinanceit.homework.Application;
import io.fourfinanceit.homework.dao.UserDAO;
import io.fourfinanceit.homework.entity.User;


//import org.hibernate.Sess
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserDaoTest {

	@Autowired
	private UserDAO userDao;

	private List<User> UserList;

	@Before
	public void setup() {
		// Initialize User mock objects for testing
		UserList = new ArrayList<User>();

		User user = new User();
		user.setFirstName("Test");
		user.setUserName("user1");

		User user2 = new User();
		user2.setFirstName("Mark");
		user2.setUserName("user2");
		
		UserList.add(user);
		UserList.add(user2);

	}

	@Test
	@Transactional
	public void testAddUser() {
		for (Iterator<User> iterator = UserList.iterator(); iterator.hasNext();) {
			User User = (User) iterator.next();
			// insert the User object
			userDao.save(User);
			assertTrue(User.getFirstName() + " is saved - Id " + User.getId(), User.getId() > 0);
		}
	}

	@Test
	@Transactional
	public void testUpdate() {
		// get a User object from the repository
		User User = (User) UserList.get(0);
		userDao.save(User);
		User dbUser = userDao.findOne(User.getId());

		// assert if its not null
		assertTrue(!dbUser.getFirstName().isEmpty());

		// change the client name
		dbUser.setFirstName("user1-Changed");
		// update the User object
		userDao.save(dbUser);

		// assert the value changed id true
		assertEquals("user1-Changed", userDao.findOne(User.getId())
				.getFirstName());
	}

	@Test
	@Transactional
	public void testDelete() {
		// get a CASUser object from the repository
		User User = (User) UserList.get(1);
		userDao.save(User);
		User userBeforeDel = userDao.findOne(User.getId());

		// delete the User object
		userDao.delete(userBeforeDel.getId());

		// get a User object from the repository
		User userAfterDel = userDao.findOne(userBeforeDel.getId());

		// get the User object ID
		assertNull(userAfterDel);
	}

	@Test
	@Transactional
	public void testFind() {
		User User = (User) UserList.get(0);
		userDao.save(User);
		User userBeforeDel = userDao.findOne(User.getId());
		// get a User object from the repository
		User user = userDao.findOne(userBeforeDel.getId());

		// compare the id's of passed and retrieved objects.
		assertThat(user.getId(), is(user.getId()));
		assertTrue(user.getId() == userBeforeDel.getId());
	}


	@Test
	@Transactional
	public void testFindAll() {
		// get all User object from the repository
		for (Iterator<User> iterator = UserList.iterator(); iterator.hasNext();) {
			User User = (User) iterator.next();
			// insert the User object
			userDao.save(User);
			assertTrue(User.getFirstName() + " is saved - Id " + User.getId(), User.getId() > 0);
		}
		// check if it returns all records from DB
		Iterable<User> UserList = userDao.findAll();		
		int size = 0;
		if (UserList instanceof Collection)
		   size = ((Collection<?>)UserList).size();
		assertTrue(size > 0);
	}

}