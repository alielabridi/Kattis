import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.text.html.parser.Element;

/**
 * ShovellingSnow class is used to shovell as little snow as possible between four friends houses, there are obstacles and cleared spots. 
 * It uses Breadth-first search to complete the task.  
 * @author Jocke
 *
 */
public class ShovellingSnow {
	
	private final int UNKNOWN = -1;
	private final char BLOCKED = '#';
	private final char CLEARED = '.';
	private final int MOVEMENTS = 4;
	private final int [] RIGHT_LEFT = {0,0,1,-1};
	private final int [] DOWN_UP = {1,-1,0,0};
	private final String [] HOUSES = {"A","B","C","D"};
	private ArrayList<String> notVisitedHouses;
	private int n, m, shortestPath;
	private String start;	
	
	/**
	 * When a instance of ShovellingSnow is created it will initialize the search process. 
	 * It waits for input to proceed. 
	 * @param sc 
	 * @param n Grid height.
	 * @param m Grid width.
	 */
	public ShovellingSnow(Scanner sc, int n , int m){
		notVisitedHouses = new ArrayList<String>(Arrays.asList(HOUSES));
		setSize(n, m);
		String[] map = fillMap(sc);
		this.shortestPath = search(map, getStart(map));			
	}
	
	/**
	 * setSize method will initialize the grid dimensions. 
	 * If the inputs dont't match the criteria, an exception will be thrown. 
	 * @param n
	 * @param m
	 * @throws InputMismatchException
	 */
	private void setSize(int n, int m) throws InputMismatchException{
		if(n >= 1 && n <= 20 && m >= 1 && m <= 20){
			this.n = n;
			this.m = m;			
		}else
			throw new InputMismatchException("n & m need to be >= 1 and <= 20");
	}
	
	/**
	 * fillMap() will create a "grid" of the input from the user. 
	 * If the grid doesn't match the intervall a exception will be thrown.
	 * @param in, scanner object. 
	 * @return map contaning the grid content.
	 * @throws InputMismatchException
	 */
	private String[] fillMap(Scanner in) throws InputMismatchException{
		String[] map = new String[n];
		for(int i = 0;i<n;i++){
			String temp = in.next();
			if(temp.length() == m)
				map[i] = temp;
			else
				throw new InputMismatchException("The grid size doesn't match n & m.");
		}
		return map;
	}
	
	/**
	 * getStart() will iterate through the grid until it matches a house to start from. 
	 * If the grid does not contain houses, it will throw an exception. 
	 * @param map
	 * @return new start point.
	 * @throws InputMismatchException
	 */
	private Point getStart(String[] map) throws InputMismatchException{
		for(int i = 0; i<map.length; i++){
			for(int j = 0; j < HOUSES.length; j++)
				if(map[i].contains(HOUSES[j])){
					start = HOUSES[j];
					notVisitedHouses.remove(start);
					return new Point(i,map[i].indexOf(HOUSES[j]));
				}
		}
		throw new InputMismatchException("A,B,C or D can't be found!");
	}
	
	/**
	 * fillAdjacencyMatrix is used to create an Adjacency Matrix, it is used to save the distance. 
	 * @return
	 */
	private int[][] fillAdjacencyMatrix(){
		int[][] adMatrix = new int[this.n][this.m];
        for(int [] v : adMatrix)
            Arrays.fill(v,UNKNOWN);
        return adMatrix;
	}
	
	/**
	 * getShortestPath will return then shortest possible path in the grid. 
	 * @return 
	 */
	public String getShortestPath(){
		return Integer.toString((shortestPath/2));
	}
	
	/**
	 * By using Breadth-first search distance from start to all points will be created. 
	 * @param map, containing the grid.
	 * @param startPoint, where to start in the grid. 
	 * @return shortestpath
	 */
	private int search(String [] map, Point startPoint){
        LinkedList<Point> points = new LinkedList<Point>();
        //Add the start point to the queue.
        points.add(startPoint);
        
        //Fill the Adjacency matrix with UNKNOW value. 
        int[][] adMatrix = fillAdjacencyMatrix();
        
        //Set distance 0 att the start point. 
        adMatrix[startPoint.x][startPoint.y] = 0;
        
        String[][] test = new String[this.n][this.m];
        
        while(!points.isEmpty()){
            Point firstPoint = points.pop();
            /*
             * Two arrays are used to represent the movements. From a location in the grid, you can move up, down, left and right.
             */
            for(int i = 0; i < MOVEMENTS; i++){
            	int directionX = firstPoint.x + RIGHT_LEFT[i];
                int directionY = firstPoint.y + DOWN_UP[i];
                
                if(directionX >= 0 && directionY >= 0 && directionX < n && directionY < m){
                	//Get the distance from the Adjacency Matrix, in the current position in the grid.
                    int locationDist = adMatrix[directionX][directionY];
                    
                    //Get the character from the grid, this is used to check different circumstances.
                    char locationValue = map[directionX].charAt(directionY);

                    //If the distance is unknown, and current spot is not a blocked spot. Continue to adress this location.
                	if(locationDist == UNKNOWN && locationValue != BLOCKED){
                		int pointValue = adMatrix[firstPoint.x][firstPoint.y];
               		    
                		//If the current spot is cleared, it doesn't cost anything. Else add one movement. 
                		if(locationValue == CLEARED)
                			adMatrix[directionX][directionY] = pointValue;
                		else{
        					adMatrix[directionX][directionY] = pointValue + 1;                					
        				}
                		
                		//Go through all the houses to make sure current spot is not one of these, because they dont't cost anything. 
                		for(int k = 0 ; k<HOUSES.length;k++){
                			if(!HOUSES[k].equals(start)){
                				if(locationValue == HOUSES[k].charAt(0)){
                					adMatrix[directionX][directionY] = pointValue;                		                 					
                				}                				                				
                			}else if(HOUSES[k].equals(start) && locationValue != CLEARED)
                				adMatrix[directionX][directionY] = pointValue + 1;
                		}
                		test[directionX][directionY] = locationValue +""+adMatrix[directionX][directionY];
                		//Add new point to queue
                		points.add(new Point(directionX,directionY));
                		
                		//If the location value of current spot is a house we need to add the cost of this to the total. 
                		//Remove the house as visited, to make sure that all can be visited.
                		if(locationValue == 'A' || locationValue == 'B' || locationValue == 'C' || locationValue == 'D'){
                			String s = ""+locationValue;
                			notVisitedHouses.remove(s);
                			shortestPath += adMatrix[directionX][directionY];
                		}                		
                	}
                }
            }
        }
        
        if(notVisitedHouses.size() == 0)
        	return shortestPath;
        else 
        	throw new InputMismatchException("All the houses cant be reached.");
    }
	
	public static void main(String[] args){		
		Scanner sc = new Scanner(System.in);			
		try{
			//Go through the input, if 0 0 is entered, quit. Else Shovel Snow! 
			while (sc.hasNext()){
				int n = sc.nextInt();
				int m = sc.nextInt();
				if(n == 0 || m == 0)
					break;
				else{
					//Print the shortest distance of the entered grid.
					System.out.println(new ShovellingSnow(sc,n,m).getShortestPath());
				}
			}		
		}catch (Exception E){
			System.out.println(E.getMessage());
		}
		
	}
}
