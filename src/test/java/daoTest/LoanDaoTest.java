package daoTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.fourfinanceit.homework.Application;
import io.fourfinanceit.homework.dao.LoanDAO;
import io.fourfinanceit.homework.dao.UserDAO;
import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.User;

//import org.hibernate.Sess
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class LoanDaoTest {

	@Autowired
	private LoanDAO loanDao;

	@Autowired
	private UserDAO userDao;

	private List<Loan> loanList;
	private User user;
	java.util.Date date = new Date();
	java.sql.Date dateSQL = new java.sql.Date(date.getTime());

	@Before
	public void setup() {
		// Initialize Loan mock objects for testing
		loanList = new ArrayList<Loan>();

		user = new User();
		user.setFirstName("user1");
		user.setUserName("Mark");

		Loan loanObj = new Loan();
		loanObj.setLoanDate(date);
		loanObj.setAmount(12345);
		loanObj.setUserIp("127.0.0.1");

		Loan loanObj2 = new Loan();
		loanObj2.setLoanDate(date);
		loanObj2.setAmount(12345);
		loanObj2.setUserIp("192.0.0.1");

		loanList.add(loanObj);
		loanList.add(loanObj2);

	}

	@Test
	@Transactional
	public void testAddLoan() {
		userDao.save(user);
		for (Iterator<Loan> iterator = loanList.iterator(); iterator.hasNext();) {
			Loan loan = (Loan) iterator.next();
			loan.setUser(user);
			// insert the Loan object
			loanDao.save(loan);
			assertTrue("Loan is saved - Id " + loan.getId(), loan.getId() > 0);
		}
	}

	@Test
	@Transactional
	public void testUpdate() {

		userDao.save(user);
		// get a Loan object from the repository
		Loan loan = (Loan) loanList.get(0);

		loan.setUser(user);
		loanDao.save(loan);

		Loan dbLoan = loanDao.findOne(loan.getId());

		// assert if its not null
		assertTrue(!dbLoan.getUserIp().isEmpty());

		// change the client name
		dbLoan.setUserIp("192.168.1.1");
		// update the Loan object
		loanDao.save(dbLoan);

		// assert the value changed id true
		assertEquals("192.168.1.1", loanDao.findOne(loan.getId()).getUserIp());
	}

	@Test
	@Transactional
	public void testDelete() {
		userDao.save(user);
		// get a CASLoan object from the repository
		Loan loan = (Loan) loanList.get(1);
		loan.setUser(user);

		loanDao.save(loan);
		Loan loanBeforeDel = loanDao.findOne(loan.getId());

		// delete the Loan object
		loanDao.delete(loanBeforeDel.getId());

		// get a Loan object from the repository
		Loan loanAfterDel = loanDao.findOne(loanBeforeDel.getId());

		// get the Loan object ID
		assertNull(loanAfterDel);
	}

	@Test
	@Transactional
	public void testFind() {
		userDao.save(user);
		// get a CASLoan object from the repository
		Loan loan = (Loan) loanList.get(0);
		loan.setUser(user);

		loanDao.save(loan);
		// get a Loan object from the repository
		Loan loan2 = loanDao.findOne(loan.getId());

		// compare the id's of passed and retrieved objects.
		assertThat(loan.getId(), is(loan2.getId()));
		assertTrue(loan.getId() == loan2.getId());
	}

	@Test
	@Transactional
	public void testFindByUserIdAndLoanDateAndUserIp() {
		userDao.save(user);
		// get a CASLoan object from the repository
		Loan loan = (Loan) loanList.get(0);
		loan.setUser(user);
		loanDao.save(loan);
		// get a Loan object from the repository
		List<Loan> loanObj = loanDao.findByUserIdAndLoanDateAndUserIp(user.getId(), dateSQL,"127.0.0.1");
		// compare the id's of passed and retrieved objects.
		assertTrue(loanObj.size() == 1);
	}

	
	@Test
	@Transactional
	public void testFindAll() {
		// insert the User object
		userDao.save(user);
		
		for (Iterator<Loan> iterator = loanList.iterator(); iterator.hasNext();) {
			Loan loan = (Loan) iterator.next();
			// insert the User object
			loan.setUser(user);
			loanDao.save(loan);
			assertTrue("Loan is saved - Id " + loan.getId(), loan.getId() > 0);
		}
		// check if it returns all records from DB
		Iterable<Loan> LoanList = loanDao.findAll();	
		
		int size = 0;
		if (LoanList instanceof Collection)
		   size = ((Collection<?>)LoanList).size();
		assertTrue(size > 0);
	}

}