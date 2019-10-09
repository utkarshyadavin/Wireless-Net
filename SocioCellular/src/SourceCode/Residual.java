package SourceCode;
public class Residual{

	public static int RUNS;
	public static void main(String [] args){
		args = "./UE.txt ./BS.txt 500".split(" ");
		RUNS = Integer.parseInt(args[2]);
		String outPath = "./outPut/";

		CdfHelper sinrZeroOperatorCollaborationCDF  = new CdfHelper(2,-1, Params.CDF_STEP, outPath+"sinrZeroOperatorCollaboration");
		CdfHelper sinrFullOperatorCollaborationCDF = new CdfHelper(2,-1, Params.CDF_STEP, outPath+"sinrFullOperatorCollaboration");
		CdfHelper sinrPartialOperatorCollaborationCDF  = new CdfHelper(2,-1, Params.CDF_STEP, outPath+"sinrPartialOperatorCollaboration");
		CdfHelper sinrSocialOperatorCollaborationCDF  = new CdfHelper(2,-1, Params.CDF_STEP, outPath+"sinrSocialOperatorCollaboration");
			
		CdfHelper bitrateZeroOperatorCollaborationCDF = new CdfHelper(5, 0, Params.CDF_STEP, outPath+"bitrateZeroOperatorCollaboration");
		CdfHelper bitrateFullOperatorCollaborationCDF = new CdfHelper(5, 0, Params.CDF_STEP, outPath+"bitrateFullOperatorCollaboration");
		CdfHelper bitratePartialOperatorCollaborationCDF = new CdfHelper(5, 0, Params.CDF_STEP, outPath+"bitratePartialOperatorCollaboration");
		CdfHelper bitrateSocialOperatorCollaborationCDF = new CdfHelper(5, 0, Params.CDF_STEP, outPath+"bitrateSocialOperatorCollaboration");
			
		
		for(int i=1; i<=1; i++){
			Simulation scenario = new Simulation();
			scenario.init(args[0], args[1]);
			
			scenario.associationRSRP();
			scenario.printAllInfo(1);
			sinrZeroOperatorCollaborationCDF.addCDFList(scenario.sinrZeroOperatorCollaboration);
			bitrateZeroOperatorCollaborationCDF.addCDFList(scenario.bitrateZeroOperatorCollaboration);
			sinrZeroOperatorCollaborationCDF.saveToFile();
			bitrateZeroOperatorCollaborationCDF.saveToFile();
			
			scenario.associationRSRPSocialPartialOperatorCollaboration();
			scenario.printAllInfo(1);
			sinrSocialOperatorCollaborationCDF.addCDFList(scenario.sinrSocialOperatorCollaboration);
			bitrateSocialOperatorCollaborationCDF.addCDFList(scenario.bitrateSocialOperatorCollaboration);
			sinrSocialOperatorCollaborationCDF.saveToFile();
			bitrateSocialOperatorCollaborationCDF.saveToFile();


			scenario.associationRSRPPartialOperatorCollaboration();
			scenario.printAllInfo(1);
			sinrPartialOperatorCollaborationCDF.addCDFList(scenario.sinrPartialOperatorCollaboration);
			bitratePartialOperatorCollaborationCDF.addCDFList(scenario.bitratePartialOperatorCollaboration);
			sinrPartialOperatorCollaborationCDF.saveToFile();
			bitratePartialOperatorCollaborationCDF.saveToFile();

			scenario.associationRSRPCollaboration();
			scenario.printAllInfo(1);
			sinrFullOperatorCollaborationCDF.addCDFList(scenario.sinrFullOperatorCollaboration);
			bitrateFullOperatorCollaborationCDF.addCDFList(scenario.bitrateFullOperatorCollaboration);
			sinrFullOperatorCollaborationCDF.saveToFile();
			bitrateFullOperatorCollaborationCDF.saveToFile();
			
		}
	}
}