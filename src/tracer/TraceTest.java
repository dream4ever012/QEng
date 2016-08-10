package tracer;

import java.io.File;

public class TraceTest {
    private static String prefix = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Trace" + File.separator; //"/tmp/";
    public static void main (String[] args) {
        Trace.GRAPHVIZ_SHOW_STEPS = true;
        Trace.setGraphizBaseFileName (prefix + "test-");
        Trace.run (TraceTestExample.class);
        //Trace.setGraphizBaseFileName (prefix + "counter-");
       // Trace.run (algs12.Counter.class);
       // Trace.setGraphizBaseFileName (prefix + "date-");
       // Trace.run (algs12.Date.class);
        
        
    }
}
