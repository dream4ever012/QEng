package qEng;


public class ClassNameTableData {

	public static String [] TableData =
		{
				"'PCA1','The basal flow rate, F(basal) is prescribed by a physican and entred in to the PCA pump by scanning the presription from the drug container label as it is loaded in to the reservoir.','Caleb','4/1/2016','V1.1'",
				"'PCA2','The pump shall be able to deliver basal infusion at flows throughout the basal infusion flow range of Fbasal min = 1 to Fbasal max = 10 ml/hr.','Caleb','4/2/2016','V2.1'",
				"'PCA3','The PCA pump shall measure drug flow within a tolerance of Fmdf tol = 0.1 ml/hr.','Caleb','4/3/2016','V3.1'",
				"'PCA4','If alarms are inactivated or paused through the ICE supervisor user interface, they shall be reactivated upon loss of connection to ICE','Caleb','4/4/2016','V1.2'",
				"'PCA5','The control panel must display currently-programmed patient data and physician�s prescription.','Andrew','4/5/2016','V2.2'",
				"'PCA6','Prescriptions that violate the soft limits of the drug in the drug library shall issue a visible and audible warning requiring a soft limit confirmation by the clinician.','Andrew','4/6/2016','V3.2'",
				"'PCA7','\"The PCA pump shall maintain an electronic event log to record each action taken by the pump and each event sensed of its environment.\"','Andrew','4/7/2016','V1.3'",
				"'PCA8','A real-time clock must produce timestamps accurate to 10 ms.','Andrew','4/8/2016','V2.3'",
				"'PCA9','The ICE Interface shall transmit encrypted and authenticated current operating status to the ICE system.','Peikang','4/9/2016','V3.3'",
				"'PCA10','The PCA pump shall resume prescribed infusion upon receiving an authenticated command through its ICE interface.','Peikang','4/10/2016','V1.4'",
				"'PCA11','\"The drug reservoir holds liquid pain-killer supplied by the hospital pharmacy and loaded in to the PCA pump by the clinician.\"','Peikang','4/11/2016','V2.4'",
				"'PCA12','\"If the drug volume in the reservoir measures less than Vlra = 1 ml, and an infusion is in progress, a low-reservoir warning100 shall be issued.\"','Peikang','4/12/2016','V3.4'",
				"'PCA13','\"The drug library102 can be thought of as a lookup table that, given a drug name and a location, provides typical and safe limits of different infusion parameters. The drug library shall be determined by an authorized and authenticated hospital pharmacist\"','Hotmail','4/13/2016','V1.5'",
				"'PCA14','\"If a drug library�s soft limit is violated by the proposed settings, but not a hard limit, a warning is shown to the clinician by the user interface and a distinctive, irritating sound made and recorded in the Event Log.\"','Hotmail','4/14/2016','V2.5'",
				"'PCA15','\"The PCA pump shall implement a safety architecture112 that separates normal operation from fault detection and response.\"','Hotmail','4/15/2016','V3.5'",
				"'PCA16','\"When the stop button is pressed, the current pump stroke shall be completed prior to stopping the pump.\"','Hotmail','4/16/2016','V1.6'",
				"'PCA17','\"A open door alarm shall be triggered when the reservoir door is opened while the pump is not stopped.\"','Jane','4/17/2016','V2.6'",
				"'PCA18','\"The PCA pump shall continue to infuse for 10 minutes during interruption of mains electricity supply using battery backup, either continuously or spread over an hour. (Five minutes to recharge per minute using battery.)\"','Jane','4/18/2016','V3.6'",
				"'PCA19','Component failure must not harm patient (beyond stopping function).','Jane','4/19/2016','V1.7'",
				"'PCA20','The PCA pump must be electromagnetically compatible according to IEC 60601-1-2 (2001)','Jane','4/20/2016','V2.7'",
				"'PCA21','\"The PCA pump shall perform a power-on-self-test (POST) to assure system integrity after being turned on, yet before any infusion begins. Failure of POST shall raise a POST alarm, stop pump\"','Alex','4/21/2016','V3.7'",
				"'PCA22','\"Hardware faults detected, but not masked, shall raise a fault alarm, stop pump, record it in the Fault Log, and display the reason for fault on the user interface.\"','Alex','4/22/2016','V1.8'",
				"'PCA23','\"Hardware faults that prevent operation of the Control Panel shall illuminate a hardware fault indicator  (light-emitting diode).\"','Alex','4/23/2016','V2.8'",
				"'PCA24','\"Because the drugs used for analgesia are often narcotic, requiring Drug Enforcement Agency (DEA) tracking if used in the United States, the drug reservoir and means to change prescriptions\"','Alex','4/24/2016','V3.8'",
				"'PCA25','\"Drug container must have a valid prescription, filled by an authorized pharmacist, for the particular patient to be infused by the PCA pump.\"','Sugandah','4/25/2016','V1.9'",
				"'PCA26','\"Provisioning shall be a single, unitary block-transfer.\"','Sugandah','4/26/2016','V2.9'"				
		};
}