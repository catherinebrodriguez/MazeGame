// Catherine Rodriguez, Reem Alharbi, Alexandria Brelsford
// maze display renderer to colorize and set style for each cell type
// 12/5/2017
// data structures 501

package mazeProject;
 
import javax.swing.JLabel;
import javax.swing.JTable; 
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class CustomRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Space[][] map;  // the list of rooms/walls in the maze
	private RandomMazeClass maze; // the mazeclass
	 
	public CustomRenderer()
	{
		maze = null;
		map = null;
		
	}
	
	public CustomRenderer(RandomMazeClass maze, Space[][] map, boolean gameEnd)
	{
		this.maze = maze;
		this.map = map;
		 
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(
	 * javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col)
	{  
		Component rendererComp = super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, col);
 
		// Set alignment and design of cells
		rendererComp.setForeground(Color.white);
		setHorizontalAlignment(JLabel.CENTER); 
		setVerticalAlignment(JLabel.CENTER); 
		  
		table.setCellSelectionEnabled(true);

		// set keys at each quarter section of the maze
		int keyOnePos = (int) ((maze.getNumRooms()-1)*.25);
		int keyTwoPos = (int) ((maze.getNumRooms()-1)*.50);
		int keyThrPos = (int) ((maze.getNumRooms()-1)*.75);
		// if cell is a border wall, disable selection and paint wall color
		if ((col == 0 || col == table.getColumnCount() - 1) 
				|| (row == 0 || row == table.getRowCount() - 1))
		{
			//table.setCellSelectionEnabled(false);
			 
				rendererComp.setBackground(new Color(255-row, 204, 255));
		}
		// else if the start or end cell in the maze bounds
		else if((row >= 1 && col >= 1) 
			&& (row <  table.getRowCount()-1 && col < table.getColumnCount()-1))
		{ 
			if(map[col-1][row-1].getRoomNum() == 1)
			{
				rendererComp.setBackground(Color.magenta);
				setText("S");
			}
			// end	
        	else if(map[col-1][row-1].getRoomNum() == maze.getNumRooms()-1)
        	{
        		rendererComp.setBackground(Color.magenta);
        		//maze.setGameEnd(true);
        		setText("E");
        	}
			// set the keys in the maze at quarter intervals
        	else if(map[col-1][row-1].getRoomNum() == keyOnePos)
        	{
        		rendererComp.setBackground(new Color(245, 245, 245));
        		rendererComp.setForeground(Color.magenta);
        		map[col-1][row-1].setRoomText("K1");
        		setText("K1");
        	}
        	else if(map[col-1][row-1].getRoomNum() == keyTwoPos)
        	{
        		rendererComp.setBackground(new Color(245, 245, 245));
        		rendererComp.setForeground(Color.magenta);
        		map[col-1][row-1].setRoomText("K2");
        		setText("K2");
        	}
        	else if(map[col-1][row-1].getRoomNum() == keyThrPos)
        	{
        		rendererComp.setBackground(new Color(245, 245, 245));
        		rendererComp.setForeground(Color.magenta);
        		map[col-1][row-1].setRoomText("K3");
        		//table.setValueAt(map[col-1][row-1], row, col);
        		setText("K3");
        	}
			// else if an interior wall cell, disable selection
        	else if(map[col-1][row-1].getRoomNum() == -1)
			{

    				rendererComp.setBackground(new Color(255-row, 204, 255));
        	 
			}
			// else an empty room cell
			else
			{ 
				rendererComp.setForeground(Color.magenta);
				rendererComp.setBackground(new Color(245, 245, 245));
				//setText(map[col-1][row-1].getRoomNum() + "");
			}
			// if the current cell is selected by player, paint cell blue
			if(table.isCellSelected(row, col))
        	{ 
				 if((map[col-1][row-1].getRoomNum() == maze.getNumRooms()-1))
				 {	 
					// System.out.println("END!");	
					 maze.setGameEnd(true);
					 
				 }
				// color current selected cell blue where player is
				rendererComp.setBackground(new Color(10, 204, 255));
        	}
			 
		} // end if in maze bounds
		 
		 
		return rendererComp;

	} // end Component getTableCellRendererComponent

} // end CustomerRenderer
