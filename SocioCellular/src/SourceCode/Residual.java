package SourceCode;

public class Residual{

	public static int RUNS;
	public static void main(String [] args){
		args = "./UE.txt ./BS.txt 500".split(" ");
		RUNS = Integer.parseInt(args[2]);

		String outPath = "./outPut/";

		for(int i=1; i<=1; i++){
			Simulation scenario = new Simulation();
			scenario.init(args[0], args[1]);
			scenario.associationRSRP();
			scenario.printAllInfo(1);
			scenario.associationRSRPSocialPartialOperatorCollaboration();
			scenario.printAllInfo(1);
			scenario.associationRSRPPartialOperatorCollaboration();
			scenario.printAllInfo(1);
			scenario.associationRSRPCollaboration();
			scenario.printAllInfo(1);
			
		}
	}
}