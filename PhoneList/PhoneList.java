import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Phonelist class is used to check if numbers are prefix to other numbers.
 * It will get one or more test cases which contain phonenumbers.  
 * @author Joakim Fristedt
 *
 */
public class PhoneList {
	
	//numbers ArrayList is used to store the numbers in each test case. 
	private ArrayList<String> numbers;	
	private Scanner sc;
	private int numbOfTestCase;
	private int numbOfPhoneNumbers;
	private String phoneNumb;
	
	public PhoneList(){
		this.numbers = new ArrayList<String>();
		this.sc = new Scanner(System.in);
		this.phoneNumb = new String();
	}
	
	/**
	 * init method is used to get the total number of test cases 
	 * and checks that these are in the allowed interval, 
	 * if not it will give an exception.  
	 * For every testcase it will run the method addNumbers().
	 * Depending on the outcome from addNumbers method it will print if it the list was consistent or not.  
	 * @throws InputMismatchException
	 */
	private void init() throws InputMismatchException{
		numbOfTestCase = sc.nextInt();
		
		if(numbOfTestCase < 1 || numbOfTestCase > 40)
			throw new InputMismatchException("Testcase need to be in range 1 to 40.");
		
		for(int i = 0; i<numbOfTestCase;i++){
			addNumbers();
			if(checkIfConsistent())
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}

	/**
	 * addNumbers() will get the total number of phone number in current test case,
	 * if not in the allowed interval it will give an exception. 
	 * It will clear the array, from potential earlier testcases phone numbers. 
	 * For every number in current test case it will add to the array, 
	 * if the number is in the allowed interval. 
	 * The array is then sorted to make the comparison later faster.   
	 * 
	 * @throws InputMismatchException
	 */
	private void addNumbers() throws InputMismatchException{
		numbOfPhoneNumbers = sc.nextInt();
		if(numbOfPhoneNumbers < 1 || numbOfPhoneNumbers > 10000)
			throw new InputMismatchException("Phonenumbers need to be in range 1 to 10000.");
		
		numbers.clear();
		
		for(int i = 0; i<numbOfPhoneNumbers; i++){
			phoneNumb = sc.next();
			if(phoneNumb.length() > 10)
				throw new InputMismatchException("Phonenumb is longer than 10 digits.");
			numbers.add(phoneNumb);
		}
		
		Collections.sort(numbers);
	}
	
	/**
	 * checkIfConsistent will check the numbers from the test case. 
	 * Two variables are used, potentialPrefix and couldHavePrefix. 
	 * Since the array is sorted, potentialPrefix is one index less than couldHavePrefix. 
	 * Then it checks that the length of couldHavePrefix is greater or equal to potentialPrefix. 
	 * If this is the case, a sub string is created from couldHavePrefix with the same length as potentialPrefix.
	 * If the sub string is equal to potentialPrefix, the list is not consistent.     
	 *     
	 * @return <tt>true</tt> if current test case is consistent, and <tt>false</tt> if it is not consistent. 
	 */
	private boolean checkIfConsistent(){
		for(int i=0; i<numbOfPhoneNumbers-1; i++){
			String potentialPrefix = numbers.get(i);
			String couldHavePrefix = numbers.get(i+1);
			if(couldHavePrefix.length() >= potentialPrefix.length()){
				phoneNumb = couldHavePrefix.substring(0,potentialPrefix.length());
				if(phoneNumb.equals(potentialPrefix))
					return false;				
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		try{
			new PhoneList().init();
		}catch(Exception E){
			System.out.println(E.getMessage());
		}
		
	}
	

}
