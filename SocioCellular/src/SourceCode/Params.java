package SourceCode;

public class Params {
	public static final double FEMTOPOWER = 2; //in watts
	public static final double SUBCHANNELCOUNT = 50; // 50 subchannels
	public static final double BANDWIDTH = 10; // 10 MHz
	public static final double FEMTOSUBPOWER = FEMTOPOWER/SUBCHANNELCOUNT; //in watts
	public static final double SUBCHANNELBANDWIDTH = BANDWIDTH/SUBCHANNELCOUNT; // in MHz
	public static final double NOISE = 0.000000000001; // in MHz
	public static final int TOTALUES = 500;
	public static final int TOTALOPERATORS = 6;
	public static final int TOTALOPERATORCOLLABORATION = 3;
	public static final int TOTALSOCIALCOLLABORATION = 230;	
	public static final int CDF_STEP = 1000;
}
