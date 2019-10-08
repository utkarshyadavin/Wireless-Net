package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
public class UE{			// Class for User Equipment (or Mobile Device) 
	public int uID;
	public int opID;
	public Point2D location;
	public BS target;
	public double sinr;
	public static Set<UE> friendUEs = new HashSet<UE>();

	public UE(int id, Point2D location, int opid) {
		this.uID = id;
		this.location = location;
		this.target=null;
		this.opID = opid;
    }
}