package iitm.hpcn.fap;

import java.io.PrintWriter;

import iitm.hpcn.fap.resources.CDFHelper;
import iitm.hpcn.fap.resources.Params;
public class Residual {

	public static int RUNS;
	public static double ALPHA;
	public static double BIAS;
	public static boolean debug = false;
	public static boolean BIASED = true;
		
	public static void main(String[] args) throws Exception {
		args="Data/UE-Dist-600-50 Data/FAP-Dist-600-10 500 0.5 12".split(" ");
		RUNS = Integer.parseInt(args[2]);//1
		ALPHA = Double.parseDouble(args[3]);//0.5
		BIAS = Double.parseDouble(args[4]);//5
		
		String outPath = "./outPut/";
		
		PrintWriter eeWriter = new PrintWriter(outPath+"EE"+"_"+ALPHA+"_"+BIAS);
		PrintWriter capacityWriter = new PrintWriter(outPath+"CAP"+"_"+ALPHA+"_"+BIAS);
		
		
		
		for( int runType =1 ; runType<= 4; runType++)
		{
			CDFHelper macroSinrCDF = new CDFHelper(40,-40, Params.CDF_STEP, outPath+"mSinr_"+ALPHA+"_"+BIAS+"_"+runType);
			CDFHelper femtoSinrILCDF = new CDFHelper(40,-40, Params.CDF_STEP, outPath+"fSinrIL_"+ALPHA+"_"+BIAS+"_"+runType);
			CDFHelper femtoSinrIFCDF = new CDFHelper(40,-40, Params.CDF_STEP, outPath+"fSinrIF_"+ALPHA+"_"+BIAS+"_"+runType);
			
			CDFHelper macroBitRateCDF = new CDFHelper(50, 0, 5000, outPath+"mBitRate_"+ALPHA+"_"+BIAS+"_"+runType);
			CDFHelper femtoBitRateCDF = new CDFHelper(50, 0, Params.CDF_STEP, outPath+"fBitRate_"+ALPHA+"_"+BIAS+"_"+runType);
			
			CDFHelper allBitRateCDF = new CDFHelper(50, 0, Params.CDF_STEP, outPath+"allBitRate_"+ALPHA+"_"+BIAS+"_"+runType);
			CDFHelper allSINRCDF = new CDFHelper(40, -40, Params.CDF_STEP, outPath+"allSinr_"+ALPHA+"_"+BIAS+"_"+runType);
			
			double energyEfficiency = 0;
			double systemCapacity = 0;
			
			for(int i=1;i<=RUNS;i++)
			{
				Simulator scenario = new Simulator();
				scenario.init(args[0] + "-" + i, args[1] + "-" + i, runType);
				scenario.runSim();
				macroSinrCDF.addCDFList(scenario.getSinrListM());
				femtoSinrILCDF.addCDFList(scenario.getSinrListF_IL());
				femtoSinrIFCDF.addCDFList(scenario.getSinrListF_IF());
				
				macroBitRateCDF.addCDFList(scenario.getBitRateListM());
				femtoBitRateCDF.addCDFList(scenario.getBitRateListF());
				allBitRateCDF.addCDFList(scenario.getBitRateListAll());
				allSINRCDF.addCDFList(scenario.getSinrAll());
				energyEfficiency += scenario.getEE();
				systemCapacity += scenario.getCAP();
			}
//			macroSinrCDF.saveToFile();
//			femtoSinrILCDF.saveToFile();
//			femtoSinrIFCDF.saveToFile();
//			
//			macroBitRateCDF.saveToFile();
//			femtoBitRateCDF.saveToFile();
//			
//			allBitRateCDF.saveToFile();
//			allSINRCDF.saveToFile();
			
			eeWriter.append(ALPHA+"\t"+BIAS+"\t"+runType+"\t"+energyEfficiency/RUNS+"\n");
			capacityWriter.append(ALPHA+"\t"+BIAS+"\t"+runType+"\t"+systemCapacity/RUNS+"\n");
			
		}
		eeWriter.close();
		capacityWriter.close();
		System.out.println("\nAll done.");
	}
}