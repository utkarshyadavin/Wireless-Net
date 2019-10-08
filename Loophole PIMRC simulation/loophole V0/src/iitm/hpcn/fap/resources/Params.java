package iitm.hpcn.fap.resources;

public interface Params {
	//resource blocks 	
	public static final int NO_OF_CHANNELS = 256;	

	public static final int RBs_FOR_1_4_MHz = 6;
	public static final int RBs_FOR_3_MHz = 15;
	public static final int RBs_FOR_5_MHz = 25;
	public static final int RBs_FOR_10_MHz = 50;
	public static final int RBs_FOR_15_MHz = 75;
	public static final int RBs_FOR_20_MHz = 100;
	
	public static final double MACRO_POWER = 0.126;//20.0/RBs_FOR_5_MHz;  // In Watt per subchannel  43 dbm		[Correct]
	public static final double FAP_POWER = 0.004;//0.9/RBs_FOR_5_MHz;	 // In Watt per subchannel	20 dbm		[Correct]

	public static final double EXT_WALL_LOSS_DB = 20;  				
	public static final double PENETRATION_LOSS_DB = 10;
	public static final double ANTENNAGAIN_FEMTO_DBI = 0;// 8
	public static final double ANTENNAGAIN_MACRO_DBI = 0;// 15
	public static final double NOISE_DB = -144.0;// or -97.5;
	public static final double MIN_RSRP_TH_DB = -135.0; 
	public static final double BANDWIDTH = 5000000;  // 5MHz
	
	public static final double BSCHANNELWATT = 5;
	
	public static final int MAXRSRP = 1;
	public static final int MAXRSRPBIAS = 2;
	public static final int TOTALCAPACITY = 3;
	public static final int RESIDUALCAPACITY = 4;
	
	public static final int OUT_OF_RANGE = -1;
	public static final int MUE = 0;
	public static final int FUE = 1;
	
	public static final double BITRATE_MAX = 5.0;
	public static final double BITRATE_MIN = 0.0;
	
	public static final double SINR_MAX = 40.0;
	public static final double SINR_MIN = -40.0;
	public static final int CDF_STEP = 1000;
}
