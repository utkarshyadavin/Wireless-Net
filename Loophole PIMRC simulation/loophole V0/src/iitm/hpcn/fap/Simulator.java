package iitm.hpcn.fap;

import iitm.hpcn.fap.resources.FAP;
import iitm.hpcn.fap.resources.Params;
import iitm.hpcn.fap.resources.UE;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Simulator {

	private Set<UE> setUE = new HashSet<UE>();
	private Set<FAP> setFAP = new HashSet<FAP>();

	private Set<UE> associatedMacroUE = new HashSet<UE>();

	private int associationType = 0;
	
	private double ee = 0;
	private double capacity = 0;

	private SummaryStatistics allFAP = new SummaryStatistics();
	// TODO : implement an resource allocation algorithm
	public double sumThroughput = 0.0;
	
	private ArrayList<Double> sinrListM;
	private ArrayList<Double> sinrListF_IF;
	private ArrayList<Double> sinrListF_IL;
	
	private ArrayList<Double> bitRateListM;
	private ArrayList<Double> bitRateListF;
	private ArrayList<Double> bitRateListAll;
	private ArrayList<Double> sinrAll;
	

	public void init(String UEFile, String FAPFile, int associationType) {
		this.associationType = associationType;
		try {
			int id = 0;
			for (Point2D point : getInfo(UEFile)) {
				setUE.add(new UE(id++, point));
			}			
			id = 0;
			for (Point2D point : getInfo(FAPFile)) {
				setFAP.add(new FAP(id++, point));
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void runSim() {
		double sumThptM = 0.0,sumThptF = 0.0;
		warmUpUe();
		associateUEs();
		System.out.println("FUE : "+getTotalFUECount()+" MUE : "+(setUE.size() - getTotalFUECount())+" Idle FBS: "+idleFemtoCount());
		// get mean and variance
		for(FAP fap:setFAP)
		{
			allFAP.addValue(fap.getUECount());
		}
		System.out.println("Max : "+allFAP.getMax()+ " Mean FUE : "+allFAP.getMean()+" STD: "+allFAP.getStandardDeviation());
		radioEnvironmentMap();
		sinrCDF();
		allocateBW();
	}

	public void associateUEs() {
		for(UE ue:setUE)
		{
			FAP target = null;
			if(associationType == Params.MAXRSRP)
				target = ue.maxRsrp();
			else
				if(associationType == Params.MAXRSRPBIAS)
					target = ue.maxRSRPBias();
			else
				if(associationType == Params.TOTALCAPACITY)
					target = ue.eTotalBitRate();
				else
					if(associationType == Params.RESIDUALCAPACITY)
						target = ue.eResidualBitRate(associatedMacroUE.size());
					else
						System.err.println("ERROR :: unknow association tech all associated to macro bs");
			if(target != null)
				target.addUE(ue);
			else
				associatedMacroUE.add(ue);
		}
	}
	public void warmUpUe()
	{
		for (UE ue : setUE) 
		{
			double macroSignal = PathLoss.rxPowerWatt(ue, null);
			
			double noiseWatt = PathLoss.dB2watt(Params.NOISE_DB);
			double sinrMacro = 0.0;
			double femtoSignalSum = 0.0;
			double femtoSinrIL = 0.0;
			double femtoSinrIF = 0.0;

			ue.setRsrpMacroDB(PathLoss.watt2dB(macroSignal));//+Params.ANTENNAGAIN_MACRO_DBI);
			
			// sum of all femto cell signal at the current UE location under consideration.
			for(FAP fap : setFAP)
				femtoSignalSum += PathLoss.rxPowerWatt(ue, fap);
			
			sinrMacro = PathLoss.watt2dB(macroSignal / (femtoSignalSum + noiseWatt));
			
			if(sinrMacro > 30)
				sinrMacro =30;
			
			ue.setSinrMacroDB(sinrMacro);
			// collect SINR_IF, SINR_IL of all femto BS
			for(FAP fap : setFAP)
			{
				double femtoSignal = PathLoss.rxPowerWatt(ue, fap);
				femtoSinrIF = femtoSignal / (femtoSignalSum - femtoSignal + noiseWatt);
											
				femtoSinrIL = femtoSignal / (femtoSignalSum - femtoSignal + macroSignal + noiseWatt);
				
				femtoSinrIF = PathLoss.watt2dB(femtoSinrIF);
				femtoSinrIL = PathLoss.watt2dB(femtoSinrIL);
				femtoSignal = PathLoss.watt2dB(femtoSignal);
				
				if(femtoSinrIF > 30)
					femtoSinrIF =30;
				if(femtoSinrIL >30)
					femtoSinrIL =30;
				ue.add(femtoSignal,femtoSinrIL, femtoSinrIF, fap);
			}
		}
	}
	public void allocateBW()
	{
		bitRateListM = new ArrayList<Double>();
		bitRateListF = new ArrayList<Double>();
		
		bitRateListAll = new ArrayList<Double>();
		
		double totalFractionSinr = 0;
		
		for(UE ue : associatedMacroUE)
				totalFractionSinr += 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSinrMacroDB())));
		
		for(UE ue : associatedMacroUE)
		{
			double myFactionSinr = 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSinrMacroDB())));
//			double allocation = (myFactionSinr / (totalFractionSinr))* (1 - Residual.ALPHA);
			double allocation = 1.0 / associatedMacroUE.size();
			double bitRate = allocation*(1/myFactionSinr)/0.3010;
			bitRateListM.add(bitRate);
			bitRateListAll.add(bitRate);
		}
		for(FAP fap : setFAP)
		{
			double totalFractionSinrIF = 0.0;
			double totalFractionSinrIL = 0.0;
			Set<UE> associatedFapUE = fap.getAssociatedUE(); 
			for(UE ue : associatedFapUE)
			{
				totalFractionSinrIF += 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSINRIFdb())));
				totalFractionSinrIL += 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSINRILdb())));
			}
			for(UE ue : associatedFapUE)
			{
				double myFactionSinrIL = 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSINRILdb())));
				double myFactionSinrIF = 1 / (Math.log10(1 + PathLoss.dB2watt(ue.getSINRIFdb())));
				
//				double allocationIL = (myFactionSinrIL / (totalFractionSinrIL))*(1 - Residual.ALPHA);
//				double allocationIF = (myFactionSinrIF / (totalFractionSinrIF))*Residual.ALPHA;
				
				double allocationIL = associatedFapUE.size();
				double allocationIF = associatedFapUE.size();
				
				double bitRate = (allocationIL)*(1/myFactionSinrIL) + (allocationIF)*(1/myFactionSinrIF);
				bitRate /= 0.3010;
				bitRateListF.add(bitRate);
				bitRateListAll.add(bitRate);
			}
		}

		double sumThroughput = 0;
		double sumEnergy = 0;
		
		for(Double d : bitRateListAll){
			sumThroughput += d;
		}
		
		double bsEnergy = 250 + associatedMacroUE.size()*5;
		double fapEnergy = (setFAP.size() - idleFemtoCount())*10;
		
		sumEnergy = bsEnergy + fapEnergy;
		
		ee = sumEnergy/sumThroughput;
		capacity = sumThroughput;
		

	}
	public void sinrCDF()
	{
		sinrListM = new ArrayList<Double>();
		sinrListF_IF = new ArrayList<Double>();
		sinrListF_IL = new ArrayList<Double>();
		sinrAll = new ArrayList<Double>();
		for(UE ue : setUE)
		{
			if(ue.getUeType() == Params.MUE){
				sinrListM.add(ue.getSinrMacroDB());
				sinrAll.add(ue.getSinrMacroDB());
			}
			if(ue.getUeType() == Params.FUE){
				sinrListF_IL.add(ue.getSINRILdb());
				sinrListF_IF.add(ue.getSINRIFdb());
				sinrAll.add(ue.getSINRILdb());
				sinrAll.add(ue.getSINRIFdb());
			}
		}
//		System.out.println(sinrListF_IL);
//		System.out.println(sinrListF_IF);
	}
	public void cdfHelper(ArrayList<Double> cdf, String firstName)
	{
		String fName = null;
		switch(associationType)
		{
			case Params.MAXRSRP:
				fName = "maxRSRP";
				break;
			case Params.MAXRSRPBIAS:
				fName = "maxRSRPBIAS";
				break;
			case Params.TOTALCAPACITY:
				fName = "TOTALCAPACITY";
				break;
			case Params.RESIDUALCAPACITY:
				fName = "RESIDUALCAPACITY";
				break;
		}
		Collections.sort(cdf);
		double max,min,size;
		size = cdf.size();
		min = cdf.get(0);
		max = cdf.get((int) (size-1));
		double stepSize = (max - min)/Params.CDF_STEP;
		try
		{
			PrintWriter cdfWriter = new PrintWriter("./outPut/"+firstName+fName);
			for(int i =0;i<=Params.CDF_STEP;i++)
			{
				double prob = 0.0;
				double value = stepSize * i + min;
				for(Double d : cdf)
					if(d < value)
						prob++;
				cdfWriter.printf("%3.2f %5.4f \n", value, prob/size);
			}
			cdfWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getTotalFUECount() {
		int ueCount = 0;
		for (FAP fap : setFAP) {
			ueCount += fap.getUECount();
		}
		return ueCount;
	}

	public int idleFemtoCount()
	{
		int count = 0;
		for(FAP fap:setFAP)
			if(fap.getUECount() == 0)
				count++;
		return count;
	}
	static Set<Point2D> getInfo(String filename) throws FileNotFoundException {
		Set<Point2D> set = new HashSet<Point2D>();
		Scanner fileScanner = new Scanner(new File(filename));
		while (fileScanner.hasNextDouble()) {
			double x = fileScanner.nextDouble();
			double y = fileScanner.nextDouble();
			Point2D point = new Point2D.Double(x, y);

			set.add(point);
		}
		return set;
	}
	public void radioEnvironmentMap()
	{
		try
		{
			PrintWriter remap = new PrintWriter("./outPut/remap.dat");
			for(UE ue:setUE)
			{
				double x = ue.getLocation().getX();
				double y = ue.getLocation().getY();
				
				ArrayList<Double> rmap = ue.getRem();
				double macro = rmap.get(0);
				double femto = rmap.get(1);

				remap.printf("%3.2f %3.2f %5.4f %5.4f\n",x,y,macro,femto);	
			}
			remap.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Double> getSinrListM() {
		return sinrListM;
	}

	public ArrayList<Double> getSinrListF_IF() {
		return sinrListF_IF;
	}

	public ArrayList<Double> getSinrListF_IL() {
		return sinrListF_IL;
	}

	public ArrayList<Double> getBitRateListM() {
		return bitRateListM;
	}

	public ArrayList<Double> getBitRateListF() {
		return bitRateListF;
	}

	public ArrayList<Double> getBitRateListAll() {
		return bitRateListAll;
	}
	
	public ArrayList<Double> getSinrAll() {
		return sinrAll;
	}

	public void setSinrAll(ArrayList<Double> sinrAll) {
		this.sinrAll = sinrAll;
	}
	
	public double getEE() {
		return ee;
	}
	
	public double getCAP() {
		return capacity;
	}
}
