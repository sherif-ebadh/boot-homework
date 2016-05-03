
package controllerTest;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.fourfinanceit.homework.Application;
import io.fourfinanceit.homework.controller.LoanController;
import io.fourfinanceit.homework.dao.LoanDAO;
import io.fourfinanceit.homework.dao.UserDAO;
import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.User;
import io.fourfinanceit.homework.model.LoanRequest;
import io.fourfinanceit.homework.model.UserRequest;
import io.fourfinanceit.homework.service.LoanService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class LoanControllerTest {

	final String BASE_URL = "http://localhost:8080/";

	private MockMvc mockMvc;

	@Mock
	public LoanService loanService;

	@Mock
	public UserDAO userDao;

	@Mock
	public LoanDAO loanDao;

	@InjectMocks
	private LoanController loanController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
	}

	@Test
	public void testLoanRequest() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/text;charset=UTF-8"))
				.andExpect(content().string("Welcome to 4financeit! :) Please use the correct Request"));
	}

	@Test
	public void testLoanRequestWithParmeter() throws Exception {
		User user = new User("Test", "testUser11");
		Loan loan = new Loan(new Date(), 123, "127.0.0.1", user);
		when(loanService.saveLoan(any(LoanRequest.class), eq("testUser11"), any(HttpServletRequest.class),
				any(User.class))).thenReturn(loan);
		when(loanService.checkUser("testUser11")).thenReturn(user);
		this.mockMvc
				.perform(post("/user/testUser11/loan").contentType("application/json")
						.content("{\"firstName\":\"Test\",\"amount\":\"10000\"}")
						.accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isCreated()).andExpect(content().contentType("application/text;charset=UTF-8"))
				.andExpect(content()
						.string(allOf(startsWith("New Loan Created for user"), containsString("loan Refernce ID"))));
	}

	@Test
	public void testLoanRequestWithExceedAmount() throws Exception {
		User user = new User("Test", "testUser11");
		when(loanService.saveLoan(any(LoanRequest.class), eq("testUser11"), any(HttpServletRequest.class),
				any(User.class))).thenReturn(null);
		when(loanService.checkUser("testUser11")).thenReturn(user);
		this.mockMvc
				.perform(post("/user/testUser11/loan").contentType("application/json")
						.content("{\"firstName\":\"Test\",\"amount\":\"1000000\"}")
						.accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isNotAcceptable())
				.andExpect(content().contentType("application/text;charset=UTF-8"))
				.andExpect(content().string(allOf(startsWith("Rejection"))));
	}

	@Test
	public void testLoanRequestWithNonExictUser() throws Exception {
		when(loanService.checkUser("testUser11")).thenReturn(null);
		this.mockMvc
				.perform(post("/user/testUser11/loan").contentType("application/json")
						.content("{\"firstName\":\"Test\",\"amount\":\"1000000\"}")
						.accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateNewUser() throws Exception {
		User user = new User("Test", "testUser11");
		when(loanService.createUser(any(UserRequest.class))).thenReturn(user);
		this.mockMvc
				.perform(post("/user").contentType("application/json")
						.content("{\"firstName\":\"Test\",\"userName\":\"testUser11\"}")
						.accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isCreated()).andExpect(content().contentType("application/text;charset=UTF-8"))
				.andExpect(content().string(allOf(startsWith("User created with username"))));
	}

	@Test
	public void testCreateNewUserFaild() throws Exception {
		when(loanService.createUser(any(UserRequest.class))).thenReturn(null);
		this.mockMvc
				.perform(post("/user").contentType("application/json")
						.content("{\"firstName\":\"Test\",\"userName\":\"testUser11\"}")
						.accept(MediaType.parseMediaType("application/text;charset=UTF-8")))
				.andExpect(status().isNotAcceptable());
	}
}