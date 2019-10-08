package cdf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorting {
	public ArrayList<Double> d=new ArrayList<Double>();
	public static void main(String args[])
	{
		Sorting s = new Sorting();
		s.d.add(0.3);
		s.d.add(-0.3);
		s.d.add(1.3);
		s.d.add(10.0);
		s.sortA();
		System.out.println(s.d);
	}
	private void sortA() {
		Collections.sort(d, new Dorder());
	}

	private class Dorder implements Comparator<Double> {
		// sort in decreasing order.
		@Override
		public int compare(Double e1, Double e2) {
			return e1 > e2 ? 1 : (e1 < e2 ? -1 : 0);
//			return e2 > e1 ? 1 : (e2 < e1 ? -1 : 0);
		}
	}
}
