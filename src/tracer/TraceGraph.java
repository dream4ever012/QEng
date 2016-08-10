package tracer;
/**
 * This is an auxiliary classed used by Trace.java.
 * Each method in this class must be a static methods with an empty body.
 * Therefore, when called during normal execution, the method does nothing.
 * During a trace, calls to this class are trapped, causing a the program
 * to be drawn into a file.  A filename prefix can be provided as an optional
 * argument, otherwise a default is used.  The draw method may be called multiple
 * times; each call creates a separate drawing.  A global counter is used as a
 * suffix in the filename.
 *
 * Implementation detail:
 * Rather bizarrely, the draw method cannot be put in any class that includes
 * calls to the JDI, or the class loader will fail to load it while Tracing.
 * Thus, the draw method cannot be put in the Trace.java class, which is why
 * it is here.
 */
public class TraceGraph {
    private TraceGraph () { }
    public static void draw (String filenamePrefix) { /* See Trace.methodEntryEvent */ }
    public static void draw () { /* See Trace.methodEntryEvent */ }
}
