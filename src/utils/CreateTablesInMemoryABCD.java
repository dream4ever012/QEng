package utils;

import ResourceStrings.ABCD;
import optimizer.QueryManager;

//import ResourceStrings.CSC553;

public class CreateTablesInMemoryABCD {
	public static void createTablesInMemoryCSC553(QueryManager myAW){
		
		//myAW.importCSVAsTable(CSC553.part_FP, CSC553.part);
		System.out.println("table created: CSC553");
	}
	
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
		
		myAW.importCSVAsTable(ABCD.AaaB6_FP, ABCD.AaaB6);
		myAW.importCSVAsTable(ABCD.BaaC6_FP, ABCD.BaaC6);
		myAW.importCSVAsTable(ABCD.CaaD6_FP, ABCD.CaaD6);
		myAW.importCSVAsTable(ABCD.AaaBaaC6_FP, ABCD.AaaBaaC6);
		myAW.importCSVAsTable(ABCD.BaaCaaD6_FP, ABCD.BaaCaaD6);
		System.out.println("table created: ABCD");
	}
	
	public static void registerTMABCD(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB, ABCD.A, "AID", ABCD.B, "BID");
		myAW.RegisterTM(ABCD.BaaC, ABCD.B, "BID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.CaaD, ABCD.C, "CID", ABCD.D, "DID");
		myAW.RegisterTM(ABCD.AaaB6, ABCD.A, "AID", ABCD.B, "BID");
		myAW.RegisterTM(ABCD.BaaC6, ABCD.B, "BID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.CaaD6, ABCD.C, "CID", ABCD.D, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC, ABCD.A, "AID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD, ABCD.B, "BID", ABCD.D, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC6, ABCD.A, "AID", ABCD.C, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD6, ABCD.B, "BID", ABCD.D, "DID");
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
		myAW.importCSVAsTable(ABCD.AaaB5_FP, ABCD.AaaB5);
		myAW.importCSVAsTable(ABCD.BaaC5_FP, ABCD.BaaC5);
		myAW.importCSVAsTable(ABCD.CaaD5_FP, ABCD.CaaD5);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC2_FP, ABCD.AaaBaaC2);
		myAW.importCSVAsTable(ABCD.BaaCaaD2_FP, ABCD.BaaCaaD2);
		myAW.importCSVAsTable(ABCD.AaaBaaC3_FP, ABCD.AaaBaaC3);
		myAW.importCSVAsTable(ABCD.BaaCaaD3_FP, ABCD.BaaCaaD3);
		myAW.importCSVAsTable(ABCD.AaaBaaC5_FP, ABCD.AaaBaaC5);
		myAW.importCSVAsTable(ABCD.BaaCaaD5_FP, ABCD.BaaCaaD5);
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
		myAW.RegisterTM(ABCD.AaaB5, ABCD.A6k, "AID", ABCD.B6k, "BID");
		myAW.RegisterTM(ABCD.BaaC5, ABCD.B6k, "BID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.CaaD5, ABCD.C3k, "CID", ABCD.D3k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC3, ABCD.A6k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD3, ABCD.B6k, "BID", ABCD.D3k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC5, ABCD.A6k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD5, ABCD.B6k, "BID", ABCD.D3k, "DID");
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
		System.out.println("table created: ABCD3");
	}
	
	public static void registerTMABCD3(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB4, ABCD.A3k, "AID", ABCD.B3k, "BID");
		myAW.RegisterTM(ABCD.BaaC4, ABCD.B3k, "BID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.CaaD4, ABCD.C1_5k, "CID", ABCD.D1_5k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC4, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD4, ABCD.B3k, "BID", ABCD.D1_5k, "DID");
		System.out.println("Index created: ABCD3");
	}
	
	public static void createTablesInMemoryABCD4(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A2k_FP, ABCD.A2k);
		myAW.importCSVAsTable(ABCD.B2k_FP, ABCD.B2k);
		myAW.importCSVAsTable(ABCD.C1k_FP, ABCD.C1k);
		myAW.importCSVAsTable(ABCD.D1k_FP, ABCD.D1k);
		myAW.importCSVAsTable(ABCD.AaaB7_FP, ABCD.AaaB7);
		myAW.importCSVAsTable(ABCD.BaaC7_FP, ABCD.BaaC7);
		myAW.importCSVAsTable(ABCD.CaaD7_FP, ABCD.CaaD7);
		myAW.importCSVAsTable(ABCD.AaaB8_FP, ABCD.AaaB8);
		myAW.importCSVAsTable(ABCD.BaaC8_FP, ABCD.BaaC8);
		myAW.importCSVAsTable(ABCD.CaaD8_FP, ABCD.CaaD8);
		
		myAW.importCSVAsTable(ABCD.AaaB_B1k_FP, ABCD.AaaB_B1k);
		myAW.importCSVAsTable(ABCD.BaaC_B1k_FP, ABCD.BaaC_B1k);
		myAW.importCSVAsTable(ABCD.CaaD_B1k_FP, ABCD.CaaD_B1k);
		myAW.importCSVAsTable(ABCD.AaaB_B2k_FP, ABCD.AaaB_B2k);
		myAW.importCSVAsTable(ABCD.BaaC_B2k_FP, ABCD.BaaC_B2k);
		myAW.importCSVAsTable(ABCD.CaaD_B2k_FP, ABCD.CaaD_B2k);
		myAW.importCSVAsTable(ABCD.AaaB_B3k_FP, ABCD.AaaB_B3k);
		myAW.importCSVAsTable(ABCD.BaaC_B3k_FP, ABCD.BaaC_B3k);
		myAW.importCSVAsTable(ABCD.CaaD_B3k_FP, ABCD.CaaD_B3k);
		myAW.importCSVAsTable(ABCD.AaaB_B4k_FP, ABCD.AaaB_B4k);
		myAW.importCSVAsTable(ABCD.BaaC_B4k_FP, ABCD.BaaC_B4k);
		myAW.importCSVAsTable(ABCD.CaaD_B4k_FP, ABCD.CaaD_B4k);
		myAW.importCSVAsTable(ABCD.AaaB_B5k_FP, ABCD.AaaB_B5k);
		myAW.importCSVAsTable(ABCD.BaaC_B5k_FP, ABCD.BaaC_B5k);
		myAW.importCSVAsTable(ABCD.CaaD_B5k_FP, ABCD.CaaD_B5k);
		myAW.importCSVAsTable(ABCD.AaaB_B6k_FP, ABCD.AaaB_B6k);
		myAW.importCSVAsTable(ABCD.BaaC_B6k_FP, ABCD.BaaC_B6k);
		myAW.importCSVAsTable(ABCD.CaaD_B6k_FP, ABCD.CaaD_B6k);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC7_FP, ABCD.AaaBaaC7);
		myAW.importCSVAsTable(ABCD.BaaCaaD7_FP, ABCD.BaaCaaD7);
		myAW.importCSVAsTable(ABCD.AaaBaaC8_FP, ABCD.AaaBaaC8);
		myAW.importCSVAsTable(ABCD.BaaCaaD8_FP, ABCD.BaaCaaD8);
		System.out.println("table created: ABCD4");
	}
	
	public static void registerTMABCD4(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB7, ABCD.A2k, "AID", ABCD.B2k, "BID");
		myAW.RegisterTM(ABCD.BaaC7, ABCD.B2k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD7, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC7, ABCD.A2k, "AID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD7, ABCD.B2k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaB8, ABCD.A2k, "AID", ABCD.B2k, "BID");
		myAW.RegisterTM(ABCD.BaaC8, ABCD.B2k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD8, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC8, ABCD.A2k, "AID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD8, ABCD.B2k, "BID", ABCD.D1k, "DID");
		System.out.println("Index created: ABCD4");
	}
	
	public static void createTablesInMemoryABCD5(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A2k_FP, ABCD.A2k);
		myAW.importCSVAsTable(ABCD.A3k_FP, ABCD.A3k);
		myAW.importCSVAsTable(ABCD.B2k_FP, ABCD.B2k);
		myAW.importCSVAsTable(ABCD.B1k_FP, ABCD.B1k);
		myAW.importCSVAsTable(ABCD.B3k_FP, ABCD.B3k);
		myAW.importCSVAsTable(ABCD.B4k_FP, ABCD.B4k);
		myAW.importCSVAsTable(ABCD.B5k_FP, ABCD.B5k);
		myAW.importCSVAsTable(ABCD.C1k_FP, ABCD.C1k);
		myAW.importCSVAsTable(ABCD.C2k_FP, ABCD.C2k);
		myAW.importCSVAsTable(ABCD.C3k_FP, ABCD.C3k);
		myAW.importCSVAsTable(ABCD.C4k_FP, ABCD.C4k);
		myAW.importCSVAsTable(ABCD.C1_5k_FP, ABCD.C1_5k);
		myAW.importCSVAsTable(ABCD.D1_5k_FP, ABCD.D1_5k);
		myAW.importCSVAsTable(ABCD.D1k_FP, ABCD.D1k);
		myAW.importCSVAsTable(ABCD.D2k_FP, ABCD.D2k);
		myAW.importCSVAsTable(ABCD.AaaB7_FP, ABCD.AaaB7);
		myAW.importCSVAsTable(ABCD.BaaC7_FP, ABCD.BaaC7);
		myAW.importCSVAsTable(ABCD.CaaD7_FP, ABCD.CaaD7);
		myAW.importCSVAsTable(ABCD.AaaB9_FP, ABCD.AaaB9);
		myAW.importCSVAsTable(ABCD.BaaC9_FP, ABCD.BaaC9);
		myAW.importCSVAsTable(ABCD.CaaD9_FP, ABCD.CaaD9);
		myAW.importCSVAsTable(ABCD.AaaB10_FP, ABCD.AaaB10);
		myAW.importCSVAsTable(ABCD.BaaC10_FP, ABCD.BaaC10);
		myAW.importCSVAsTable(ABCD.CaaD10_FP, ABCD.CaaD10);
		myAW.importCSVAsTable(ABCD.AaaB11_FP, ABCD.AaaB11);
		myAW.importCSVAsTable(ABCD.BaaC11_FP, ABCD.BaaC11);
		myAW.importCSVAsTable(ABCD.CaaD11_FP, ABCD.CaaD11);
		myAW.importCSVAsTable(ABCD.AaaB12_FP, ABCD.AaaB12);
		myAW.importCSVAsTable(ABCD.BaaC12_FP, ABCD.BaaC12);
		myAW.importCSVAsTable(ABCD.CaaD12_FP, ABCD.CaaD12);
		myAW.importCSVAsTable(ABCD.AaaB13_FP, ABCD.AaaB13);
		myAW.importCSVAsTable(ABCD.BaaC13_FP, ABCD.BaaC13);
		myAW.importCSVAsTable(ABCD.CaaD13_FP, ABCD.CaaD13);
		myAW.importCSVAsTable(ABCD.AaaB14_FP, ABCD.AaaB14);
		myAW.importCSVAsTable(ABCD.BaaC14_FP, ABCD.BaaC14);
		myAW.importCSVAsTable(ABCD.CaaD14_FP, ABCD.CaaD14);
		myAW.importCSVAsTable(ABCD.AaaB15_FP, ABCD.AaaB15);
		myAW.importCSVAsTable(ABCD.BaaC15_FP, ABCD.BaaC15);
		myAW.importCSVAsTable(ABCD.CaaD15_FP, ABCD.CaaD15);
		myAW.importCSVAsTable(ABCD.AaaB16_FP, ABCD.AaaB16);
		myAW.importCSVAsTable(ABCD.BaaC16_FP, ABCD.BaaC16);
		myAW.importCSVAsTable(ABCD.CaaD16_FP, ABCD.CaaD16);
		myAW.importCSVAsTable(ABCD.AaaB17_FP, ABCD.AaaB17);
		myAW.importCSVAsTable(ABCD.BaaC17_FP, ABCD.BaaC17);
		myAW.importCSVAsTable(ABCD.CaaD17_FP, ABCD.CaaD17);
		myAW.importCSVAsTable(ABCD.AaaB18_FP, ABCD.AaaB18);
		myAW.importCSVAsTable(ABCD.BaaC18_FP, ABCD.BaaC18);
		myAW.importCSVAsTable(ABCD.CaaD18_FP, ABCD.CaaD18);
		myAW.importCSVAsTable(ABCD.AaaB19_FP, ABCD.AaaB19);
		myAW.importCSVAsTable(ABCD.BaaC19_FP, ABCD.BaaC19);
		myAW.importCSVAsTable(ABCD.CaaD19_FP, ABCD.CaaD19);
		myAW.importCSVAsTable(ABCD.AaaB20_FP, ABCD.AaaB20);
		myAW.importCSVAsTable(ABCD.BaaC20_FP, ABCD.BaaC20);
		myAW.importCSVAsTable(ABCD.CaaD20_FP, ABCD.CaaD20);
		myAW.importCSVAsTable(ABCD.AaaB21_FP, ABCD.AaaB21);
		myAW.importCSVAsTable(ABCD.BaaC21_FP, ABCD.BaaC21);
		myAW.importCSVAsTable(ABCD.CaaD21_FP, ABCD.CaaD21);
		myAW.importCSVAsTable(ABCD.AaaB22_FP, ABCD.AaaB22);
		myAW.importCSVAsTable(ABCD.BaaC22_FP, ABCD.BaaC22);
		myAW.importCSVAsTable(ABCD.CaaD22_FP, ABCD.CaaD22);
		myAW.importCSVAsTable(ABCD.AaaB22_FP, ABCD.AaaB23);
		myAW.importCSVAsTable(ABCD.BaaC23_FP, ABCD.BaaC23);
		myAW.importCSVAsTable(ABCD.CaaD23_FP, ABCD.CaaD23);
		myAW.importCSVAsTable(ABCD.AaaB24_FP, ABCD.AaaB24);
		myAW.importCSVAsTable(ABCD.BaaC24_FP, ABCD.BaaC24);
		myAW.importCSVAsTable(ABCD.CaaD24_FP, ABCD.CaaD24);
		myAW.importCSVAsTable(ABCD.AaaB25_FP, ABCD.AaaB25);
		myAW.importCSVAsTable(ABCD.BaaC25_FP, ABCD.BaaC25);
		myAW.importCSVAsTable(ABCD.CaaD25_FP, ABCD.CaaD25);
		myAW.importCSVAsTable(ABCD.AaaB26_FP, ABCD.AaaB26);
		myAW.importCSVAsTable(ABCD.BaaC26_FP, ABCD.BaaC26);
		myAW.importCSVAsTable(ABCD.CaaD26_FP, ABCD.CaaD26);
		myAW.importCSVAsTable(ABCD.AaaB27_FP, ABCD.AaaB27);
		myAW.importCSVAsTable(ABCD.BaaC27_FP, ABCD.BaaC27);
		myAW.importCSVAsTable(ABCD.CaaD27_FP, ABCD.CaaD27);
		myAW.importCSVAsTable(ABCD.AaaB28_FP, ABCD.AaaB28);
		myAW.importCSVAsTable(ABCD.BaaC28_FP, ABCD.BaaC28);
		myAW.importCSVAsTable(ABCD.CaaD28_FP, ABCD.CaaD28);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC7_FP, ABCD.AaaBaaC7);
		myAW.importCSVAsTable(ABCD.BaaCaaD7_FP, ABCD.BaaCaaD7);
		myAW.importCSVAsTable(ABCD.AaaBaaC9_FP, ABCD.AaaBaaC9);
		myAW.importCSVAsTable(ABCD.BaaCaaD9_FP, ABCD.BaaCaaD9);
		myAW.importCSVAsTable(ABCD.AaaBaaC10_FP, ABCD.AaaBaaC10);
		myAW.importCSVAsTable(ABCD.BaaCaaD10_FP, ABCD.BaaCaaD10);
		myAW.importCSVAsTable(ABCD.AaaBaaC11_FP, ABCD.AaaBaaC11);
		myAW.importCSVAsTable(ABCD.BaaCaaD11_FP, ABCD.BaaCaaD11);
		myAW.importCSVAsTable(ABCD.AaaBaaC12_FP, ABCD.AaaBaaC12);
		myAW.importCSVAsTable(ABCD.BaaCaaD12_FP, ABCD.BaaCaaD12);
		myAW.importCSVAsTable(ABCD.AaaBaaC13_FP, ABCD.AaaBaaC13);
		myAW.importCSVAsTable(ABCD.BaaCaaD13_FP, ABCD.BaaCaaD13);
		myAW.importCSVAsTable(ABCD.AaaBaaC14_FP, ABCD.AaaBaaC14);
		myAW.importCSVAsTable(ABCD.BaaCaaD14_FP, ABCD.BaaCaaD14);
		myAW.importCSVAsTable(ABCD.AaaBaaC15_FP, ABCD.AaaBaaC15);
		myAW.importCSVAsTable(ABCD.BaaCaaD15_FP, ABCD.BaaCaaD15);
		myAW.importCSVAsTable(ABCD.AaaBaaC16_FP, ABCD.AaaBaaC16);
		myAW.importCSVAsTable(ABCD.BaaCaaD16_FP, ABCD.BaaCaaD16);
		myAW.importCSVAsTable(ABCD.AaaBaaC17_FP, ABCD.AaaBaaC17);
		myAW.importCSVAsTable(ABCD.BaaCaaD17_FP, ABCD.BaaCaaD17);
		myAW.importCSVAsTable(ABCD.AaaBaaC18_FP, ABCD.AaaBaaC18);
		myAW.importCSVAsTable(ABCD.BaaCaaD18_FP, ABCD.BaaCaaD18);
		myAW.importCSVAsTable(ABCD.AaaBaaC19_FP, ABCD.AaaBaaC19);
		myAW.importCSVAsTable(ABCD.BaaCaaD19_FP, ABCD.BaaCaaD19);
		myAW.importCSVAsTable(ABCD.AaaBaaC20_FP, ABCD.AaaBaaC20);
		myAW.importCSVAsTable(ABCD.BaaCaaD20_FP, ABCD.BaaCaaD20);
		myAW.importCSVAsTable(ABCD.AaaBaaC21_FP, ABCD.AaaBaaC21);
		myAW.importCSVAsTable(ABCD.BaaCaaD21_FP, ABCD.BaaCaaD21);
		myAW.importCSVAsTable(ABCD.AaaBaaC22_FP, ABCD.AaaBaaC22);
		myAW.importCSVAsTable(ABCD.BaaCaaD22_FP, ABCD.BaaCaaD22);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC23_FP, ABCD.AaaBaaC23);
		myAW.importCSVAsTable(ABCD.BaaCaaD23_FP, ABCD.BaaCaaD23);
//		myAW.importCSVAsTable(ABCD.AaaBaaC24_FP, ABCD.AaaBaaC24);
//		myAW.importCSVAsTable(ABCD.BaaCaaD24_FP, ABCD.BaaCaaD24);
//		myAW.importCSVAsTable(ABCD.AaaBaaC25_FP, ABCD.AaaBaaC25);
//		myAW.importCSVAsTable(ABCD.BaaCaaD25_FP, ABCD.BaaCaaD25);
//		myAW.importCSVAsTable(ABCD.AaaBaaC26_FP, ABCD.AaaBaaC26);
//		myAW.importCSVAsTable(ABCD.BaaCaaD26_FP, ABCD.BaaCaaD26);
//		myAW.importCSVAsTable(ABCD.AaaBaaC27_FP, ABCD.AaaBaaC27);
//		myAW.importCSVAsTable(ABCD.BaaCaaD27_FP, ABCD.BaaCaaD27);
//		myAW.importCSVAsTable(ABCD.AaaBaaC28_FP, ABCD.AaaBaaC28);
//		myAW.importCSVAsTable(ABCD.BaaCaaD28_FP, ABCD.BaaCaaD28);
		System.out.println("table created: ABCD5");
	}
	
	public static void registerTMABCD5(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB7, ABCD.A2k, "AID", ABCD.B2k, "BID");
		myAW.RegisterTM(ABCD.BaaC7, ABCD.B2k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD7, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaB9, ABCD.A2k, "AID", ABCD.B1k, "BID");
		myAW.RegisterTM(ABCD.BaaC9, ABCD.B1k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD9, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC9, ABCD.A2k, "AID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD9, ABCD.B1k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaB10, ABCD.A2k, "AID", ABCD.B4k, "BID");
		myAW.RegisterTM(ABCD.BaaC10, ABCD.B4k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD10, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaB11, ABCD.A2k, "AID", ABCD.B3k, "BID");
		myAW.RegisterTM(ABCD.BaaC11, ABCD.B3k, "BID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.CaaD11, ABCD.C1k, "CID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC10, ABCD.A2k, "AID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD10, ABCD.B4k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC11, ABCD.A2k, "AID", ABCD.C1k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD11, ABCD.B3k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC12, ABCD.A2k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD12, ABCD.B2k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC13, ABCD.A2k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD13, ABCD.B1k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC14, ABCD.A2k, "AID", ABCD.C4k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD14, ABCD.B1k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC15, ABCD.A2k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD15, ABCD.B4k, "BID", ABCD.D1k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC16, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD16, ABCD.B5k, "BID", ABCD.D2k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC17, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD17, ABCD.B3k, "BID", ABCD.D2k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC18, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD18, ABCD.B3k, "BID", ABCD.D2k, "DID");
		myAW.RegisterTM(ABCD.AaaBaaC19, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD19, ABCD.B3k, "BID", ABCD.D2k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC20, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD20, ABCD.B3k, "BID", ABCD.D2k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC21, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD21, ABCD.B3k, "BID", ABCD.D2k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC22, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD22, ABCD.B3k, "BID", ABCD.D2k, "DID");

		myAW.RegisterTM(ABCD.AaaBaaC23, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD23, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC24, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD24, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC25, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD25, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC26, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD26, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC27, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD27, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC28, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD28, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	

		System.out.println("Index created: ABCD5");
		
		
	}

	public static void createTablesInMemoryABCD6(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A2k_FP, ABCD.A2k);
		myAW.importCSVAsTable(ABCD.A3k_FP, ABCD.A3k);
		myAW.importCSVAsTable(ABCD.B2k_FP, ABCD.B2k);
		myAW.importCSVAsTable(ABCD.B1k_FP, ABCD.B1k);
		myAW.importCSVAsTable(ABCD.B3k_FP, ABCD.B3k);
		myAW.importCSVAsTable(ABCD.B4k_FP, ABCD.B4k);
		myAW.importCSVAsTable(ABCD.B5k_FP, ABCD.B5k);
		myAW.importCSVAsTable(ABCD.C1k_FP, ABCD.C1k);
		myAW.importCSVAsTable(ABCD.C2k_FP, ABCD.C2k);
		myAW.importCSVAsTable(ABCD.C3k_FP, ABCD.C3k);
		myAW.importCSVAsTable(ABCD.C4k_FP, ABCD.C4k);
		myAW.importCSVAsTable(ABCD.C1_5k_FP, ABCD.C1_5k);
		myAW.importCSVAsTable(ABCD.D1_5k_FP, ABCD.D1_5k);
		myAW.importCSVAsTable(ABCD.D1k_FP, ABCD.D1k);
		myAW.importCSVAsTable(ABCD.D2k_FP, ABCD.D2k);
		myAW.importCSVAsTable(ABCD.AaaB21_FP, ABCD.AaaB21);
		myAW.importCSVAsTable(ABCD.BaaC21_FP, ABCD.BaaC21);
		myAW.importCSVAsTable(ABCD.CaaD21_FP, ABCD.CaaD21);
		myAW.importCSVAsTable(ABCD.AaaB22_FP, ABCD.AaaB22);
		myAW.importCSVAsTable(ABCD.BaaC22_FP, ABCD.BaaC22);
		myAW.importCSVAsTable(ABCD.CaaD22_FP, ABCD.CaaD22);
		myAW.importCSVAsTable(ABCD.AaaB22_FP, ABCD.AaaB23);
		myAW.importCSVAsTable(ABCD.BaaC23_FP, ABCD.BaaC23);
		myAW.importCSVAsTable(ABCD.CaaD23_FP, ABCD.CaaD23);
		myAW.importCSVAsTable(ABCD.AaaB24_FP, ABCD.AaaB24);
		myAW.importCSVAsTable(ABCD.BaaC24_FP, ABCD.BaaC24);
		myAW.importCSVAsTable(ABCD.CaaD24_FP, ABCD.CaaD24);
		myAW.importCSVAsTable(ABCD.AaaB25_FP, ABCD.AaaB25);
		myAW.importCSVAsTable(ABCD.BaaC25_FP, ABCD.BaaC25);
		myAW.importCSVAsTable(ABCD.CaaD25_FP, ABCD.CaaD25);
		myAW.importCSVAsTable(ABCD.AaaB26_FP, ABCD.AaaB26);
		myAW.importCSVAsTable(ABCD.BaaC26_FP, ABCD.BaaC26);
		myAW.importCSVAsTable(ABCD.CaaD26_FP, ABCD.CaaD26);
		myAW.importCSVAsTable(ABCD.AaaB27_FP, ABCD.AaaB27);
		myAW.importCSVAsTable(ABCD.BaaC27_FP, ABCD.BaaC27);
		myAW.importCSVAsTable(ABCD.CaaD27_FP, ABCD.CaaD27);
		myAW.importCSVAsTable(ABCD.AaaB28_FP, ABCD.AaaB28);
		myAW.importCSVAsTable(ABCD.BaaC28_FP, ABCD.BaaC28);
		myAW.importCSVAsTable(ABCD.CaaD28_FP, ABCD.CaaD28);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC20_FP, ABCD.AaaBaaC20);
		myAW.importCSVAsTable(ABCD.BaaCaaD20_FP, ABCD.BaaCaaD20);
		myAW.importCSVAsTable(ABCD.AaaBaaC21_FP, ABCD.AaaBaaC21);
		myAW.importCSVAsTable(ABCD.BaaCaaD21_FP, ABCD.BaaCaaD21);
		myAW.importCSVAsTable(ABCD.AaaBaaC22_FP, ABCD.AaaBaaC22);
		myAW.importCSVAsTable(ABCD.BaaCaaD22_FP, ABCD.BaaCaaD22);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC23_FP, ABCD.AaaBaaC23);
		myAW.importCSVAsTable(ABCD.BaaCaaD23_FP, ABCD.BaaCaaD23);
		myAW.importCSVAsTable(ABCD.AaaBaaC24_FP, ABCD.AaaBaaC24);
		myAW.importCSVAsTable(ABCD.BaaCaaD24_FP, ABCD.BaaCaaD24);
		myAW.importCSVAsTable(ABCD.AaaBaaC25_FP, ABCD.AaaBaaC25);
		myAW.importCSVAsTable(ABCD.BaaCaaD25_FP, ABCD.BaaCaaD25);
		myAW.importCSVAsTable(ABCD.AaaBaaC26_FP, ABCD.AaaBaaC26);
		myAW.importCSVAsTable(ABCD.BaaCaaD26_FP, ABCD.BaaCaaD26);
		myAW.importCSVAsTable(ABCD.AaaBaaC27_FP, ABCD.AaaBaaC27);
		myAW.importCSVAsTable(ABCD.BaaCaaD27_FP, ABCD.BaaCaaD27);
		myAW.importCSVAsTable(ABCD.AaaBaaC28_FP, ABCD.AaaBaaC28);
		myAW.importCSVAsTable(ABCD.BaaCaaD28_FP, ABCD.BaaCaaD28);
		
		myAW.importCSVAsTable(ABCD.AaaB99_FP, ABCD.AaaB99);
		myAW.importCSVAsTable(ABCD.BaaC99_FP, ABCD.BaaC99);
		myAW.importCSVAsTable(ABCD.CaaD99_FP, ABCD.CaaD99);
		myAW.importCSVAsTable(ABCD.AaaBaaC99_FP, ABCD.AaaBaaC99);
		myAW.importCSVAsTable(ABCD.BaaCaaD99_FP, ABCD.BaaCaaD99);
		
		myAW.importCSVAsTable(ABCD.AaaB98_FP, ABCD.AaaB98);
		myAW.importCSVAsTable(ABCD.BaaC98_FP, ABCD.BaaC98);
		myAW.importCSVAsTable(ABCD.CaaD98_FP, ABCD.CaaD98);
		myAW.importCSVAsTable(ABCD.AaaBaaC98_FP, ABCD.AaaBaaC98);
		myAW.importCSVAsTable(ABCD.BaaCaaD98_FP, ABCD.BaaCaaD98);
		
		System.out.println("table created: ABCD6");
	}
	
	public static void registerTMABCD6(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaBaaC20, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD20, ABCD.B3k, "BID", ABCD.D2k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC21, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD21, ABCD.B3k, "BID", ABCD.D2k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC22, ABCD.A3k, "AID", ABCD.C2k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD22, ABCD.B3k, "BID", ABCD.D2k, "DID");

		myAW.RegisterTM(ABCD.AaaBaaC23, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD23, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC24, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD24, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC25, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD25, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC26, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD26, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC27, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD27, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	
		myAW.RegisterTM(ABCD.AaaBaaC28, ABCD.A3k, "AID", ABCD.C1_5k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD28, ABCD.B3k, "BID", ABCD.D1_5k, "DID");	

		
		myAW.RegisterTM(ABCD.AaaBaaC99, ABCD.A2k, "AID", ABCD.C3k, "CID");
		myAW.RegisterTM(ABCD.BaaCaaD99, ABCD.B4k, "BID", ABCD.D1k, "DID");
		System.out.println("Index created: ABCD6");
		
		
	}
	
	public static void createTablesInMemoryABCD7(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A4k_FP, ABCD.A4k);
		myAW.importCSVAsTable(ABCD.B3k_FP, ABCD.B3k);
		myAW.importCSVAsTable(ABCD.B4k_FP, ABCD.B4k);
		
		myAW.importCSVAsTable(ABCD.C9k01_FP, ABCD.C9k01);

//		myAW.importCSVAsTable(ABCD.C9k0_1_FP, ABCD.C9k0_1);
//		myAW.importCSVAsTable(ABCD.C9k0_2_FP, ABCD.C9k0_2);
//		myAW.importCSVAsTable(ABCD.C9k0_3_FP, ABCD.C9k0_3);
//		myAW.importCSVAsTable(ABCD.C9k0_4_FP, ABCD.C9k0_4);
//		myAW.importCSVAsTable(ABCD.C9k0_5_FP, ABCD.C9k0_5);
//		myAW.importCSVAsTable(ABCD.C9k0_6_FP, ABCD.C9k0_6);
//		myAW.importCSVAsTable(ABCD.C9k0_7_FP, ABCD.C9k0_7);
//		myAW.importCSVAsTable(ABCD.C9k0_8_FP, ABCD.C9k0_8);
//		myAW.importCSVAsTable(ABCD.C9k0_9_FP, ABCD.C9k0_9);
//		myAW.importCSVAsTable(ABCD.C9k1_0_FP, ABCD.C9k1_0);
		myAW.importCSVAsTable(ABCD.C10k_FP, ABCD.C10k);
		myAW.importCSVAsTable(ABCD.D4k_FP, ABCD.D4k);

		myAW.importCSVAsTable(ABCD.AaaB29_FP, ABCD.AaaB29);
		myAW.importCSVAsTable(ABCD.BaaC29_FP, ABCD.BaaC29);
		myAW.importCSVAsTable(ABCD.CaaD29_FP, ABCD.CaaD29);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC29_FP, ABCD.AaaBaaC29);
		myAW.importCSVAsTable(ABCD.BaaCaaD29_FP, ABCD.BaaCaaD29);
		
		System.out.println("table created: ABCD7");
	}
	
	public static void registerTMABCD7(QueryManager myAW){
		myAW.RegisterTM(ABCD.AaaB29, ABCD.A4k, "AID", ABCD.B4k, "BID");	
		myAW.RegisterTM(ABCD.BaaC29, ABCD.B4k, "BID", ABCD.C10k, "CID");	
		myAW.RegisterTM(ABCD.CaaD29, ABCD.C10k, "CID", ABCD.D4k, "DID");	

		myAW.RegisterTM(ABCD.AaaBaaC29, ABCD.A4k, "AID", ABCD.C10k, "CID");	
		myAW.RegisterTM(ABCD.BaaCaaD29, ABCD.B4k, "BID", ABCD.D4k, "DID");	

		
		System.out.println("Index created: ABCD7");		
	}
	
	public static void createTablesInMemoryABCD8(QueryManager myAW){
		
		myAW.importCSVAsTable(ABCD.A4k_FP, ABCD.A4k);
		myAW.importCSVAsTable(ABCD.B3k_FP, ABCD.B3k);
		myAW.importCSVAsTable(ABCD.B4k_FP, ABCD.B4k);
		myAW.importCSVAsTable(ABCD.C9k0_1_FP, ABCD.C9k0_1);
		myAW.importCSVAsTable(ABCD.C9k0_2_FP, ABCD.C9k0_2);
		myAW.importCSVAsTable(ABCD.C9k0_3_FP, ABCD.C9k0_3);
		myAW.importCSVAsTable(ABCD.C9k0_4_FP, ABCD.C9k0_4);
		myAW.importCSVAsTable(ABCD.C9k0_5_FP, ABCD.C9k0_5);
		myAW.importCSVAsTable(ABCD.C9k0_6_FP, ABCD.C9k0_6);
		myAW.importCSVAsTable(ABCD.C9k0_7_FP, ABCD.C9k0_7);
		myAW.importCSVAsTable(ABCD.C9k0_8_FP, ABCD.C9k0_8);
		myAW.importCSVAsTable(ABCD.C9k0_9_FP, ABCD.C9k0_9);
		myAW.importCSVAsTable(ABCD.C9k1_0_FP, ABCD.C9k1_0);
		myAW.importCSVAsTable(ABCD.BCaaCt28_FP, ABCD.BCaaCt28);

		myAW.importCSVAsTable(ABCD.BCaaCt0_1_FP, ABCD.BCaaCt0_1);
		myAW.importCSVAsTable(ABCD.BCaaCt0_2_FP, ABCD.BCaaCt0_2);
		myAW.importCSVAsTable(ABCD.BCaaCt0_3_FP, ABCD.BCaaCt0_3);
		myAW.importCSVAsTable(ABCD.BCaaCt0_4_FP, ABCD.BCaaCt0_4);
		myAW.importCSVAsTable(ABCD.BCaaCt0_5_FP, ABCD.BCaaCt0_5);
		myAW.importCSVAsTable(ABCD.BCaaCt0_6_FP, ABCD.BCaaCt0_6);
		myAW.importCSVAsTable(ABCD.BCaaCt0_7_FP, ABCD.BCaaCt0_7);
		myAW.importCSVAsTable(ABCD.BCaaCt0_8_FP, ABCD.BCaaCt0_8);
		myAW.importCSVAsTable(ABCD.BCaaCt0_9_FP, ABCD.BCaaCt0_9);
		myAW.importCSVAsTable(ABCD.BCaaCt1_0_FP, ABCD.BCaaCt1_0);
		
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_1_FP, ABCD.ABaaBCaCt0_1);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_2_FP, ABCD.ABaaBCaCt0_2);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_3_FP, ABCD.ABaaBCaCt0_3);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_4_FP, ABCD.ABaaBCaCt0_4);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_5_FP, ABCD.ABaaBCaCt0_5);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_6_FP, ABCD.ABaaBCaCt0_6);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_7_FP, ABCD.ABaaBCaCt0_7);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_8_FP, ABCD.ABaaBCaCt0_8);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt0_9_FP, ABCD.ABaaBCaCt0_9);
		myAW.importCSVAsTable(ABCD.ABaaBCaCt1_0_FP, ABCD.ABaaBCaCt1_0);
		
		myAW.importCSVAsTable(ABCD.BCaCt0_1aaCD_FP, ABCD.BCaCt0_1aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_2aaCD_FP, ABCD.BCaCt0_2aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_3aaCD_FP, ABCD.BCaCt0_3aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_4aaCD_FP, ABCD.BCaCt0_4aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_5aaCD_FP, ABCD.BCaCt0_5aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_6aaCD_FP, ABCD.BCaCt0_6aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_7aaCD_FP, ABCD.BCaCt0_7aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_8aaCD_FP, ABCD.BCaCt0_8aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt0_9aaCD_FP, ABCD.BCaCt0_9aaCD);
		myAW.importCSVAsTable(ABCD.BCaCt1_0aaCD_FP, ABCD.BCaCt1_0aaCD);
		
		
		myAW.importCSVAsTable(ABCD.C10k_FP, ABCD.C10k);
		myAW.importCSVAsTable(ABCD.D4k_FP, ABCD.D4k);

		myAW.importCSVAsTable(ABCD.AaaB29_FP, ABCD.AaaB29);
		myAW.importCSVAsTable(ABCD.BaaC29_FP, ABCD.BaaC29);
		myAW.importCSVAsTable(ABCD.CaaD29_FP, ABCD.CaaD29);
		myAW.importCSVAsTable(ABCD.AaaB30_FP, ABCD.AaaB30);
		myAW.importCSVAsTable(ABCD.BaaC30_FP, ABCD.BaaC30);
		myAW.importCSVAsTable(ABCD.CaaD30_FP, ABCD.CaaD30);
		
		myAW.importCSVAsTable(ABCD.AaaBaaC29_FP, ABCD.AaaBaaC29);
		myAW.importCSVAsTable(ABCD.BaaCaaD29_FP, ABCD.BaaCaaD29);
		
		System.out.println("table created: ABCD7");
	}
	
}
