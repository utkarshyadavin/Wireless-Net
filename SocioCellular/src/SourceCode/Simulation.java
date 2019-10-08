package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader; 
import java.util.Scanner;	
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {
	private Set<UE> setUE = new HashSet<UE>();
	private Set<BS> setBS = new HashSet<BS>();
	private ArrayList<HashSet<Integer>> operatorCollaboration =  
		new ArrayList<HashSet<Integer>>();
	private ArrayList<HashSet<Integer>> socialCollaboration = 
		new ArrayList<HashSet<Integer>>();
	private UE ueIDs [] = new UE [Params.TOTALUES];
	private Set<Integer> randomIDs = new HashSet<Integer>();


	public void init(String UEFile, String BSFile) {
		int id = 0;
		try {    // Creating UE objects from UEFile
			Scanner s1 = new Scanner(new File(UEFile));
			while (s1.hasNextLine()) {
				String [] data = s1.nextLine().split(" ");
				Point2D loc = new Point2D.Double(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
				UE u = new UE(id,loc,Integer.parseInt(data[2]));
				ueIDs[id] = u;
				setUE.add(u);
				id++;
			}
			s1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {     // Creating BS objects from BSFile
			Scanner s2 = new Scanner(new File(BSFile));
			id = 0;
			while (s2.hasNextLine()) {
				String [] data = s2.nextLine().split(" ");
				Point2D loc = new Point2D.Double(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
				int randomID = ThreadLocalRandom.current().nextInt(0, 500);
                while(randomIDs.contains(randomID)){
                    randomID = ThreadLocalRandom.current().nextInt(0, 500);
                }
                randomIDs.add(randomID);           // Assigning a random owner to a BS
                BS b = new BS(id,loc,Integer.parseInt(data[2]),ueIDs[randomID]);                             
				setBS.add(b);
				id++;
			}
			s2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	

		for(int i=0; i<Params.TOTALOPERATORS; i++){      // Collaborative Operators
            HashSet<Integer> tempSet = new HashSet<Integer>();
                int count = Params.TOTALOPERATORCOLLABORATION; 
                for(int j=0; j<count; j++){
                    int randomopId =  ThreadLocalRandom.current().nextInt(0, Params.TOTALOPERATORS);
                        while(randomopId==i || tempSet.contains(randomopId)){
                                 randomopId =  ThreadLocalRandom.current().nextInt(0, Params.TOTALOPERATORS);
                         }
                         tempSet.add(randomopId);
                }
                operatorCollaboration.add(tempSet);
        }

        for(int i=0; i<Params.TOTALUES; i++){               // Socially Collaborative UEs
            HashSet<Integer> tempSet = new HashSet<Integer>();
                  int count = Params.TOTALSOCIALCOLLABORATION; 
                  for(int j=0; j<count; j++){
                       int randomueId =  ThreadLocalRandom.current().nextInt(0, Params.TOTALUES);
                       while(randomueId==i || tempSet.contains(randomueId)){
                            randomueId =  ThreadLocalRandom.current().nextInt(0, Params.TOTALUES);
                       }
                        tempSet.add(randomueId);
                  }
                  socialCollaboration.add(tempSet);
        }
                
	}
	
	public void associationRSRP() {
		resetStats();
		for(UE u:setUE) {								// Max RSRQ based association without operator collaboration
			double maxRSRP = -999999;
			BS targetBS=null;
			for(BS b:setBS) {
				if((u.opID != b.opID) || b.associatedUEs.size()>=10)
					continue;
				double dist = b.location.distance(u.location);
				double signal = Params.FEMTOPOWER/Math.pow(dist, 2);
				double interference = 0;
				for(BS bi:setBS) {
					if(bi.opID != b.opID && bi.bsID==b.bsID)
						continue;
					dist = bi.location.distance(u.location);
					interference+=Params.FEMTOPOWER/Math.pow(dist, 2);
				}

				double sinr = signal/(interference+Params.NOISE);
				if(signal>=maxRSRP) {
					maxRSRP=signal;
					targetBS=b;
					u.sinr = sinr;
				}
			}
			u.target=targetBS;
			if(u.target!=null)
				targetBS.associatedUEs.add(u);
		}
		
	}
	
	public void associationRSRPCollaboration() {
		resetStats();
		for(UE u:setUE) {								// Max RSRQ based association with (full/all) operator collaboration
			double maxRSRP = -999999;
			BS targetBS=null;
			for(BS b:setBS) {
				if(b.associatedUEs.size()>=10)
					continue;
				double dist = b.location.distance(u.location);
				double signal = Params.FEMTOPOWER/Math.pow(dist, 2);
				double interference = 0;
				for(BS bi:setBS) {
					if(bi.opID != b.opID && bi.bsID==b.bsID)
						continue;
					dist = bi.location.distance(u.location);
					interference+=Params.FEMTOPOWER/Math.pow(dist, 2);
				
				}

				double sinr = signal/(interference+Params.NOISE);
				if(signal>=maxRSRP) {
					maxRSRP=signal;
					targetBS=b;
					u.sinr = sinr;
				}
			}
			u.target=targetBS;
			if(u.target!=null)
				targetBS.associatedUEs.add(u);
		}
		
	}
	

 public void associationRSRPPartialOperatorCollaboration() {
		resetStats();
		for(UE u:setUE) {								// Max RSRQ based association with (full/all) operator collaboration
			double maxRSRP = -999999;
			BS targetBS=null;
			for(BS b:setBS) {
                            
                                HashSet<Integer> temp = operatorCollaboration.get(b.opID);
                                boolean status = true && (u.opID !=b.opID);
                                for(int t : temp){
                                    status = status && (u.opID != t);
                                }
				if(status || b.associatedUEs.size()>=10)
					continue;
				double dist = b.location.distance(u.location);
				double signal = Params.FEMTOPOWER/Math.pow(dist, 2);
				double interference = 0;
				for(BS bi:setBS) {
					if(bi.opID != b.opID && bi.bsID==b.bsID)
						continue;
					dist = bi.location.distance(u.location);
					interference+=Params.FEMTOPOWER/Math.pow(dist, 2);
				
				}

				double sinr = signal/(interference+Params.NOISE);
				if(signal>=maxRSRP) {
					maxRSRP=signal;
					targetBS=b;
					u.sinr = sinr;
				}
			}
			u.target=targetBS;
			if(u.target!=null)
				targetBS.associatedUEs.add(u);
		}
		
	}
	
   public void associationRSRPSocialPartialOperatorCollaboration() {
		resetStats();
		for(UE u:setUE) {								// Max RSRQ based association with (full/all) operator collaboration
			double maxRSRP = -999999;
			BS targetBS=null;
			for(BS b:setBS) {
                            
                                HashSet<Integer> tempOperator = operatorCollaboration.get(b.opID);
                                boolean statusOperator = true && (u.opID !=b.opID);
                                for(int t : tempOperator){
                                    statusOperator = statusOperator && (u.opID != t);
                                }
                                HashSet<Integer> tempSocial = socialCollaboration.get(b.owner.uID);
                                boolean statusSocial = true && (u.uID !=b.owner.uID);
                                for(int t : tempSocial){
                                    statusSocial = statusSocial && (u.uID != t);
                                }
				if(statusOperator || statusSocial || b.associatedUEs.size()>=10)
					continue;
				double dist = b.location.distance(u.location);
				double signal = Params.FEMTOPOWER/Math.pow(dist, 2);
				double interference = 0;
				for(BS bi:setBS) {
					if(bi.opID != b.opID && bi.bsID==b.bsID)
						continue;
					dist = bi.location.distance(u.location);
					interference+=Params.FEMTOPOWER/Math.pow(dist, 2);
				
				}

				double sinr = signal/(interference+Params.NOISE);
				if(signal>=maxRSRP) {
					maxRSRP=signal;
					targetBS=b;
					u.sinr = sinr;
				}
			}
			u.target=targetBS;
			if(u.target!=null)
				targetBS.associatedUEs.add(u);
		}
		
	}

	private void resetStats() {
		for(UE u:setUE) {	
			u.sinr = -1;
			u.target = null;
		}
		for(BS b:setBS) {
			b.associatedUEs.clear();
		}
		
	}
	
	public void printAllInfo(int mode) {
		int blocked=0;
		if(mode == 0) {
		System.out.println("Printing UE Information\n");
		for(UE u:setUE) {
			if(u.target!=null)
			System.out.println("UE id: "+u.uID+" Operator: "+u.opID+" Target BS: "+u.target.bsID+" SINR: "+u.sinr);
		
		}
		
		System.out.println("\nPrinting BS Information\n");
		for(BS b:setBS) {
			System.out.print("BS id: "+b.bsID+" Operator: "+b.opID + " Associated UEs: [ ");
			for(UE u:b.associatedUEs)
			System.out.print(u.uID+" ");
			System.out.println("]");
		}
		
		System.out.println("\nPrinting Operator Information");
		int operator[] = new int[5];
		for(UE u:setUE) {
			if(u.target!=null)
			operator[u.opID]++;
		}
		
		for(int i=0;i<5;i++) {
			System.out.println("Operator: "+i+"  User Count: "+operator[i]);
		}
		}
		double sysThpt = 0;
		for(UE u:setUE) {
			if(u.target!=null) {
			double bitrate = (Params.BANDWIDTH/u.target.associatedUEs.size())*Math.log10(1+u.sinr)/0.3010;
			sysThpt+=bitrate;
			}
			else
				blocked++;
		}
		System.out.println("\nSystem throughput: "+sysThpt);
		
	}

}
