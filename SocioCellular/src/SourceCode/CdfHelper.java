package SourceCode;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


public class CdfHelper{

	private double maxValue;
	private double minValue;
	private int stepSize;
	private double stepLength;
	private String fName;
	private double cdfProb[];
	
	public CdfHelper(double maxValue, double minValue, int stepCount,
			String fName){
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.stepSize = stepCount;
		this.fName = fName;
		cdfProb = new double[this.stepSize];
		this.stepLength = (this.maxValue - this.minValue) / stepCount;

	}

	public void addCDFList(ArrayList<Double> cdfList)
	{
		double cdfSize = cdfList.size();
		if(cdfSize == 0)
			return;
		Collections.sort(cdfList);
		for(int i =0;i<stepSize;i++)
		{
			double prob = 0.0;
			double value = stepLength * i + minValue;
			for(Double d : cdfList)
				if(d < value)
					prob++;
			cdfProb[i] += prob/cdfSize;
		}
	}

	public void saveToFile()
	{

		try
		{
			PrintWriter cdfWriter = new PrintWriter(fName);
			for(int i =0;i<stepSize;i++)
			{
				double value = stepLength * i + minValue;
				cdfWriter.printf("%3.5f %5.5f \n", value, cdfProb[i]);
			}
			cdfWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}