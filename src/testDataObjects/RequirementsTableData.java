package testDataObjects;

import java.util.ArrayList;

public class RequirementsTableData {

	public ArrayList<RequirementsRowData> ReqTestData;


	public RequirementsTableData()
	{
		ReqTestData = new ArrayList<RequirementsRowData>();
		populateList();

	}


	//RequirementsRowData(String id, String type, String clss, String cat,
	//String desc, String dc, String auth, String pri)
	//TODO: figure out why the following two rows throw an exception I found out that it is tied to the word "into"
	//TODO: why the hell does the word "into" cause an exception to be thrown?
	//ReqTestData.add(new RequirementsRowData("R4.1.0(1)","Functional","PCA Pump Function","Basal flow rate","The basal flow rate, F(basal) is prescribed by a physican and entred into the PCA pump by scanning the presription from the drug container label as it is loaded into the reservoir.","12-15-15","John","low"));
	//ReqTestData.add(new RequirementsRowData("R5.8.0(1)","Functional","Drug Reservoir","Drug Reservoir","The drug reservoir holds liquid pain-killer supplied by the hospital pharmacy and loaded into the PCA pump by the clinician.",null,null,"low"));

	private void populateList()
	{

		ReqTestData.add(new RequirementsRowData("R4.1.0(1)","Functional","PCA Pump Function","Basal flow rate","The basal flow rate, F(basal) is prescribed by a physician and entered the in to PCA pump by scanning the presceription from the drug container label as it is loaded in to the reservoir.","12-15-15","John","low"));
		ReqTestData.add(new RequirementsRowData("R4.1.0(2)","Functional","PCA Pump Function","Basal flow rate","The pump shall be able to deliver basal infusion at flows throughout the basal infusion flow range of Fbasal min = 1 to Fbasal max = 10 ml/hr.","12-17-15","John","high"));
		ReqTestData.add(new RequirementsRowData("R5.1.0(1)","Functional","PCA Pump Interfaces","Sensors","The PCA pump shall measure drug , F(basal) flow within a tolerance of Fmdf tol = 0.1 ml/hr.","12/25/2015","Peikang","low"));
		ReqTestData.add(new RequirementsRowData("R5.4.4(3)","Functional","PCA Pump Interfaces","Alarms"," If alarms are inactivated or paused through the ICE supervisor user interface, they shall be reactivated upon loss of connection to ICE","1/25/2016","Caleb","high"));
		ReqTestData.add(new RequirementsRowData("R5.5.0(1)","Functional","Control Panel","Control Panel","The control panel must display currently-programmed patient data and physician�s prescription.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R5.5.0(9)","Functional","Control Panel","Control Panel","Prescriptions that violate the soft limits of the drug in the drug library shall issue a visible and audible warning requiring a soft limit confirmation by the clinician.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R5.6.0(1)","Functional","Logging","Logging","The PCA pump shall maintain an electronic event log to record each action taken by the pump and each event sensed of its environment.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R5.6.0(8)","Functional","Logging","Logging","A real-time clock must produce timestamps accurate to 10 ms.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R5.7.0(1)","Functional","ICE Interface","ICE Interface","The ICE Interface shall transmit encrypted and authenticated current operating status to the ICE system.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R5.7.0(5)","Functional","ICE Interface","ICE Interface","The PCA pump shall resume prescribed infusion upon receiving an authenticated command through its ICE interface.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R5.8.0(1)","Functional","Drug Reservoir","Drug Reservoir","The drug reservoir holds liquid pain-killer supplied by the hospital pharmacy and loaded in to the PCA pump by the clinician.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R5.8.0(9)","Functional","Drug Reservoir","Drug Reservoir","If the drug volume in the reservoir measures less than Vlra = 1 ml, and an infusion is in progress, allow-reservoir warning100 shall be issued.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R5.9.0(1)","Functional","Drug Library","Drug Library","The drug library102 can be thought of as a lookup table that, given a drug name and a location, provides typical and safe limits of different infusion parameters. The drug library shall be determined by an authorized and authenticated hospital pharmacist",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R5.9.0(6)","Functional","Drug Library","Drug Library","If a drug library�s soft limit is violated by the proposed settings, but not a hard limit, a warning is shown to the clinician by the user interface and a distinctive, irritating sound made and recorded in the Event Log.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R6.1.0(1)","Safety","Safety Architecture","Safety Architercture","The PCA pump shall implement a safety architecture112 that separates normal operation from fault detection and response.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R6.2.0(1)","Safety","Anomaly Detection and Response","Anomaly Detection and Response","When the stop button is pressed, the current pump stroke shall be completed prior to stopping the  pump.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R6.2.0(8)","Safety","Anomaly Detection and Response","Anomaly Detection and Response","A open door alarm shall be triggered when the reservoir door is opened while the pump is not stopped.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R6.3.0(1)","Safety","Power Supply","Power Supply","The PCA pump shall continue to infuse for 10 minutes during interruption of mains electricity supply using battery backup, either continuously or spread over an hour. (Five minutes to recharge per minute using battery.)",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R6.3.0(8)","Safety","Power Supply","Power Supply","Component failure must not harm patient (beyond stopping function).",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R6.3.0(9)","Safety","Power Supply","Sensors","The PCA pump must be electromagnetically compatible according to IEC 60601-1-2 (2001)",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R6.4.0(1)","Safety","Diagnostics and Fail-Stop","Diagnostics and Fail-Stop","The PCA pump shall perform a power-on-self-test (POST) to assure system integrity after being turned on, yet before any infusion begins. Failure of POST shall raise a POST alarm, stop pump",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R6.4.0(6)","Safety","Diagnostics and Fail-Stop","Diagnostics and Fail-Stop","Hardware faults detected, but not masked, shall raise a fault alarm, stop pump, record it in the Fault Log, and display the reason for fault on the user interface.",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R6.4.0(7)","Safety","Diagnostics and Fail-Stop","Diagnostics and Fail-Stop","Hardware faults that prevent operation of the Control Panel shall illuminate a hardware fault indicator  (light-emitting diode).",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R6.5.0(1)","Safety","Tamper-Resistant Door","Tamper-Resistant Door","Because the drugs used for analgesia are often narcotic, requiring Drug Enforcement Agency (DEA)tracking if used in the United States, the drug reservoir and means to change prescriptions",null,null,"high"));
		ReqTestData.add(new RequirementsRowData("R7.1.0(3)","Security","Tamper-Resistant Door","Tamper-Resistant Door","Drug container must have a valid prescription, filled by an authorized pharmacist, for the particular patient to be infused by the PCA pump.",null,null,"low"));
		ReqTestData.add(new RequirementsRowData("R7.3.0(4)","Security","Provisioning","Provisioning","Provisioning shall be a single, unitary block-transfer.",null,null,"high"));
	}
}