package SourceCode;

import java.io.FileWriter; 
import java.io.IOException; 
import java.util.concurrent.ThreadLocalRandom;

public class Random{

	public static void main(String [] args){

		try{    
			FileWriter f1=new FileWriter("UE.txt"); 

			for(int i=0; i<500; i++) {				// Generate 100 UE Objects in 1000X1000 area
				double randomX = ThreadLocalRandom.current().nextDouble(-500, 500);
				double randomY = ThreadLocalRandom.current().nextDouble(-500, 500);
				int opID = ThreadLocalRandom.current().nextInt(0,6);
				f1.write(randomX + " " + randomY + " " + opID + "\n");
			}

			f1.close();
		}catch(Exception e){System.out.println(e);}


		try{ 
		FileWriter f2=new FileWriter("BS.txt"); 
		
		for(int i=0; i<50; i++) {				// Generate 20 BS Objects in 1000X1000 area
			double randomX = ThreadLocalRandom.current().nextDouble(-500, 500);
			double randomY = ThreadLocalRandom.current().nextDouble(-500, 500);
			int opID = ThreadLocalRandom.current().nextInt(0,6);
			f2.write(randomX + " " + randomY + " " + opID + "\n");
		}

		f2.close();
		}catch(Exception e){System.out.println(e);}
	}
}
