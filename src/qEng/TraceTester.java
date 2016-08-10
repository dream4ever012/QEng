package qEng;

import java.io.File;

import tracer.Trace;

public class TraceTester {

	private static String prefix = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Trace" + File.separator; //"/tmp/";

	public static void main(String[] args) {
		new File(prefix).mkdirs();

		Trace.GRAPHVIZ_SHOW_STEPS = true;
		Trace.setGraphizBaseFileName (prefix + "test-");
		Trace.run (mainforTesting.class);
	}

}
