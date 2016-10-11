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
	
	public static void registerTMDDS(QueryManager myAW){
		myAW.RegisterTM(DDS.ATaaUC, DDS.AT, "atid", DDS.UC, "ucid");
		myAW.RegisterTM(DDS.UCaaGL, DDS.UC, "ucid", DDS.GL, "glid");
		myAW.RegisterTM(DDS.GLaaPJT, DDS.GL, "glid", DDS.PJT, "pjtid");
		myAW.RegisterTM(DDS.GLaaSRQ, DDS.GL, "glid", DDS.SRQ, "srqid");
		myAW.RegisterTM(DDS.SRQaaSSRQ, DDS.SRQ, "srqid", DDS.SSRQ, "ssrqid");

		myAW.RegisterTM(DDS.SSRQaaDRQ, DDS.SSRQ, "ssrqid", DDS.DRQ, "drqid");
		myAW.RegisterTM(DDS.DRQaaEA, DDS.DRQ, "drqid", DDS.EA, "eaid");
		myAW.RegisterTM(DDS.DRQaaCLS, DDS.DRQ, "drqid", DDS.CLS, "clsid");		
		myAW.RegisterTM(DDS.EAaaCLS, DDS.EA, "eaid", DDS.CLS, "clsid");
		myAW.RegisterTM(DDS.PJTaaPPL, DDS.PJT, "pjtid", DDS.PPL, "pplid");
		
		myAW.RegisterTM(DDS.PPLaaBGR, DDS.PPL, "pplid", DDS.BGR, "bgrid");
		myAW.RegisterTM(DDS.BGRaaCLS, DDS.BGR, "bgrid", DDS.CLS, "clsid");
		myAW.RegisterTM(DDS.CLSaaUT, DDS.CLS, "clsid", DDS.UT, "utid");
		myAW.RegisterTM(DDS.UTaaUTL, DDS.UT, "utid", DDS.UTL, "utlid");
		myAW.RegisterTM(DDS.HZDaaFLT, DDS.HZD, "hzdid", DDS.FLT, "fltid");
		
		myAW.RegisterTM(DDS.FLTaaSRQ, DDS.FLT, "fltid", DDS.SRQ, "srqid");
		
		
		System.out.println("Index created: DDS");
	}
}
