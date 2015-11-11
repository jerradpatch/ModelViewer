package DAOTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.UserModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/applicationContext-hibernate.xml"})
public class DAOTestConcurancy {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOTestConcurancy.class);
	
	@Test
	public void TestConcurancyTestViability() throws InterruptedException{
		
		boolean testPassed = false;
		
		ConcurrancyTestClass singletonTest = new ConcurrancyTestClass();
		List<MultiThreadClass> classList = new ArrayList<MultiThreadClass>();
		
		for(int i = 0; i < 100; ++i){
			MultiThreadClass mtc = new MultiThreadClass(i,singletonTest);
			classList.add(mtc);
		}
		for(MultiThreadClass mtc : classList){
			mtc.start();
		}
		for(MultiThreadClass mtc : classList){
			mtc.join();
		}
		
		int countFails = 0;		
		for(MultiThreadClass mtc : classList){
			if(mtc.threadPassed == -1){
				testPassed = true;
				countFails++;
			}
			else if(mtc.threadPassed == 0){
				fail("a thread did not evaluate its varible, ie expected and returned not tested");
			}				
		}
		logger.info("count of instances that were not concurrent: "+countFails);
		//there should have been a test that over wrote another test's returned value;
		assertTrue(testPassed);
	}
	class ConcurrancyTestClass{
		int memberVarbile = -1;

		public int getMemberVarbile() {
			return memberVarbile;
		}

		public void setMemberVarbile(int memberVarbile) {
			this.memberVarbile = memberVarbile;
		}
		
	}	
	public class MultiThreadClass extends Thread {
		public int expected = 0;
		public ConcurrancyTestClass singletonObject = null;
		public int threadPassed = 0;
		public int returned = 0;
		
		public MultiThreadClass(int expected, ConcurrancyTestClass singletonObject) {
			super();
			this.expected = expected;
			this.singletonObject = singletonObject;
		}
		public void run(){
			
			
			//set a number
			singletonObject.memberVarbile = expected;
			try {
				Random rn = new Random();
				int i = Math.abs(rn.nextInt() % 10000);
				
				Thread.sleep(i);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Retrieve a number
			returned = singletonObject.memberVarbile;
			//compare expected
			if(returned == expected){
				threadPassed = 1;
			} else {
				threadPassed = -1;
			}
			return;
		}
	}
	
	
	@Inject
	UserDAO userDAO;
	
	@Test
	public void TestDAOConcurancy_userRead() throws InterruptedException{
		int testSize = 200;
		
		boolean testPass = true;
		//write a bunch of objects to db
		List<UserModel> userM = new ArrayList<UserModel>();
		for(int i = 0; i < testSize; i++){
			UserModel um = new UserModel();
			um.setPassword("Test"+i);
			um.setUserName("Test"+i);
			userM.add(um);
			userDAO.CreateUserByModel(um);
		}
		
		//start threads to read those objects
		List<TestDAOConcurancy_UserThread> userThreads = new ArrayList<TestDAOConcurancy_UserThread>();
		for(int i = 0; i < testSize; i++){
			TestDAOConcurancy_UserThread ut = new TestDAOConcurancy_UserThread(userM.get(i),userDAO);
			userThreads.add(ut);
		}
		//see if read objects match expected
		for(TestDAOConcurancy_UserThread ut : userThreads){
			ut.start();
		}
		for(TestDAOConcurancy_UserThread ut : userThreads){
			ut.join();
		}
			//clean up
		for(TestDAOConcurancy_UserThread ut : userThreads){
			userDAO.DeleteByModel(ut.expected);
		}		
		//check all objects to see if any returned an error
		for(TestDAOConcurancy_UserThread ut : userThreads){
			if(ut.threadPassed == -1){
				fail("this implementation is not concurrent");
				testPass = false;
			} else if(ut.threadPassed == 0){
				fail("there was a thread that was not initialized");
				return;
			}
		}
		
		assertTrue(testPass);
		
		
	}
	public class TestDAOConcurancy_UserThread extends Thread {
		public UserModel expected = null;
		public UserModel returned = null;
		public UserDAO userDAO = null;
		public int threadPassed = 0;
		

		public TestDAOConcurancy_UserThread(UserModel expected, UserDAO userDAO) {
			super();
			this.expected = expected;
			this.userDAO = userDAO;
		}


		public void run(){
			
			try {
				Random rn = new Random();
				int i = Math.abs(rn.nextInt() % 1000);
				
				Thread.sleep(i);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Retrieve a number
			returned = userDAO.GetUserByUserName(expected.getUserName());
			//compare expected
			if(expected.getPassword().equals(returned.getPassword()) && expected.getUserName().equals(returned.getUserName()) ){
				threadPassed = 1;
			} else {
				threadPassed = -1;
			}
			return;
		}
	}
	
	//concurrent class, gets back and object and tests if the object it got back was the on that it expected
}

















