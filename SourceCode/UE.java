package SourceCode;
import java.awt.geom.Point2D;

public class UE{         // Class for UserEquipments/Nobile Devices
	public int id; 
	public double receivedPower;
	public Point2D location;
	public BS target;

	public UE(int id, Point2D location){
		this.id = id;
		this.location = location;
		this.target = null;
	}
}
