package cdf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class CDF {
	static int samples = 100000;
	static Random rand = new Random();
	static float sMin = -5;
	static float sMax = 5;
	private static final int STEPS = 100;
	public static void main(String args[])
	{
		new1();
	}
	
	public static void new0() {
		double cdf[] = new double[STEPS + 1];
		for (int i = 0; i < samples; i++) {
			float value = (float)getValue();
			int lim = Math.round((value - sMin) / (sMax - sMin) * STEPS);
			for (int j = STEPS; j >= lim; j--)
				cdf[j]++;
		}
		for (int i = 0; i < cdf.length; i++) {
			double value = sMin +  (sMax - sMin) * i / STEPS;
			double prob = cdf[i] / samples;
			System.out.printf("%3.2f %5.4f \n", value, prob);
		}
	}

	private static double getValue() {
		return sMin + (sMax - sMin) * rand.nextFloat();
	}
	public static void new1() {
		ArrayList<Double> cdf = new ArrayList<Double>();
		for (int i = 0; i < samples; i++)
			cdf.add(getValue());
		Collections.sort(cdf);
		double max,min,size;
		size = cdf.size();
		min = sMin;// cdf.get(0);
		max = sMax;//cdf.get((int) (size-1));
		double stepSize = (max - min)/STEPS;
		for(int i =0;i<=STEPS;i++)
		{
			double prob = 0.0;
			double value = stepSize * i + min;
			for(Double d : cdf)
			{
				if(d < value)
					prob++;
			}
			System.out.printf("%3.2f %5.4f \n", value, prob/samples);
		}
	}
}