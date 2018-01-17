// Catherine Rodriguez, Reem Alharbi, Alexandria Brelsford
// maze driver class to initialize and set up display for maze
// 12/5/2017
// data structures 501

package mazeProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.*;

import net.miginfocom.swing.MigLayout;
 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;

public class MazeGame
{
	private JFrame frame;   // outer frame of gui
	
	private JTable table;   // table to store maze
	
	private JPanel panel;
	private JPanel mazeMap;
	
	private JButton btnRestart;
	private JButton btnStart;
	
	private JButton btnEasy;
	private JButton btnMedium;
	private JButton btnHard;
	
	private JLabel lblTop;
	private RandomMazeClass newMaze;
	private Space[][] map;
	
	private boolean keyOne;
	private boolean keyTwo;
	private boolean keyThr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// initialize a maze
					MazeGame window = new MazeGame();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MazeGame()
	{
		// set all keys found in maze as false - not found

		generateMaze();
		generateTable();
		initialize();
	}

	public void generateMaze()
	{
		keyOne = false;
		keyTwo = false;
		keyThr = false;
		newMaze = new RandomMazeClass(21, 21);
		map = newMaze.getMap(); // list of rooms

	} // end generateMaze

	public void generateTable()
	{
		// create table to display maze
		// (should be mazeSize+2 to include borders, not static 23)
		table = new JTable(new MyTableModel(23, 23))
		{
			// add default serialID to remove warning
			private static final long serialVersionUID = 1L;

			// override the changeSelection method to allow user to only
			// select valid rooms within the maze bounds that are 1 away from
			// the currently selected cell
			@Override
			public void changeSelection(int row, int col, boolean toggle,
					boolean expand)
			{

				// get the currently selected cell position values
				int currRow = table.getSelectedRow();
				int currCol = table.getSelectedColumn();

				if ((row >= 1 && col >= 1) && (row < table.getRowCount() - 1
						&& col < table.getColumnCount() - 1))
				{
					if (map[col - 1][row - 1].getRoomText() == "K1")
					{	
						keyOne = true;
						map[col - 1][row - 1].setRoomText("");
					}
					if (map[col - 1][row - 1].getRoomText() == "K2")
					{
						keyTwo = true;
					}
					if (map[col - 1][row - 1].getRoomText() == "K3")
					{
						keyThr = true;
					}
				}
				if ((row >= 1 && col >= 1)
						&& (row < table.getRowCount() - 1
								&& col < table.getColumnCount() - 1)
						&& map[col - 1][row - 1]
								.getRoomNum() == newMaze.getNumRooms() - 1)
				{

					if (keyOne && keyTwo && keyThr)
						winGame();

				}
				// if new space selection is a valid, contiguous space in maze
				// then update to the new selected cell position
				if ((row >= 1 && col >= 1) && (row < table.getRowCount() - 1
						&& col < table.getColumnCount() - 1))
					if ((map[col - 1][row - 1].getRoomNum() != -1)
							&& ((Math.abs(currRow - row) == 1
									|| Math.abs(currRow - row) == 0)
									&& (Math.abs(currCol - col) == 1
											|| Math.abs(currCol - col) == 0)))
						super.changeSelection(row, col, toggle, expand);
 
			}  // end changeSelection

		}; // end table cell selection configuration
		table.setBackground(new Color(255, 255, 255));
		table.setFont(new Font("Segoe UI Historic", Font.PLAIN, 18));

		// set table selection to single cell selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// start at cell 1,1 in table (start position)
		table.setRowSelectionInterval(1, 1);
		table.setColumnSelectionInterval(1, 1);
		table.setUpdateSelectionOnSort(false);
		table.setRowSelectionAllowed(false);
		table.setIgnoreRepaint(false);

		// set table cell design, height, margins
		table.setForeground(Color.WHITE);
		table.setRowHeight(30);
		table.setIntercellSpacing(new Dimension(0, 0));

		// set the column width size for each column in table
		for (int i = 0; i < 23; i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(30);
			table.getColumnModel().getColumn(i).setCellRenderer(
					new CustomRenderer(newMaze, map, newMaze.isGameEnd()));

		}

	} // end generateTable

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// create new maze on game load
		// (using size 21 now but should allow user to choose difficulty)
		//Font btnFont = new Font("Segoe UI Historic", Font.PLAIN, 28);
		lblTop = new JLabel("ESCAPE THE MAZE!");
		 
		// main application frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(new Color(90, 80, 150));
		frame.setTitle("Escape the Maze!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("wrap 1", "[grow,fill]", "[grow,fill]"));

		// main panel to display game contents
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBackground(new Color(90, 80, 150));
		panel.setLayout(new MigLayout("wrap 1", "[grow][grow,fill]",
				"[grow][grow,fill][grow,fill]"));

		// panel to display maze
		mazeMap = new JPanel();
		mazeMap.setBackground(new Color(90, 80, 150));
		mazeMap.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		// add maze map table to maze panel
		mazeMap.add(table, "cell 0 0,alignx center,aligny center");

		// add restart button to generate new maze
		btnRestart = new JButton("New Maze");
		btnRestart.setVisible(false);
		btnRestart.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// remove old maze
				mazeMap.remove(table);
				// create new maze and add to table
				generateMaze();
				generateTable();
				// reset title
				lblTop.setText("ESCAPE THE MAZE!");
				// add new maze to panel
				mazeMap.add(table);
				table.setIntercellSpacing(new Dimension(0, 0));
				// set focus back to table from button
				btnRestart.transferFocusBackward();
			}

		}); // end btnStart mouseListener

		// start button for title screen
		btnStart = new JButton("Start!");
		btnStart.setFont(new Font("Segoe UI Historic", Font.PLAIN, 28));
		btnStart.setMargin(new Insets(20, 20, 20, 20));
		btnStart.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{ 
				btnStart.setVisible(false);
				btnRestart.setVisible(true);
				table.setVisible(true);
				panel.add(mazeMap, "cell 0 1,alignx center");
				panel.add(btnRestart, "cell 0 2,alignx center");
				for (Component comp : panel.getComponents())
				{
					// remove text focus border and add hover effect
					if (comp instanceof JButton)
					{
						((JButton) comp).setFocusPainted(false);
						createNewButton((JButton) comp);
					}
				} // end for each button in buttons
				panel.remove(btnStart);
				btnRestart.transferFocusBackward();
				// System.out.print(table.getValueAt(1, 1));
				frame.pack();
				frame.setLocationRelativeTo(null);
			}

		}); // end btnStart mouseListener

		// player info will go here add ....
		JLabel lblPlayerName = new JLabel("PLAYER!");

		lblPlayerName.setFont(new Font("Calibri", Font.BOLD, 28));
		JLabel lblScore = new JLabel("Score is 20");

		// information panel to display player info and buttons
		JPanel displayText = new JPanel();
		displayText.setLayout(
				new MigLayout("wrap 2", "[grow,fill]", "[grow,fill]"));

		// add info buttons/labels to display panel
		displayText.add(lblPlayerName);
		displayText.add(lblScore);

		lblTop.setFont(new Font("Segoe UI Historic", Font.BOLD, 28));
		lblTop.setForeground(Color.white);
		panel.add(lblTop, "cell 0 0,alignx center");

		// table initially not visible to screen
		table.setVisible(false);
		// add maze and info panels to main panel
		panel.add(btnStart, "cell 0 1,alignx center");
		// btnStart.setMargin(new Insets(20, 20, 20, 20));
		for (Component comp : panel.getComponents())
		{
			// remove text focus border and add hover effect
			if (comp instanceof JButton)
			{
				((JButton) comp).setFocusPainted(false);
				createNewButton((JButton) comp);
			}
		} // end for each button in buttons

		// add panel to frame and size to screen
		frame.getContentPane().add(panel, "cell 0 0,growx,aligny top");
		frame.pack();
		frame.setLocationRelativeTo(null);

	} // end initialize

	// function to add same styling to all buttons
	public void createNewButton(JButton btn)
	{
		Font btnFont = new Font("Segoe UI Historic", Font.BOLD, 28);

		btn.setForeground(new Color(255, 255, 255));
		btn.setBackground(new Color(246, 61, 162));
		btn.setFont(btnFont);
		btn.setMargin(new Insets(10, 20, 10, 20));
		btn.setBorderPainted(false);
		 
		btn.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(java.awt.event.MouseEvent e)
			{
				JButton button = (JButton) e.getComponent();
				button.setBackground(new Color(230, 10, 130));

			}

			public void mouseExited(java.awt.event.MouseEvent e)
			{
				JButton button = (JButton) e.getComponent();
				button.setBackground(new Color(246, 61, 162));
			}
		});

	} // end createNewButton

	// function to inform user they won the game
	public void winGame()
	{
		lblTop.setText("YOU WON!");

		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
	}

} // end MazeGame
