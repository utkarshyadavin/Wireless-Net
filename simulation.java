import java.util.*;

class BaseStation{

	int xCoordinate; 
	int yCoordinate;
	int id; 
	ArrayList<Integer> connectedUserEquipmentIds; 

	public BaseStation(){
		this.xCoordinate = 0; 
		this.yCoordinate = 0; 
		this.id = -1;
		this.connectedUserEquipmentIds = new ArrayList<Integer>;
	}

	// Get BaseStation Id
	public int getId(){
		return this.id;
	}

	// Set BaseStation Id
	public void setId(int value){
		this.id = value;
	}

	// Get X coordinate of BaseStation
	public int getxCoordinate(){
		return this.xCoordinate; 
	}

	// Set X coordinate of BaseStation
	public void setxCoordinate(int value){
		this.xCoordinate = value;
	}

	// Get Y coordinate of BaseStation
	public int getyCoordinate(){
		return this.yCoordinate;
	}

	// Set Y coordinate of BaseStation
	public void setyCoordinate(int value){
		this.yCoordinate = value;
	}

	// Add UserEuipment to the list of connected devices
	public void addUserEquipment(int id){
		connectedUserEquipmentIds.add(id);
	}

	// Get the list of connected User Equipments;
	public ArrayList<Integer> getConnectedUserEquipmentIds(){
		return this.connectedUserEquipmentIds; 
	}

}



class UserEquipment{

	int xCoordinate; 
	int yCoordinate;
	int id; 
	int assignedBaseStationId;

	public UserEquipment(){
		this.xCoordinate = 0; 
		this.yCoordinate = 0; 
		this.id = -1; 
		this.assignedBaseStationId = -1;
	}

	// Get UserEquipment Id
	public int geId(){
		return this.id;
	}

	// Set UserEquipment Id
	public void setId(int value){
		this.id = value;
	}

	// Get X coordinate of UserEquipment
	public int getxCoordinate(){
		return this.xCoordinate; 
	}

	// Set X coordinate of UserEquipment
	public void setxCoordinate(int value){
		this.xCoordinate = value;
	}

	// Get Y coordinate of UserEquipment
	public int getyCoordinate(){
		return this.yCoordinate;
	}

	// Set Y coordinate of UserEquipment
	public void setyCoordinate(int value){
		this.yCoordinate = value;
	}

	// Get the id of the assigned base station the the User Equipment
	public int getassignedBaseStationId(){
		return this.assignedBaseStationId;
	}

	// Assigne a particular base station id to a User Equipment
	public void setassignedBaseStationId(int value){
		this.assignedBaseStationId = value;
	}

}


class Simulation{

}