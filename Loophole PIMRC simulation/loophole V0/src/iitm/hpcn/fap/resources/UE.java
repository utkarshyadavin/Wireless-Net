package iitm.hpcn.fap.resources;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import iitm.hpcn.fap.Residual;
import iitm.hpcn.fap.PathLoss;

public class UE {
	private int id;
	private Point2D location;
	private TargetBsDTO target;
	// ueType = MUE, FUE, out of range & can not communicate.
	private int ueType;
	private boolean sorted;
	private double sinrMacroDB;
	private double rsrpMacroDB;
	private ArrayList<TargetBsDTO> fapList;
	private HashSet<Integer> fapSet;

	public UE(int id, Point2D location) {
		this.id = id;
		this.location = location;
		this.ueType = Params.OUT_OF_RANGE;
		this.sorted = false;

		fapSet = new HashSet<Integer>();
		fapList = new ArrayList<TargetBsDTO>();
	}

	public ArrayList<Double> getRem() {
		ArrayList<Double> rmap = new ArrayList<Double>();
		rmap.add(sinrMacroDB);
		rmap.add(fapList.get(0).getSinrDBIL());
		return rmap;
	}
	public int getId() {
		return id;
	}

	public Point2D getLocation() {
		return location;
	}

	public int getUeType() {
		return ueType;
	}
	
	public double getRsrpMacroDB() {
		return rsrpMacroDB;
	}

	public void setRsrpMacroDB(double rsrpMacroDB) {
		this.rsrpMacroDB = rsrpMacroDB;
	}

	public void printFAPList(int count) {
		System.out.println("\n[UE[" + id + "] MacroSinrDB=" + sinrMacroDB);
		for (int i = 0; i < count; i++)
			System.out.print(fapList.get(i) + "\n");
		System.out.print("]");
	}

	public FAP getTarget() {
		if (ueType == Params.OUT_OF_RANGE) {
			System.err.println("ERROR :: UE is out of range Target value unreliable.");
			return null;
		}
		if (target == null)
			return null;
		return target.getFapBS();
	}

	public void setOutOfRange() {
		this.ueType = Params.OUT_OF_RANGE;
	}

	public boolean isOutOfRange() {
		return (this.ueType == Params.OUT_OF_RANGE) ? true : false;
	}

	public void setSinrMacroDB(double sinrMacroDB) {
		this.sinrMacroDB = sinrMacroDB;
	}

	public double getSinrMacroDB() {
		if (ueType == Params.OUT_OF_RANGE)
			System.err.println("ERROR :: UE is out of range SINR value unreliable.");
		if (target == null)
			return sinrMacroDB;
		System.err.println("ERROR :: connected to femto. Thermal noise returned.");
		return Params.NOISE_DB;
	}

	public double getSINRILdb() {
		if (ueType == Params.OUT_OF_RANGE) {
			System.err.println("ERROR :: UE is out of range SINR value unreliable.");
			return Params.NOISE_DB;
		}
		if (target != null)
			return target.getSinrDBIL();
		System.err.println("ERROR :: connected to macro SINR returned[sinrMacroIL]");
		return sinrMacroDB;
	}

	public double getSINRIFdb() {
		if (ueType == Params.OUT_OF_RANGE)
			System.err.println("ERROR :: UE is out of range SINR  = Thermal noise.");
		if (target != null)
			return target.getSinrDBIF();
		System.err.println("ERROR :: connected to macro. SINR IF = Thermal noise.");
		return Params.NOISE_DB;
	}

	public void add(double femtoSignal,double sinrDBIL, double sinrDBIF, FAP pTarget) {
		int fapID = pTarget.getId();
		if (fapSet.contains(fapID)) {
			System.err.println("FAP(" + fapID + ") allready present");
			return;
		}
		fapSet.add(fapID);
		sorted = false;
//		System.out.println(sinrDBIL+" :: "+sinrDBIF);
		TargetBsDTO bs = new TargetBsDTO(femtoSignal,sinrDBIL, sinrDBIF, pTarget);
		fapList.add(bs);
	}

	public FAP eTotalBitRate() {
		FAP fap = null;
		double maxBitrate = ((1 - Residual.ALPHA) * Math.log10(1 + PathLoss.dB2watt(sinrMacroDB)));
		if (!sorted) {
			sortBSperTotalBitRate();
			sorted = true;
		}
		TargetBsDTO tBs = fapList.get(0);
		double fapBitRate = tBs.getTotalBitRate();
		if (maxBitrate > fapBitRate) {
			target = null;
			ueType = Params.MUE;
		} else {
			ueType = Params.FUE;
			target = tBs;
			fap = target.getFapBS();
		}
		return fap;
	}

	public FAP eResidualBitRate(int mueCount) {
		FAP fap = null;
		// /number of maco users
		double macroBitrate = 0.0;
		if(rsrpMacroDB >= Params.MIN_RSRP_TH_DB)
			macroBitrate=((1 - Residual.ALPHA) * Math.log10(1 + PathLoss	.dB2watt(sinrMacroDB))) / (mueCount+1);
		if (!sorted) {
			sortBSperResidualBitRate();
			sorted = true;
		}
		TargetBsDTO tBs = fapList.get(0);
		double fapBitRate = tBs.getResidualBitrate();
		if (macroBitrate > fapBitRate) {
			target = null;
			ueType = Params.MUE;
		} else {
			ueType = Params.FUE;
			target = tBs;
			fap = target.getFapBS();
		}
		return fap;
	}

	public FAP maxRSRPBias() {
		FAP fap = null;
		if (!sorted) {
			sortBSperRsrpBias();
			sorted = true;
		}
		TargetBsDTO tBs = fapList.get(0);
		if (rsrpMacroDB > tBs.getBiasedRsrpDB()) {
			target = null;
			ueType = Params.MUE;
		} else {
			ueType = Params.FUE;
			target = tBs;
			fap = target.getFapBS();
		}
		return fap;
	}

	public FAP maxRsrp() {
		FAP fap = null;
		if (!sorted) {
			sortBSperRsrp();
			sorted = true;
		}
		TargetBsDTO tBs = fapList.get(0);
		if (rsrpMacroDB > tBs.getRsrpFemtoDB()) {
			target = null;
			ueType = Params.MUE;
		} else {
			ueType = Params.FUE;
			target = tBs;
			fap = target.getFapBS();
		}
		return fap;
	}

	@Override
	public String toString() {
		if (target == null)
			return String.format("UE%s(Macro)", id);
		else
			return String.format("UE%s(%s)", id, target.getFapBS());
	}

	// sort bs per max rsrp.
	private void sortBSperRsrp() {
		Collections.sort(fapList, new MaxRsrpIL());
	}

	private class MaxRsrpIL implements Comparator<TargetBsDTO> {
		// sort in decreasing order.
		@Override
		public int compare(TargetBsDTO e1, TargetBsDTO e2) {
			return e2.getRsrpFemtoDB() > e1.getRsrpFemtoDB() ? 1 : (e2.getRsrpFemtoDB() < e1.getRsrpFemtoDB() ? -1 : 0);
		}
	}

	// sort bs per max rsrp + bias.
	private void sortBSperRsrpBias() {
		Collections.sort(fapList, new MaxRsrpBiasIL());
	}

	private class MaxRsrpBiasIL implements Comparator<TargetBsDTO> {
		// sort in decreasing order.
		@Override
		public int compare(TargetBsDTO e1, TargetBsDTO e2) {
			return e2.getBiasedRsrpDB() > e1.getBiasedRsrpDB() ? 1 : (e2.getBiasedRsrpDB() < e1.getBiasedRsrpDB() ? -1 : 0);
		}
	}

	// sort per total bit rate
	private void sortBSperTotalBitRate() {
		Collections.sort(fapList, new MaxTotalBitRate());
	}

	private class MaxTotalBitRate implements Comparator<TargetBsDTO> {
		// sort in decreasing order.
		@Override
		public int compare(TargetBsDTO e1, TargetBsDTO e2) {
			return e2.getTotalBitRate() > e1.getTotalBitRate() ? 1 : (e2.getTotalBitRate() < e1.getTotalBitRate() ? -1 : 0);
		}
	}

	// sort per residual bit rate
	private void sortBSperResidualBitRate() {
		Collections.sort(fapList, new MaxResidualBitRate());
	}

	private class MaxResidualBitRate implements Comparator<TargetBsDTO> {
		// sort in decreasing order.
		@Override
		public int compare(TargetBsDTO e1, TargetBsDTO e2) {
			return e2.getResidualBitrate() > e1.getResidualBitrate() ? 1 : (e2.getResidualBitrate() < e1.getResidualBitrate() ? -1 : 0);
		}
	}

}
