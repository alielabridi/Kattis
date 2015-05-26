import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Ladder class is used to to find the length of a ladder when climbing a wall h high,
 *  and is at a angle v degrees.  
 * 
 * 
 * @author Joakim Fristedt
 *
 */
public class Ladder {
	
	private int h;
	private int v;
	private Scanner sc;
	
	public Ladder(){
		this.sc = new Scanner(System.in);
		initValues();
		sc.close();
	}
	
	/**
	 * Get the values of h and v from the scanner. Check if they are in the interval, 
	 * if not throw exception. 
	 * @throws InputMismatchException
	 */
	private void initValues()throws InputMismatchException{
		this.h = sc.nextInt();
		this.v = sc.nextInt();
		if(!(h > 0 && h <= 10000 && v > 0 && v <= 89))
			throw new InputMismatchException("Wrong input, 1 <= h <= 10000 & 1 <= v <= 89");
	}
	
	/**
	 * Calculate the length of the ladder. Convert v to radian, then h/sin(v). 
	 * Then Math.ceil to return the smallest integer greater than or equal given number. 
	 * @return ladderLength
	 */
	public int getHeight(){
		double v = Math.toRadians(this.v);
		int ladderLength = (int) Math.ceil(this.h/Math.sin(v)); 
		return ladderLength; 
	}
	
	public static void main(String[] args){
		/*
		 * We create a ladder and print the calculated height. 
		 * If error, it is handled and prints what error. 
		 */
		try{
			System.out.println(new Ladder().getHeight());			
		}catch(Exception E){
			System.out.print(E.getMessage());
		}
	}
}
