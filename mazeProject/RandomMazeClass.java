// Catherine Rodriguez, Reem Alharbi, Alexandria Brelsford
// maze randomizer class to generate a random maze
// 12/5/2017
// data structures 501

package mazeProject;
 
import java.util.LinkedList; 
import java.util.Random;

public class RandomMazeClass 
{
    public static final String WALL_BLOCK = "\u2588"; // black box symbol

    // instance variables
    private Space cells[][]; // a 2d list of room/wall cell objects in the maze
  	 
    private boolean isWall;  // checks if a cell is a wall
    private boolean isRoom;  // checks if a cell is a room
    private int row;		 // cell row
    private boolean gameEnd;   // if the game is won
     
	private int col;		 // cell col
    private int numRooms;    // number of rooms in maze
   // private Space start;  // add the starting and end room numbers (i think?)
   // private Space end;
    
	public RandomMazeClass( int row, int col )
    {
    	this.isWall = false;
    	this.isRoom = !isWall;
    	this.gameEnd = false;
        this.row = row;
        this.col = col;
        this.numRooms = 0;
       
        // initialize the list of spaces
        this.cells = new Space[row][col];

        // for each cell in the list, create a new space object to store
        // the details of each cell 
        for(int i = 0; i < col; i++)
        	for(int j = 0; j < row; j++)
        		cells[i][j] = new Space(); 
                		
        // create a linked list to hold cells adjacent to the space
        LinkedList<int[]> adjoiningCells = new LinkedList<>();
        
        Random rand = new Random();
        
        // maze starting cell always in position 0,0 in maze
        int xPos = 0; 
        int yPos = 0; 
        
        // add the first start cell 
        adjoiningCells.add(new int[]{xPos,yPos,xPos,yPos});
         
        // loop while able to process more cells in maze to build random maze
        while ( !adjoiningCells.isEmpty() )
        {
        	// choose a random cell from the adjoining cells list
        	int randNum = rand.nextInt( adjoiningCells.size() );
        	
        	// remove and store in loc - location array
            int[] loc = adjoiningCells.remove( randNum );
            
            // new cell uses the 3rd and 4th values from removed cell position list
            xPos = loc[2];
            yPos = loc[3];
             
            // check if the cell is a wall
            if ( cells[xPos][yPos].isTypeRoom() == isWall )
            { 
            	// change to a room 
                cells[xPos][yPos].setTypeRoom(isRoom);
                cells[loc[0]][loc[1]].setTypeRoom(cells[xPos][yPos].isTypeRoom());
                 
                // increment number of rooms in maze
                cells[loc[0]][loc[1]].setRoomNum(numRooms++);
                cells[xPos][yPos].setRoomNum(numRooms++);
                
                // check surrounding cells
                if ( xPos >= 2 && cells[xPos-2][yPos].isTypeRoom() == isWall )
                	adjoiningCells.add( new int[]{xPos-1,yPos,xPos-2,yPos} );
                	
                if ( yPos >= 2 && cells[xPos][yPos-2].isTypeRoom() == isWall )
                	adjoiningCells.add( new int[]{xPos,yPos-1,xPos,yPos-2} );
                
                if ( xPos < row-2 && cells[xPos+2][yPos].isTypeRoom() == isWall )
                	adjoiningCells.add( new int[]{xPos+1,yPos,xPos+2,yPos} );
                
                if ( yPos < col-2 && cells[xPos][yPos+2].isTypeRoom() == isWall )
                	adjoiningCells.add( new int[]{xPos,yPos+1,xPos,yPos+2} );
                 
            } // end if
            
        } // end while 
         
    } // end non-default constructor
 
    public void printMaze()
    { 
    	// print the top border to console
        for(int i = 0; i < row+2; i++)
        	System.out.printf("%2s", WALL_BLOCK+WALL_BLOCK + WALL_BLOCK);
        
        System.out.print("\n");
        
        // print each row representing the maze
        for (int i = 0; i < col; i++)
        {
        	// print left border wall
        	System.out.printf("%3s",WALL_BLOCK+WALL_BLOCK + WALL_BLOCK);
            
        	// print cells in maze bounds
            for (int j = 0; j < row; j++)
            {
            	// if starting room, print an S
            	if(cells[j][i].getRoomNum() == 0)
            		System.out.printf("%3s", "S ");
            	
            	// if the last room, print an E
            	else if(cells[j][i].getRoomNum() == numRooms-1)
            		System.out.printf("%3s", "E ");
            	
            	// if a wall, print wall block character
            	else if(cells[j][i].isTypeRoom() == isWall)
            		System.out.printf("%3s", WALL_BLOCK+WALL_BLOCK + WALL_BLOCK);
               	
            	// if a room, print a blank space
            	else if(cells[j][i].isTypeRoom() == isRoom)
            		System.out.printf("%3s", " " );
               		
            } // end for x < width 
            
            // print right border wall
            System.out.printf("%3s",WALL_BLOCK + WALL_BLOCK + WALL_BLOCK );
            System.out.print("\n");
            
        } // end for y < height
         
        // print last border wall
        for(int i = 0; i < row+2; i++)
        	System.out.printf("%3s",WALL_BLOCK+ WALL_BLOCK + WALL_BLOCK);
        
        System.out.println();
        
        
    } // end printMaze
       
    public Space[][] getMap()
   	{
   		return cells;
   	}
    
    public int getNumRooms()
	{
		return numRooms;
	}
 
    public boolean isGameEnd()
   	{
   		return gameEnd;
   	}

   	public void setGameEnd(boolean gameEnd)
   	{
   		this.gameEnd = gameEnd;
   	}
   	
       public int getRow()
   	{
   		return row;
   	}

   	public void setRow(int row)
   	{
   		this.row = row;
   	}

   	public int getCol()
   	{
   		return col;
   	}

   	public void setCol(int col)
   	{
   		this.col = col;
   	}
    public static void main(String[] args)
    {
    	//RandomMazeClass myMaze = new RandomMazeClass(15,15);
    	
    	//myMaze.printMaze(); // print the maze to console
    }
    
}