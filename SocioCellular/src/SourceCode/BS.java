package SourceCode;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
public class BS{			// Class for User Equipment (or Mobile Device) 
	public int bsID;
	public int opID;
	public UE owner;
	public Point2D location;
	public Set<UE> associatedUEs = new HashSet<UE>();

	public BS(int bsid, Point2D location, int opid) {
		this.bsID = bsid;
		this.opID = opid;
		this.location = location;
		this.owner = null;
    }
	
	public BS(int bsid, Point2D location, int opid, UE owner) {
		this.bsID = bsid;
		this.opID = opid;
		this.location = location;
		this.owner = owner;
    }
}