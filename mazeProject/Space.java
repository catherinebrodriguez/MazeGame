// Catherine Rodriguez, Reem Alharbi, Alexandria Brelsford
// Space class to define attributes for a cell in a maze as a room or wall
// 12/5/2017
// data structures 501

package mazeProject;

public class Space
{
	 private boolean typeRoom; // type of room (false==wall / true==room)
	 private int roomNum;      // room number in the maze
	 private String roomText;  // if room displays text
	 
	 public Space()
	 {
		 this.typeRoom = false; // space initially a wall
		 this.roomNum = -1;     // -1 signifies a wall
		 this.roomText = "";
	 }
	 
	 public Space(boolean typeRoom, int roomNum)
	 {
		 this.typeRoom = typeRoom;
		 this.roomText = "";
		 
		 // if a room, give room number, else wall is -1
		 if(typeRoom == true)
			 this.roomNum = roomNum;
		 else
			 this.roomNum = -1;

	 }
	public boolean isTypeRoom()
	{
		return typeRoom;
	}

	public void setTypeRoom(boolean typeRoom)
	{
		this.typeRoom = typeRoom;
	}

	public int getRoomNum()
	{
		return roomNum;
	}

	public void setRoomNum(int roomNum)
	{
		this.roomNum = roomNum;
	}

	public String getRoomText()
	{
		return roomText;
	}

	public void setRoomText(String roomText)
	{
		this.roomText = roomText;
	}
}
