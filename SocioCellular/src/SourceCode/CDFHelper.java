package Sourcecode;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class CDFHelper {
	private double maxValue;
	private double minValue;
	private int stepSize;
	private double stepLength;
	private String fName;
	
	private double cdfProb[];
	private int runs;
	
	public CDFHelper(double maxValue, double minValue, int stepCount,
			String fName) {
		super();
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.stepSize = stepCount;
		this.fName = fName;
		runs = 0;
		cdfProb = new double[this.stepSize];
		this.stepLength = (this.maxValue - this.minValue) / stepCount;
	}

	public void addCDFList(ArrayList<Double> cdfList)
	{
		double cdfSize = cdfList.size();
		if(cdfSize == 0)
			return;
		runs++;
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
		if(runs==0)
		{
			System.err.println("ERROR : Nothing to write.");
			return;
		}
		try
		{
			PrintWriter cdfWriter = new PrintWriter(fName);
			for(int i =0;i<stepSize;i++)
			{
				double value = stepLength * i + minValue;
				cdfWriter.printf("%3.5f %5.5f \n", value, cdfProb[i]/runs);
			}
			cdfWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
