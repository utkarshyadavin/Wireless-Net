package iitm.hpcn.fap.resources;

import iitm.hpcn.fap.Residual;
import iitm.hpcn.fap.PathLoss;

public class TargetBsDTO {
	private double sinrDBIL, sinrDBIF;
	private double rsrpFemtoDB;
	private FAP fapBS;

	public TargetBsDTO(double rsrpFemto, double sinrDBIL, double sinrDBIF, FAP fapTarget) {
		super();
		this.sinrDBIL = sinrDBIL;
		this.sinrDBIF = sinrDBIF;
		this.fapBS = fapTarget;
		this.rsrpFemtoDB =rsrpFemto;
	}

	public double getBiasedRsrpDB() {
		return rsrpFemtoDB + fapBS.getBias();
	}

	public double getSinrDBIL() {
		return sinrDBIL;
	}

	public void setSinrDBIL(double sinrDBIL) {
		this.sinrDBIL = sinrDBIL;
	}

	public double getSinrDBIF() {
		return sinrDBIF;
	}

	public void setSinrDBIF(double sinrDBIF) {
		this.sinrDBIF = sinrDBIF;
	}

	public FAP getFapBS() {
		return fapBS;
	}

	public void setFapBS(FAP target) {
		this.fapBS = target;
	}

	public double getRsrpFemtoDB() {
		return rsrpFemtoDB;
	}

	public void setRsrpFemtoDB(double rsrpFemto) {
		this.rsrpFemtoDB = rsrpFemto;
	}

	// equal to all
	public double getTotalBitRate() {
		double bitRate = 0.0;
		if(rsrpFemtoDB >= Params.MIN_RSRP_TH_DB)
			bitRate = (1 - Residual.ALPHA) * Math.log10(1 + PathLoss.dB2watt(sinrDBIL)) + Residual.ALPHA * Math.log10(1 + PathLoss.dB2watt(sinrDBIF));
		return bitRate;
	}

	public double getResidualBitrate() {
		return getTotalBitRate() / (fapBS.getUECount() + 1);
	}

	// the real bit rate achieved with equal channel allocation.
	public double getAllocatedBitRate() {
		double bitRate = 0.0;
		if (sinrDBIF >= Params.MIN_RSRP_TH_DB)
			bitRate += Residual.ALPHA * Math.log10(1 + PathLoss.dB2watt(sinrDBIF));
		if (sinrDBIL >= Params.MIN_RSRP_TH_DB)
			bitRate += (1 - Residual.ALPHA)	* Math.log10(1 + PathLoss.dB2watt(sinrDBIL));
		bitRate /= fapBS.getUECount();
		return bitRate;
	}

	@Override
	public String toString() {
		return " [" + fapBS + " sinrDBIF=" + sinrDBIF + ", sinrDBIL="
				+ sinrDBIL + "]";
	}

}
