// Catherine Rodriguez, Reem Alharbi, Alexandria Brelsford
// custom method for the jtable tablemodel 
// to allow setting custom row/col size and placing objects in cells
// 12/5/2017
// data structures 501

package mazeProject;

import javax.swing.table.DefaultTableModel;

class MyTableModel extends DefaultTableModel  
{ 
	private static final long serialVersionUID = 1L;
	 
    private Object[][] data;// = ...//same as before...

    
	public MyTableModel(int i, int j)
	{
		setRowCount(i);
		setColumnCount(j);
		 
	}
 
 // make all cells non-editable
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
	 public void setValueAt(Object value, int row, int col) 
	 {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	 }
}