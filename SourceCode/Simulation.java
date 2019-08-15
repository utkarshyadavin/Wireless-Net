package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

class Simulation{
	public static Set<UE> setUE = new HashSet<UE>();
	public static Set<BS> setBS = new HashSet<BS>();
	
	public static void main(String args[]){

		for(int i=0; i<100; i++){  // Creating 100 UE objects in 200X200 area
			double randomX = ThreadLocalRandom.current().nextDouble(-100, 100);
			double randomY = ThreadLocalRandom.current().nextDouble(-100, 100);
			Point2D location = new Point2D.Double(randomX, randomY);
			UE ue = new UE(i, location);
			setUE.add(ue);
		}

		for(int i=0; i<20; i++){  // Creating 20 BS objects in 200X200 area
			double randomX = ThreadLocalRandom.current().nextDouble(-100, 100);
			double randomY = ThreadLocalRandom.current().nextDouble(-100, 100);
			Point2D location = new Point2D.Double(randomX, randomY);
			BS bs = new BS(i, location);
			setBS.add(bs);
		}

		for(UE ue:setUE){    //Shortest distance based association of UE to BS
			BS targetBS = null;
			double maxPower = -1;
			for(BS bs:setBS){
				double dist = bs.location.distance(ue.location);
				int count = bs.associatedUEs.size() + 1; 
				double power = (bs.power)/(count*dist*dist);
				if(power>=maxPower){
					maxPower = power;
					targetBS = bs;
				}
			}
			ue.target = targetBS;
			ue.receivedPower = maxPower;
			targetBS.associatedUEs.add(ue);
		}
		
		System.out.println("Printing UE Infromation");
		System.out.println();
		System.out.println("UE id" + "            " + "Location" + "                                             "
		 + "Target BS" + "        " + "Received Power");
		for(UE ue:setUE){
			System.out.println(ue.id + "       " + ue.location + "             " + ue.target.id + "       " 
				+ ue.receivedPowerls);
		}	

		System.out.println("\nPrinting BS Infromation\n");
		System.out.println("BS id" + "      " + "Location" + "                                                   " + "Associated UEs");
		for(BS bs:setBS){
			System.out.print(bs.id + "         " + bs.location + "  " + "           [");
			for(UE ue:bs.associatedUEs)
				System.out.print(ue.id+ " ");
				System.out.println("]");
		}
	}
}