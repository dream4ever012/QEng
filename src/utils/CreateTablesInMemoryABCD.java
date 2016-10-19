package utils;

import ResourceStrings.ABCD;
import optimizer.QueryManager;

public class CreateTablesInMemoryABCD {
	public static void createTablesInMemoryABCD(QueryManager myAW){
				
		myAW.importCSVAsTable(ABCD.A_FP, ABCD.A);
		myAW.importCSVAsTable(ABCD.AaaB_FP, ABCD.AaaB);
		myAW.importCSVAsTable(ABCD.B_FP, ABCD.B);
		myAW.importCSVAsTable(ABCD.BaaC_FP, ABCD.BaaC);
		myAW.importCSVAsTable(ABCD.C_FP, ABCD.C);
		myAW.importCSVAsTable(ABCD.CaaD_FP, ABCD.CaaD);
		myAW.importCSVAsTable(ABCD.D_FP, ABCD.D);
		myAW.importCSVAsTable(ABCD.AaaBaaC_FP, ABCD.AaaBaaC);
		myAW.importCSVAsTable(ABCD.BaaCaaD_FP, ABCD.BaaCaaD);
		System.out.println("table created: ABCD");
	}
	
	public static void registerTMABCD(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB, ABCD.A, "AID", ABCD.B, "BID");
		myAW.RegisterTM(ABCD.BaaC, ABCD.B, "BID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.CaaD, ABCD.C, "CID", ABCD.D, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC, ABCD.A, "AID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD, ABCD.B, "BID", ABCD.D, "DID");
		System.out.println("Index created: ABCD2");
	}
	
	public static void createTablesInMemoryABCD2(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A6k_FP, ABCD.A6k);
		myAW.importCSVAsTable(ABCD.AaaB2_FP, ABCD.AaaB2);
		myAW.importCSVAsTable(ABCD.B6k_FP, ABCD.B6k);
		myAW.importCSVAsTable(ABCD.BaaC2_FP, ABCD.BaaC2);
		myAW.importCSVAsTable(ABCD.C3k_FP, ABCD.C3k);
		myAW.importCSVAsTable(ABCD.CaaD2_FP, ABCD.CaaD2);
		myAW.importCSVAsTable(ABCD.D3k_FP, ABCD.D3k);
		
		myAW.importCSVAsTable(ABCD.AaaB3_FP, ABCD.AaaB3);
		myAW.importCSVAsTable(ABCD.BaaC3_FP, ABCD.BaaC3);
		myAW.importCSVAsTable(ABCD.CaaD3_FP, ABCD.CaaD3);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC2_FP, ABCD.AaaBaaC2);
		myAW.importCSVAsTable(ABCD.BaaCaaD2_FP, ABCD.BaaCaaD2);
		myAW.importCSVAsTable(ABCD.AaaBaaC3_FP, ABCD.AaaBaaC3);
		myAW.importCSVAsTable(ABCD.BaaCaaD3_FP, ABCD.BaaCaaD3);
		System.out.println("table created: ABCD");
	}
	
	public static void registerTMABCD2(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB2, ABCD.A6k, "AID", ABCD.B6k, "BID");
		myAW.RegisterTM(ABCD.BaaC2, ABCD.B6k, "BID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.CaaD2, ABCD.C3k, "CID", ABCD.D3k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC2, ABCD.A6k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD2, ABCD.B6k, "BID", ABCD.D3k, "DID");
		
		myAW.RegisterTM(ABCD.AaaB3, ABCD.A6k, "AID", ABCD.B6k, "BID");
		myAW.RegisterTM(ABCD.BaaC3, ABCD.B6k, "BID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.CaaD3, ABCD.C3k, "CID", ABCD.D3k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC3, ABCD.A6k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD3, ABCD.B6k, "BID", ABCD.D3k, "DID");
		System.out.println("Index created: ABCD2");
	}
	
	public static void createTablesInMemoryABCD3(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A3k_FP, ABCD.A3k);
		myAW.importCSVAsTable(ABCD.B3k_FP, ABCD.B3k);
		myAW.importCSVAsTable(ABCD.C1_5k_FP, ABCD.C1_5k);
		myAW.importCSVAsTable(ABCD.D1_5k_FP, ABCD.D1_5k);
		myAW.importCSVAsTable(ABCD.AaaB4_FP, ABCD.AaaB4);
		myAW.importCSVAsTable(ABCD.BaaC4_FP, ABCD.BaaC4);
		myAW.importCSVAsTable(ABCD.CaaD4_FP, ABCD.CaaD4);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC4_FP, ABCD.AaaBaaC4);
		myAW.importCSVAsTable(ABCD.BaaCaaD4_FP, ABCD.BaaCaaD4);
//		myAW.importCSVAsTable(ABCD.AaaBaaC3_FP, ABCD.AaaBaaC3);
//		myAW.importCSVAsTable(ABCD.BaaCaaD3_FP, ABCD.BaaCaaD3);
		System.out.println("table created: ABCD");
	}
	
	public static void registerTMABCD3(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB4, ABCD.A3k, "AID", ABCD.B3k, "BID");
		myAW.RegisterTM(ABCD.BaaC4, ABCD.B3k, "BID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.CaaD4, ABCD.C1_5k, "CID", ABCD.D1_5k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC4, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD4, ABCD.B3k, "BID", ABCD.D1_5k, "DID");
		System.out.println("Index created: ABCD3");
	}
}
