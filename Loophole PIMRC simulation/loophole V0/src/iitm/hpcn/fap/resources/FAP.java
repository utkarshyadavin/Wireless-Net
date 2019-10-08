package iitm.hpcn.fap.resources;

import iitm.hpcn.fap.Residual;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class FAP {
	private int id;
	private Point2D location;
	private double bias;
	public Set<UE> associatedUE = new HashSet<UE>();


	public FAP(int id, Point2D location) {
		super();
		this.id = id;
		this.location = location;
		// initial bias value.
		this.bias = Residual.BIAS;
	}
	public double getBias()
	{
		return bias;
	}
	public void setBias(double bias)
	{
		this.bias = bias;
	}
	public int getId() {
		return id;
	}

	public Point2D getLocation() {
		return location;
	}

	public void addUE(UE ue) {
			associatedUE.add(ue);
	}

	public void removeAllUE() {
		associatedUE.clear();
	}

	public void printUE() {
		System.out.println(this + "\t: " + associatedUE.size() + "\t: " + associatedUE);
	}

	@Override
	public String toString() {
		return String.format("FAP%s", id);
	}

	public int getUECount() {
		return associatedUE.size();
	}
	public Set<UE> getAssociatedUE() {
		return associatedUE;
	}

}
