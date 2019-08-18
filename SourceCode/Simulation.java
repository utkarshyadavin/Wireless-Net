package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.lang.Math;
import java.io.PrintStream;
import java.text.DecimalFormat; 
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

class Simulation{
	public static Set<UE> setUE = new HashSet<UE>();
	public static Set<BS> setBS = new HashSet<BS>();
	
	public static void leastDistanceUEAssociation(){ 
		 //Shortest distance based association of UE to BS
		for(UE ue:setUE){   
			BS targetBS = null;
			double minDist = 9999999;
			for(BS bs:setBS){
				double dist = bs.location.distance(ue.location);
				if(dist<=minDist){
					minDist = dist;
					targetBS = bs;
				}
			}
			ue.target = targetBS;
			targetBS.associatedUEs.add(ue);
		}
	}

	public static void maxRsrpUEAssociation(){
		 //Max RSRP based association of UE to BS
		for(UE ue:setUE){   
			BS targetBS = null;
			double rsrp = -1;
			for(BS bs:setBS){
				double dist = bs.location.distance(ue.location);
				double power = (double)Parameters.MAC_POWER/(double)Parameters.SUBCHANNELS;
				power = (double)power/(double)(dist*dist);
				if(power>=rsrp){
					rsrp = power;
					targetBS = bs;
				}
			}
			ue.target = targetBS;
			targetBS.associatedUEs.add(ue);
		}
	}

	public static void maxBitRateUEAssociation(){
		// Max received bit rate based association
		
		for(UE ue:setUE){    //Shortest distance based association of UE to BS
			BS targetBS = null;
			double maxBitrate = -1;
			for(BS bs:setBS){
				double dist = bs.location.distance(ue.location);
				int count = bs.associatedUEs.size() + 1;  // total count of already connected UEs + 1
				double bandwidth = Parameters.BANDWIDTH;
				double signal = (double)Parameters.MAC_POWER/(double)Parameters.SUBCHANNELS;
				double interference = 0.0;
				for(BS bst:setBS){
					if(bst.id!=bs.id){
						double bsDist = bst.location.distance(ue.location);
						interference = interference + signal/(bsDist*bsDist);
					}
				}
				signal = signal/(dist*dist);
				// Shanon Equation   Bitrate = B*log(1 +  S/(I+N))
				double sinr = signal/(interference + 0.000001);
				double bitrate = bandwidth*((Math.log(1 + sinr))/1.44269); // log here is of base e
				// The above bit rate is for one subchannel
				int totalChannels = Parameters.SUBCHANNELS/count;
				bitrate = bitrate*totalChannels;
				if(bitrate>=maxBitrate){
					maxBitrate = bitrate;
					targetBS = bs;
				}
			}
			ue.target = targetBS;
			targetBS.associatedUEs.add(ue);
		}
	}

	public static void clearAssociation(){ // Method clears all existing associations
		for(UE ue:setUE){   
			ue.target = null;
		}

		for(BS bs:setBS){   
			bs.associatedUEs.clear();
		}
	}


	public static void printAssociationDetails(String message){ // Prints details about UE and BS connections along 
		// with bitrate and SINR values
		try{
			PrintStream fileOut = new PrintStream(new BufferedOutputStream(new FileOutputStream("./result.txt", true)));
			System.setOut(fileOut);
			System.out.println("\n                                      " + message + 
				"                                  \n" );
			System.out.println("Printing UE Infromation\n");
			DecimalFormat numberFormat = new DecimalFormat("#.000");
			double c = 0; 
			for(UE ue:setUE){
				BS connectedBS = ue.target;	
				double dist = connectedBS.location.distance(ue.location);
				int count = connectedBS.associatedUEs.size();
				double bandwidth = Parameters.BANDWIDTH;
				double signal = (double)Parameters.MAC_POWER/(double)Parameters.SUBCHANNELS; 
				double interference = 0.0;
				for(BS bst:setBS){
					if(bst.id!=connectedBS.id){
						double bsDist = bst.location.distance(ue.location);
						interference = interference + (double)signal/(double)(bsDist*bsDist);
					}
				}
				signal = (double)signal/(double)(dist*dist);
				double sinr = signal/(interference + 0.000001);
				double bitrate = bandwidth*((Math.log(1 + sinr))/1.44269); // Base of log is e
				int numberOfSubchannels = Parameters.SUBCHANNELS/count; 
				bitrate = numberOfSubchannels*bitrate;
				bitrate = bitrate/(double)1000000; // bit rate in Mbps
				c = c + bitrate;
				System.out.println("UE id: " + ue.id + " X: " + numberFormat.format(ue.location.getX()) + " Y: " 
					+ numberFormat.format(ue.location.getY()) + " Bs id: " + ue.target.id + " BitRate: " +
					numberFormat.format(bitrate) + " mbps SINR: " + numberFormat.format(sinr));
			}
			System.out.println("Total  bit rate : " + c);
			System.out.println("\nPrinting BS Infromation\n");
			for(BS bs:setBS){
				System.out.println("BS id: " + bs.id + " X: " + numberFormat.format(bs.location.getX()) + " Y: " + 
					numberFormat.format(bs.location.getY()) + " Connected UEs: [ ");
				for(UE ue:bs.associatedUEs)
					System.out.print(ue.id+ " ");
					System.out.println(" ]");
			}
			fileOut.close();
		}catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
	}

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
		leastDistanceUEAssociation(); // Associate based on least distance
		printAssociationDetails("Least Distance Based Association"); // Print association details
		clearAssociation(); // Clear associations for another criteria
		maxRsrpUEAssociation();
		printAssociationDetails("Max RSRP Based Association");
		clearAssociation();
		maxBitRateUEAssociation();
		printAssociationDetails(" Max Bit Rate Based Association");
		clearAssociation();
	}
}