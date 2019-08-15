package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class BS{          // Class for BaseStations
	public int id;
	public double power = 2.0;  // 2 Watt 
	public double bandwidth = 20000000; // 2 MHz
	public Point2D location; 
	public Set<UE> associatedUEs = new HashSet<UE>();
	
	public BS(int id, Point2D location) {
		this.id = id;
		this.location = location;
	}
}
