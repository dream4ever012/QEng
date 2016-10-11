package utils;

import ResourceStrings.DDS;
import ResourceStrings.SD;
import optimizer.QueryManager;

public class CreateTablesInMemoryDDS {
	
	public static void createTablesInMemory(QueryManager myAW){
		
		myAW.importCSVAsTable(DDS.AT_FP, DDS.AT);
		myAW.importCSVAsTable(DDS.ATaaUC_FP, DDS.ATaaUC);
		myAW.importCSVAsTable(DDS.BGR_FP, DDS.BGR);
		myAW.importCSVAsTable(DDS.BGRaaCLS_FP, DDS.BGRaaCLS);
		myAW.importCSVAsTable(DDS.CLS_FP, DDS.CLS);
		myAW.importCSVAsTable(DDS.CLSaaUT_FP, DDS.CLSaaUT);
		myAW.importCSVAsTable(DDS.DRQ_FP, DDS.DRQ);
		myAW.importCSVAsTable(DDS.DRQaaCLS_FP, DDS.DRQaaCLS);
		myAW.importCSVAsTable(DDS.DRQaaEA_FP, DDS.DRQaaEA);
		myAW.importCSVAsTable(DDS.EA_FP, DDS.EA);
		myAW.importCSVAsTable(DDS.EAaaCLS_FP, DDS.EAaaCLS);
		myAW.importCSVAsTable(DDS.FLT_FP, DDS.FLT);
		myAW.importCSVAsTable(DDS.FLTaaSRQ_FP, DDS.FLTaaSRQ);
		myAW.importCSVAsTable(DDS.GL_FP, DDS.GL);
		myAW.importCSVAsTable(DDS.GLaaPJT_FP, DDS.GLaaPJT);
		myAW.importCSVAsTable(DDS.GLaaSRQ_FP, DDS.GLaaSRQ);
		myAW.importCSVAsTable(DDS.HZD_FP, DDS.HZD);
		myAW.importCSVAsTable(DDS.HZDaaFLT_FP, DDS.HZDaaFLT);
		myAW.importCSVAsTable(DDS.PJT_FP, DDS.PJT);
		myAW.importCSVAsTable(DDS.PJTaaPPL_FP, DDS.PJTaaPPL);
		myAW.importCSVAsTable(DDS.PPL_FP, DDS.PPL);
		myAW.importCSVAsTable(DDS.PPLaaBGR_FP, DDS.PPLaaBGR);
		myAW.importCSVAsTable(DDS.SRQ_FP, DDS.SRQ);
		myAW.importCSVAsTable(DDS.SRQaaSSRQ_FP, DDS.SRQaaSSRQ);
		myAW.importCSVAsTable(DDS.SSRQ_FP, DDS.SSRQ);
		myAW.importCSVAsTable(DDS.SSRQaaDRQ_FP, DDS.SSRQaaDRQ);
		myAW.importCSVAsTable(DDS.UC_FP, DDS.UC);
		myAW.importCSVAsTable(DDS.UCaaGL_FP, DDS.UCaaGL);
		myAW.importCSVAsTable(DDS.UT_FP, DDS.UT);
		myAW.importCSVAsTable(DDS.UTaaUTL_FP, DDS.UTaaUTL);
		myAW.importCSVAsTable(DDS.UTL_FP, DDS.UTL);
	}

}
