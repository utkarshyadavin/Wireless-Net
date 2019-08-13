import java.util.*;

class BaseStation{

	int xCoordinate; 
	int yCoordinate;
	int id; 
	ArrayList<Integer> connectedUEIds; 

	public BaseStation(){
		this.xCoordinate = 0; 
		this.yCoordinate = 0; 
		this.id = -1;
		this.connectedUEIds = new ArrayList<Integer>();
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
		connectedUEIds.add(id);
	}

	// Get the list of connected User Equipments;
	public ArrayList<Integer> getConnectedUEIds(){
		return this.connectedUEIds; 
	}

}



class UserEquipment{

	int xCoordinate; 
	int yCoordinate;
	int id; 
	int assignedBSId;

	public UserEquipment(){
		this.xCoordinate = 0; 
		this.yCoordinate = 0; 
		this.id = -1; 
		this.assignedBSId = -1;
	}

	// Get UserEquipment Id
	public int getId(){
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
	public int getassignedBSId(){
		return this.assignedBSId;
	}

	// Assigne a particular base station id to a User Equipment
	public void setassignedBSId(int value){
		this.assignedBSId = value;
	}

}


class Simulation{

	// Generates a random number between specified max and min values
	public static int generateRandom(int max, int min){
		int temp = (int) (Math.random()*(max - min + 1) + min);
		return temp;
	}


	public static void main(String args[]){

		System.out.println("Ruuning Cellular Wireless Network Simulation");
		// Maps to keep track of coordinates of BSTs and UEs to avoid repetition
		HashMap<Integer, Integer> baseStationPosition = new HashMap<>();
		HashMap<Integer,Integer> userEquipmentPosition = new HashMap<>();


		// Create 50 Base Stations with ids ranging from 1 to 50
		BaseStation baseStations[] = new BaseStation[50]; 
		for(int i =0; i<50; i++){
			baseStations[i] = new BaseStation(); 
			baseStations[i].setId(i+1);

			// Randomly generating x and y coordinates
			int xCoordinate = generateRandom(100, -100);
			int yCoordinate = generateRandom(100, -100);
			
			// Check for unique x and y co-ordinates
			while(baseStationPosition.containsKey(xCoordinate)){
				Integer temp = baseStationPosition.get(xCoordinate);
				if(temp == yCoordinate){
					xCoordinate = generateRandom(100, -100);
					yCoordinate = generateRandom(100, -100);
				}else{
					break;
				}
			}

			baseStationPosition.put(xCoordinate, yCoordinate);
			baseStations[i].setxCoordinate(xCoordinate);
			baseStations[i].setyCoordinate(yCoordinate);	
		}


		// Creating 100 User Equipments with ids ranging from 1 to 100
		UserEquipment userEquipments[] = new UserEquipment[100];
		for(int i =0; i<100; i++){
			userEquipments[i] = new UserEquipment();
			userEquipments[i].setId(i+1);

			// Randomly generating x and y coordinates
			int xCoordinate = generateRandom(100, -100);
			int yCoordinate = generateRandom(100, -100);
			
			// Check for unique x and y co-ordinates
			while(userEquipmentPosition.containsKey(xCoordinate)){
				Integer temp = userEquipmentPosition.get(xCoordinate);
				if(temp == yCoordinate){
					xCoordinate = generateRandom(100, -100);
					yCoordinate = generateRandom(100, -100);
				}else{
					break;
				}
			}

			userEquipmentPosition.put(xCoordinate, yCoordinate);
			userEquipments[i].setxCoordinate(xCoordinate);
			userEquipments[i].setyCoordinate(yCoordinate);
		}

		// Printing out details of created BaseStations
		System.out.println("Created BaseStation Details:");
		for(int i=0; i<50 ; i++){
			BaseStation b = baseStations[i]; 
			System.out.println("BS Id: " + b.getId() + " at (" + b.getxCoordinate() + "," +
			  + b.getyCoordinate() + ")");
		}

		// Printing out details of created UserEquipments
		System.out.println();
		System.out.println();

		System.out.println("Created UserEquipment Details:");
		for(int i=0; i<100 ; i++){
			UserEquipment u = userEquipments[i]; 
			System.out.println("UE Id: " + u.getId() + " at (" + u.getxCoordinate() + "," +
			  + u.getyCoordinate() + ")");
			
		}


	}



}