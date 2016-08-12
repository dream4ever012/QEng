package tracer;

//- Copyright (C) 2014  James Riely, DePaul University
//- Copyright (C) 2004  John Hamer, University of Auckland [Graphviz code]
//-
//-   This program is free software; you can redistribute it and/or
//-   modify it under the terms of the GNU General Public License
//-   as published by the Free Software Foundation; either version 2
//-   of the License, or (at your option) any later version.
//-
//-   This program is distributed in the hope that it will be useful,
//-   but WITHOUT ANY WARRANTY; without even the implied warranty of
//-   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//-   GNU General Public License for more details.
//-
//-   You should have received a copy of the GNU General Public License along
//-   with this program; if not, write to the Free Software Foundation, Inc.,
//-   59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.package algs;

//- Event monitoring code based on
//-    http://fivedots.coe.psu.ac.th/~ad/jg/javaArt5/
//-    By Andrew Davison, ad@fivedots.coe.psu.ac.th, March 2009
//-
//- Graphviz code based on LJV
//-    https://www.cs.auckland.ac.nz/~j-hamer/
//-    By John Hamer, <J.Hamer@cs.auckland.ac.nz>, 2003
//-    Copyright (C) 2004  John Hamer, University of Auckland
//
//
//Repaired by Robert Cole, DePaul University.


import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

/**
* Traces the execution of a target program.
*
* Usage: java Trace [OptionalJvmArguments] fullyQualifiedClassName
*
* Starts a new JVM (java virtual machine) running the main program in
* fullyQualifiedClassName, then traces it's behavior.
* The OptionalJvmArguments are passed to this underlying JVM.
*
* Example usages:
*
*   java Trace MyClass
*   java Trace mypackage.MyClass
*   java Trace -cp ".:/pathTo/Library.jar" mypackage.MyClass  // mac/linux
*   java Trace -cp ".;/pathTo/Library.jar" mypackage.MyClass  // windows
*
* Two types of display are support: console and graphviz
* In order to use graphziv, you must install http://www.graphviz.org/
* and ensure that GRAPHVIZ_DOT_COMMAND is set properly below.
*
* You can either draw the state at each step (GRAPHVIZ_SHOW_STEPS==true)
* or you can draw states selectively by calling TraceGraph.draw() from
* the program you are tracing.  See the example in TraceTest.java
*
* @author James Riely, jriely@cs.depaul.edu, August 2014
*
* !!!!!!!!!!!!!!!!!!!!!!!! COMPILATION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*
* This class requires Java 8.
* It also requires the file tools.jar, which comes with the JDK (not the JRE)
*
* On windows, you can find it in C:\Program Files\Java\jdk...\lib\tools.jar
* On mac, look in /Library/Java/JavaVirtualMachines/jdk.../Contents/Home/lib/tools.jar
*
* To put this in your eclipse build path, select your project in the package explorer, then:
*
*  Project > Properties > Java Build Path > Libraries > Add External Library
*/

public class Trace {
  private Trace () { } // noninstantiable class

  // Basic graphviz options 
  public static boolean GRAPHVIZ_RUN_DOT = true; // run "dot" if it is available 
  public static String GRAPHVIZ_DOT_OUTPUT_FORMAT = "png"; // The graphviz format -- see http://www.graphviz.org/doc/info/output.html
  public static void setGraphizBaseFileName (String fileName) { Graphviz.setBaseFileName (fileName); }
  public static void setConsoleFileName (String fileName) { Printer.setFileName (fileName); }
  public static void setConsoleFileName ()                { Printer.setFileName (); }

  // Basic options -- 
  public static boolean GRAPHVIZ_SHOW_STEPS = false; // a new drawing for every step
  public static boolean CONSOLE_SHOW_CLASSES = true; // shows info about class when loaded
  public static boolean CONSOLE_SHOW_CALLS = true; // show method calls and returns
  public static boolean CONSOLE_SHOW_STEPS = true; // show which lines are executed
  public static boolean CONSOLE_SHOW_STEPS_VERBOSE = false; // shows the line of code executed for each step
  public static boolean SHOW_STRINGS_AS_PRIMITIVE = true; // show String as primitive
  public static boolean SHOW_BOXED_PRIMITIVES_AS_PRIMITIVE = true; // show Integer as int, Double as double, etc.

  public static void run (Class<?> c) { TraceInternal.internalRun(c.getCanonicalName ());  }
  public static void run (String s)   { TraceInternal.internalRun(s); }
  public static void main (String[] args) {
      if (args.length == 0) {
          System.err.println ("Usage: java " + Trace.class.getCanonicalName () + " [OptionalJvmArguments] fullyQualifiedClassName");
      } else {
          TraceInternal.internalMain(args);
      }
  }
}
//------------------------------------------------------------------------
//        _______.___________.  ______   .______      __     __
//       /       |           | /  __  \  |   _  \    |  |   |  |
//      |   (----`---|  |----`|  |  |  | |  |_)  |   |  |   |  |
//       \   \       |  |     |  |  |  | |   ___/    |  |   |  |
//   .----)   |      |  |     |  `--'  | |  |        |__|   |__|
//   |_______/       |__|      \______/  | _|        (__)   (__)
//
//        _______.  ______     ___      .______     ____    ____
//       /       | /      |   /   \     |   _  \    \   \  /   /
//      |   (----`|  ,----'  /  ^  \    |  |_)  |    \   \/   /
//       \   \    |  |      /  /_\  \   |      /      \_    _/
//   .----)   |   |  `----./  _____  \  |  |\  \----.   |  |
//   |_______/     \______/__/     \__\ | _| `._____|   |__|
//
//   .___________. __    __   __  .__   __.   _______      _______.
//   |           ||  |  |  | |  | |  \ |  |  /  _____|    /       |
//   `---|  |----`|  |__|  | |  | |   \|  | |  |  __     |   (----`
//       |  |     |   __   | |  | |  . `  | |  | |_ |     \   \
//       |  |     |  |  |  | |  | |  |\   | |  |__| | .----)   |
//       |__|     |__|  |__| |__| |__| \__|  \______| |_______/
//
//   .______    _______  __        ______   ____    __    ____    __
//   |   _  \  |   ____||  |      /  __  \  \   \  /  \  /   /   |  |
//   |  |_)  | |  |__   |  |     |  |  |  |  \   \/    \/   /    |  |
//   |   _  <  |   __|  |  |     |  |  |  |   \            /     |  |
//   |  |_)  | |  |____ |  `----.|  `--'  |    \    /\    /      |__|
//   |______/  |_______||_______| \______/      \__/  \__/       (__)
//
//------------------------------------------------------------------------
//This program uses all sorts of crazy java foo.
//You should not have to read anything else in this file.
/*private static*/ class TraceInternal {
  //EXCLUDE_PATTERNS must include "java.*" and "sun.*"
  public static String[] EXCLUDE_PATTERNS = { "java.*", "sun.*", "stdlib.*", "javax.*", "com.*", "Jama.*", "qs.*" }; // exclude classes that match

  // style!
  public static String GRAPHVIZ_ARRAY_BOX_ATTRIBUTES = "shape=record,color=blue";
  public static String GRAPHVIZ_ARRAY_ARROW_ATTRIBUTES = "fontsize=12,color=blue";
  public static String GRAPHVIZ_FRAME_BOX_ATTRIBUTES = "shape=record,color=red";
  public static String GRAPHVIZ_FRAME_ARROW_ATTRIBUTES = "fontsize=12,color=red";
  public static String GRAPHVIZ_FRAME_RETURN_ATTRIBUTES = "color=red";
  public static String GRAPHVIZ_FRAME_FRAME_ATTRIBUTES = "color=red,style=dashed";
  public static String GRAPHVIZ_OBJECT_BOX_ATTRIBUTES = "shape=record,color=purple";
  public static String GRAPHVIZ_OBJECT_ARROW_ATTRIBUTES = "fontsize=12,color=purple";
  public static String GRAPHVIZ_STATIC_CLASS_BOX_ATTRIBUTES = "shape=record,color=orange";
  public static String GRAPHVIZ_STATIC_CLASS_ARROW_ATTRIBUTES = "fontsize=12,color=orange";

  // Graphiz execution
  // If no file in GRAPHVIZ_POSSIBLE_DOT_LOCATIONS is executable, then the dot file is left
  public static String[] GRAPHVIZ_POSSIBLE_DOT_LOCATIONS;
  static {
      String os = System.getProperty("os.name").toLowerCase ();
      if (os.startsWith("win")) {
          GRAPHVIZ_POSSIBLE_DOT_LOCATIONS = new String[] {
                  "c:/Program Files (x86)/Graphviz2.38/bin/dot.exe", 
                  System.getProperty("user.dir") + "/lib/graphviz-windows/bin/dot.exe"
          };
      } else if (os.startsWith("mac")) {
          GRAPHVIZ_POSSIBLE_DOT_LOCATIONS = new String[] {
                  "/usr/local/bin/dot", 
                  "/usr/bin/dot", 
                  System.getProperty("user.dir") + "/lib/graphviz-mac/bin/dot"
          };
      } else {
          GRAPHVIZ_POSSIBLE_DOT_LOCATIONS = new String[] {
                  "/usr/local/bin/dot", 
                  "/usr/bin/dot"
          };
      }
  }
  public static boolean GRAPHVIZ_REMOVE_DOT_FILES = true; // Removes dot files when GRAPHVIZ_DOT_COMMAND != null

  // Obscure options
  // arguments for the underlying JVM -- if there is a bin directory, then use it
  private static String BIN_CLASSPATH = "bin" + System.getProperty("path.separator") + ".";
  public static String[] PREFIX_ARGS_FOR_VM = Files.exists(FileSystems.getDefault().getPath("bin")) ? (new String[] { "-cp", BIN_CLASSPATH }) : new String[] {};
  public static boolean DEBUG_VARIABLE_PRINTING = false;
  // Note: if SHOW_PACKAGE_IN_CLASS_NAME==true, then SHOW_OUTER_CLASS_IN_CLASS_NAME is ignored (taken to be true)
  public static boolean SHOW_PACKAGE_IN_CLASS_NAME = false; // shows fully qualified class names
  public static boolean SHOW_OUTER_CLASS_IN_CLASS_NAME = false; // shows outer classes in class names
  public static boolean CONSOLE_SHOW_VARIABLES = true; // shows the variables changed at each step
  public static boolean CONSOLE_SHOW_TYPE_IN_OBJECT_NAME = false; // shows the object type in addition to its id
  public static int CONSOLE_MAX_FIELDS = 8; // Maximum number of displayed fields when printing an object
  public static int CONSOLE_MAX_ARRAY_ELEMENTS_PRIMITIVE = 15; // Max displayed array elements for primitive types
  public static int CONSOLE_MAX_ARRAY_ELEMENTS_OBJECT= 8; // Max displayed array elements for object types
  public static boolean CONSOLE_SHOW_THREADS = true; // show thread events
  public static boolean CONSOLE_SHOW_NESTED_ARRAY_IDS = false; // show ids inside multidimensional arrays
  public static boolean CONSOLE_SHOW_SYNTHETIC_FIELDS = true; // show methods introduced by the compiler
  public static boolean CONSOLE_SHOW_SYNTHETIC_METHODS = false; // show methods introduced by the compiler
  public static boolean GRAPHVIZ_SHOW_FIELD_NAMES_IN_LABELS = true; // show field name in the label of an object.
  public static boolean GRAPHVIZ_SHOW_OBJECT_IDS = false; // show object ids
  public static boolean GRAPHVIZ_SHOW_BOXED_PRIMITIVES_SIMPLY = true; // leaves the labels off fields in Integer, Double, etc.
  public static boolean GRAPHVIZ_SHOW_BOXED_PRIMITIVES_VERY_SIMPLY = true; // shows Integer, Double, etc as simple boxes
  public static String[] GRAPHVIZ_IGNORED_FIELDS = { }; // Do not display any fields with this name.
  public static void graphvizSetObjectAttribute (Class<?> cz, String attrib) { Graphviz.objectAttributeMap.put (cz.getName (), attrib); } // Set the DOT attributes for objects of this class.
  public static void graphvizSetStaticClassAttribute (Class<?> cz, String attrib) { Graphviz.staticClassAttributeMap.put (cz.getName (), attrib); } // Set the DOT attributes for objects of this class.
  public static void graphvizSetFrameAttribute (Class<?> cz, String attrib) { Graphviz.frameAttributeMap.put (cz.getName (), attrib); } // Set the DOT attributes for frames of this class.
  public static void graphvizSetFieldAttribute (String field, String attrib) { Graphviz.fieldAttributeMap.put (field, attrib); } // Set the DOT attributes for all fields with this name.
  public static String BAD_ERROR_MESSAGE = "\n!!!! This shouldn't happen! \n!!!! Please contact your instructor or the author of " + Trace.class.getCanonicalName ();

  private TraceInternal () { }; // noninstantiable class

  /*
   * This code is based on the Trace.java example included in the demo/jpda/examples.jar
   * file in the JDK.
   *
   * For more information on JPDA and JDI, see:
   * http://docs.oracle.com/javase/8/docs/technotes/guides/jpda/trace.html
   * http://docs.oracle.com/javase/8/docs/jdk/api/jpda/jdi/index.html
   * http://forums.sun.com/forum.jspa?forumID=543
   *
   * Changes made by Riely:
   * - works with packages other than default
   * - prints values of variables
   *     Objects values are printed as "@uniqueId"
   *     Arrays include the values in the array, up to
   * - works for arrays, but only when local variables
   * - options for more or less detail
   * - indenting to show position in call stack
   * - added methods to draw the state of the system using graphviz
   *
   * Known bugs/limitations:
   * - Works for local arrays, including updates to fields of "this", but will not print
   *   changes through other object references, such as
   *       yourObject.publicArray[0] = 22
   *   As long as array fields are private (as they should be), it should be okay.
   * - Space leak: copies of array references are kept forever.  See "registerArray".
   * - Not debugged for multithreaded code.
   * - Slow.  Only good for short programs.
   * - This is a hodgepodge of code from various sources, not well debugged, not super clean.
   */
  // ---------------------- Launch the JVM  ----------------------------------

  public static void internalMain (String[] args) {
      String[] allArgs = Arrays.copyOf (PREFIX_ARGS_FOR_VM, PREFIX_ARGS_FOR_VM.length + args.length);
      System.arraycopy (args, 0, allArgs, PREFIX_ARGS_FOR_VM.length, args.length);

      VirtualMachine vm = launchConnect (allArgs);
      monitorJVM (vm);
  }
  public static void internalRun (String claz) {
      String[] allArgs = Arrays.copyOf (PREFIX_ARGS_FOR_VM, PREFIX_ARGS_FOR_VM.length + 1);
      allArgs[allArgs.length-1] = claz;

      VirtualMachine vm = launchConnect (allArgs);
      monitorJVM (vm);
  }

  // Set up a launching connection to the JVM
  private static VirtualMachine launchConnect (String[] args) {
      VirtualMachine vm = null;
      LaunchingConnector conn = getCommandLineConnector ();
      Map<String, Connector.Argument> connArgs = setMainArgs (conn, args);

      try {
          vm = conn.launch (connArgs); // launch the JVM and connect to it
      } catch (IOException e) {
          throw new Error ("\n!!!! Unable to launch JVM: " + e);
      } catch (IllegalConnectorArgumentsException e) {
          throw new Error ("\n!!!! Internal error: " + e);
      } catch (VMStartException e) {
          throw new Error ("\n!!!! JVM failed to start: " + e.getMessage ());
      }

      return vm;
  }

  // find a command line launch connector
  private static LaunchingConnector getCommandLineConnector () {
      List<Connector> conns = Bootstrap.virtualMachineManager ().allConnectors ();

      for (Connector conn : conns) {
          if (conn.name ().equals ("com.sun.jdi.CommandLineLaunch")) return (LaunchingConnector) conn;
      }
      throw new Error ("\n!!!! No launching connector found");
  }

  // make the tracer's input arguments the program's main() arguments
  private static Map<String, Connector.Argument> setMainArgs (LaunchingConnector conn, String[] args) {
      // get the connector argument for the program's main() method
      Map<String, Connector.Argument> connArgs = conn.defaultArguments ();
      Connector.Argument mArgs = connArgs.get ("main");
      if (mArgs == null) throw new Error ("\n!!!! Bad launching connector");

      // concatenate all the tracer's input arguments into a single string
      StringBuffer sb = new StringBuffer ();
      for (int i = 0; i < args.length; i++)
          sb.append (args[i] + " ");

      mArgs.setValue (sb.toString ()); // assign input args to application's main()
      return connArgs;
  }

  // monitor the JVM running the application
  private static void monitorJVM (VirtualMachine vm) {
      // start JDI event handler which displays trace info
      JDIEventMonitor watcher = new JDIEventMonitor (vm);
      watcher.start ();

      // redirect VM's output and error streams to the system output and error streams
      Process process = vm.process ();
      Thread errRedirect = new StreamRedirecter ("error reader", process.getErrorStream (), System.err);
      Thread outRedirect = new StreamRedirecter ("output reader", process.getInputStream (), System.out);
      errRedirect.start ();
      outRedirect.start ();

      vm.resume (); // start the application

      try {
          watcher.join (); // Wait. Shutdown begins when the JDI watcher terminates
          errRedirect.join (); // make sure all the stream outputs have been forwarded before we exit
          outRedirect.join ();
      } catch (InterruptedException e) {}
  }
}

/**
* StreamRedirecter is a thread which copies it's input to it's output and
* terminates when it completes.
*
* @author Robert Field, September 2005
* @author Andrew Davison, March 2009, ad@fivedots.coe.psu.ac.th
*/
/*private static*/ class StreamRedirecter extends Thread {
  private static final int BUFFER_SIZE = 2048;
  private final Reader in;
  private final Writer out;

  public StreamRedirecter (String name, InputStream in, OutputStream out) {
      super (name);
      this.in = new InputStreamReader (in); // stream to copy from
      this.out = new OutputStreamWriter (out); // stream to copy to
      setPriority (Thread.MAX_PRIORITY - 1);
  }

  // copy BUFFER_SIZE chars at a time
  public void run () {
      try {
          char[] cbuf = new char[BUFFER_SIZE];
          int count;
          while ((count = in.read (cbuf, 0, BUFFER_SIZE)) >= 0)
              out.write (cbuf, 0, count);
          out.flush ();
      } catch (IOException e) {
          System.err.println ("StreamRedirecter: " + e);
      }
  }

}

/**
* Monitor incoming JDI events for a program running in the JVM and print out
* trace/debugging information.
*
* This is a simplified version of EventThread.java from the Trace.java example
* in the demo/jpda/examples.jar file in the JDK.
*
* Andrew Davison: The main addition is the use of the ShowCodes and ShowLines classes to list
* the line being currently executed.
*
* James Riely: See comments in class Trace.
*
* @author Robert Field and Minoru Terada, September 2005
* @author Iman_S, June 2008
* @author Andrew Davison, ad@fivedots.coe.psu.ac.th, March 2009
* @author James Riely, jriely@cs.depaul.edu, August 2014
*/
/*private static*/ class JDIEventMonitor extends Thread {
  // exclude events generated for these classes
  private final VirtualMachine vm; // the JVM
  private boolean connected = true; // connected to VM?
  private boolean vmDied; // has VM death occurred?
  private final JDIEventHandler printer = new Printer();

  public JDIEventMonitor (VirtualMachine jvm) {
      super ("JDIEventMonitor");
      vm = jvm;
      setEventRequests ();
  }

  // Create and enable the event requests for the events we want to monitor in
  // the running program.
  private void setEventRequests () {
      EventRequestManager mgr = vm.eventRequestManager ();

      MethodEntryRequest menr = mgr.createMethodEntryRequest (); // report method entries
      for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; ++i)
          menr.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
      menr.setSuspendPolicy (EventRequest.SUSPEND_EVENT_THREAD);
      menr.enable ();

      MethodExitRequest mexr = mgr.createMethodExitRequest (); // report method exits
      for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; ++i)
          mexr.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
      mexr.setSuspendPolicy (EventRequest.SUSPEND_EVENT_THREAD);
      mexr.enable ();

      ClassPrepareRequest cpr = mgr.createClassPrepareRequest (); // report class loads
      for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; ++i)
          cpr.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
      // cpr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
      cpr.enable ();

      ClassUnloadRequest cur = mgr.createClassUnloadRequest (); // report class unloads
      for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; ++i)
          cur.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
      // cur.setSuspendPolicy(EventRequest.SUSPEND_ALL);
      cur.enable ();

      ThreadStartRequest tsr = mgr.createThreadStartRequest (); // report thread starts
      tsr.enable ();

      ThreadDeathRequest tdr = mgr.createThreadDeathRequest (); // report thread deaths
      tdr.enable ();

  }

  // process JDI events as they arrive on the event queue
  public void run () {
      EventQueue queue = vm.eventQueue ();
      while (connected) {
          try {
              EventSet eventSet = queue.remove ();
             EventIterator eI = eventSet.eventIterator();
              //for (Event event : eventSet)
                  //handleEvent (event);
             while(eI.hasNext()){
            	 handleEvent(eI.nextEvent());
             }
             
              eventSet.resume ();
          } catch (InterruptedException e) {
              // Ignore
          } catch (VMDisconnectedException discExc) {
              handleDisconnectedException ();
              break;
          }
      }
  }

  // process a JDI event
  private void handleEvent (Event event) {
      // step event -- a line of code is about to be executed
      if (event instanceof StepEvent) { stepEvent ((StepEvent) event); return; }

      // modified field event  -- a field is about to be changed
      if (event instanceof ModificationWatchpointEvent) { modificationWatchpointEvent ((ModificationWatchpointEvent) event); return; }

      // method events
      if (event instanceof MethodEntryEvent) { methodEntryEvent ((MethodEntryEvent) event); return; }
      if (event instanceof MethodExitEvent) { methodExitEvent ((MethodExitEvent) event); return; }

      // class events
      if (event instanceof ClassPrepareEvent) { classPrepareEvent ((ClassPrepareEvent) event); return; }
      if (event instanceof ClassUnloadEvent) { classUnloadEvent ((ClassUnloadEvent) event); return; }

      // thread events
      if (event instanceof ThreadStartEvent) { threadStartEvent ((ThreadStartEvent) event); return; }
      if (event instanceof ThreadDeathEvent) { threadDeathEvent ((ThreadDeathEvent) event); return; }

      // VM events
      if (event instanceof VMStartEvent) { vmStartEvent ((VMStartEvent) event); return; }
      if (event instanceof VMDeathEvent) { vmDeathEvent ((VMDeathEvent) event); return; }
      if (event instanceof VMDisconnectEvent) { vmDisconnectEvent ((VMDisconnectEvent) event); return; }

      throw new Error ("\n!!!! Unexpected event type");
  }

  // A VMDisconnectedException has occurred while dealing with another event.
  // Flush the event queue, dealing only with exit events (VMDeath,
  // VMDisconnect) so that things terminate correctly.
  private synchronized void handleDisconnectedException () {
      EventQueue queue = vm.eventQueue ();
      while (connected) {
          try {
              EventSet eventSet = queue.remove ();
              EventIterator eI = eventSet.eventIterator();
              /*for (Event event : eventSet) {
                  if (event instanceof VMDeathEvent) vmDeathEvent ((VMDeathEvent) event);
                  else if (event instanceof VMDisconnectEvent) vmDisconnectEvent ((VMDisconnectEvent) event);
              }*/
              Event event;
              while(eI.hasNext()){
            	  event = eI.nextEvent();
            	  if(event instanceof VMDeathEvent) vmDeathEvent ((VMDeathEvent) event);
            	  else if (event instanceof VMDisconnectEvent) vmDisconnectEvent ((VMDisconnectEvent) event);
              }
              
              eventSet.resume (); // resume the VM
          } catch (InterruptedException e) {
              // ignore
          } catch (VMDisconnectedException e) {
              // ignore
          }
      }
  }

  // ---------------------- VM event handling ----------------------------------

  // Notification of initialization of a target VM. This event is received
  // before the main thread is started and before any application code has
  // been executed.
  private void vmStartEvent (VMStartEvent event) {
      vmDied = false;
      printer.vmStartEvent (event);
  }

  // Notification of VM termination
  private void vmDeathEvent (VMDeathEvent event) {
      vmDied = true;
      printer.vmDeathEvent (event);
  }

  // Notification of disconnection from the VM, either through normal
  // termination or because of an exception/error.
  private void vmDisconnectEvent (VMDisconnectEvent event) {
      connected = false;
      if (!vmDied) printer.vmDisconnectEvent (event);
  }

  // -------------------- class event handling  ---------------

  // a new class has been loaded
  private void classPrepareEvent (ClassPrepareEvent event) {
      ReferenceType ref = event.referenceType ();

      List<Field> fields = ref.fields ();

      // register field modification events
      EventRequestManager mgr = vm.eventRequestManager ();
      for (Field field : fields) {
          ModificationWatchpointRequest req = mgr.createModificationWatchpointRequest (field);
          for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; i++)
              req.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
          req.setSuspendPolicy (EventRequest.SUSPEND_NONE);
          req.enable ();
      }
      printer.classPrepareEvent(event);

  }
  // a class has been unloaded
  private void classUnloadEvent (ClassUnloadEvent event) {
      if (!vmDied) printer.classUnloadEvent (event);
  }

  // -------------------- thread event handling  ---------------

  // a new thread has started running -- switch on single stepping
  private void threadStartEvent (ThreadStartEvent event) {
      ThreadReference thr = event.thread ();
      if (Format.ignoreThread (thr))
          return;
      EventRequestManager mgr = vm.eventRequestManager ();

      StepRequest sr = mgr.createStepRequest (thr, StepRequest.STEP_LINE, StepRequest.STEP_INTO);
      sr.setSuspendPolicy (EventRequest.SUSPEND_EVENT_THREAD);

      for (int i = 0; i < TraceInternal.EXCLUDE_PATTERNS.length; ++i)
          sr.addClassExclusionFilter (TraceInternal.EXCLUDE_PATTERNS[i]);
      sr.enable ();
      printer.threadStartEvent(event);
  }

  // the thread is about to terminate
  private void threadDeathEvent (ThreadDeathEvent event) {
      ThreadReference thr = event.thread ();
      if (Format.ignoreThread (thr))
          return;
      printer.threadDeathEvent(event);
  }

  // -------------------- method event handling  ---------------

  // entered a method but no code executed yet
  private void methodEntryEvent (MethodEntryEvent event) {
      printer.methodEntryEvent (event);
  }

  // all code in the method has been executed, and we are about to return
  private void methodExitEvent (MethodExitEvent event) {
      printer.methodExitEvent (event);
  }

  // Print the line that's about to be executed.
  private void stepEvent (StepEvent event) {
      printer.stepEvent (event);
  }

  // ---------------------- modified field event handling ----------------------------------

  private void modificationWatchpointEvent (ModificationWatchpointEvent event) {
      printer.modificationWatchpointEvent (event);
  }
}

/**
* Printer for events.  Prints and updates the ValueMap.
* Handles Graphviz drawing requests.
* @author James Riely, jriely@cs.depaul.edu, August 2014
*/
/*private static*/ interface IndentPrinter { public void println(ThreadReference  thr, String string); }
/*private static*/ interface JDIEventHandler {
  /** Notification of target VM termination. */
  public void vmDeathEvent (VMDeathEvent event);
  /** Notification of disconnection from target VM. */
  public void vmDisconnectEvent (VMDisconnectEvent event);
  /** Notification of initialization of a target VM. */
  public void vmStartEvent (VMStartEvent event);
  /** Notification of a new running thread in the target VM. */
  public void threadStartEvent (ThreadStartEvent event);
  /** Notification of a completed thread in the target VM. */
  public void threadDeathEvent (ThreadDeathEvent event);
  /** Notification of a class prepare in the target VM. */
  public void classPrepareEvent (ClassPrepareEvent event);
  /** Notification of a class unload in the target VM. */
  public void classUnloadEvent (ClassUnloadEvent event);
  /** Notification of a field access in the target VM. */
  //public void accessWatchpointEvent (AccessWatchpointEvent event);
  /** Notification of a field modification in the target VM. */
  public void modificationWatchpointEvent (ModificationWatchpointEvent event);
  /** Notification of a method invocation in the target VM. */
  public void methodEntryEvent (MethodEntryEvent event);
  /** Notification of a method return in the target VM. */
  public void methodExitEvent (MethodExitEvent event);
  /** Notification of an exception in the target VM. */
  //public void exceptionEvent (ExceptionEvent event);
  /** Notification of step completion in the target VM. */
  public void stepEvent (StepEvent event);
  /** Notification of a breakpoint in the target VM. */
  //public void breakpointEvent (BreakpointEvent event);
  /** Notification that a thread in the target VM is attempting to enter a monitor that is already acquired by another thread. */
  //public void monitorContendedEnterEvent (MonitorContendedEnterEvent event);
  /** Notification that a thread in the target VM is entering a monitor after waiting for it to be released by another thread. */
  //public void monitorContendedEnteredEvent (MonitorContendedEnteredEvent event);
  /** Notification that a thread in the target VM is about to wait on a monitor object. */
  //public void monitorWaitEvent (MonitorWaitEvent event);
  /** Notification that a thread in the target VM has finished waiting on an monitor object. */
  //public void monitorWaitedEvent (MonitorWaitedEvent event);
}
/*private static*/ class Printer implements IndentPrinter, JDIEventHandler {
  private final Set<ReferenceType> staticClasses = new HashSet<>();
  private final Map<ThreadReference, Value> returnValues = new HashMap<>();
  private final ValueMap values = new ValueMap();
  private final CodeMap codeMap = new CodeMap ();
  private final InsideIgnoredMethodMap boolMap = new InsideIgnoredMethodMap ();

  public void vmStartEvent (VMStartEvent event) {
      if (TraceInternal.CONSOLE_SHOW_THREADS) println ("|||| VM Started");
  }
  public void vmDeathEvent (VMDeathEvent event) {
      if (TraceInternal.CONSOLE_SHOW_THREADS) println ("|||| VM Stopped");
  }
  public void vmDisconnectEvent (VMDisconnectEvent event) {
      if (TraceInternal.CONSOLE_SHOW_THREADS) println ("|||| VM Disconnected application");
  }
  public void threadStartEvent (ThreadStartEvent event) {
      ThreadReference thr = event.thread ();
      values.stackCreate(thr);
      boolMap.addThread (thr);
      if (TraceInternal.CONSOLE_SHOW_THREADS) println ("|||| thread started: " + thr.name ());
  }
  public void threadDeathEvent (ThreadDeathEvent event) {
      ThreadReference thr = event.thread ();
      values.stackDestroy(thr);
      boolMap.removeThread (thr);
      if (TraceInternal.CONSOLE_SHOW_THREADS) println ("|||| thread stopped: " + thr.name ());
  }
  public void classPrepareEvent (ClassPrepareEvent event) {

      ReferenceType ref = event.referenceType ();

      List<Field> fields = ref.fields ();
      List<Method> methods = ref.methods ();

      String fileName;
      try {
    	  //TODO: changed added Cast (String)
          fileName = ref.sourcePaths (null).get (0); // get filename of the class
          codeMap.addFile (fileName);
      } catch (AbsentInformationException e) {
          fileName = "??";
      }

      boolean hasConstructors = false;
      boolean hasObjectMethods = false;
      boolean hasClassMethods = false;
      boolean hasClassFields = false;
      boolean hasObjectFields = false;
      for (Method m : methods) {
          if (Format.isConstructor (m))  hasConstructors = true;
          if (Format.isObjectMethod (m)) hasObjectMethods = true;
          if (Format.isClassMethod (m))  hasClassMethods = true;
      }
      for (Field f: fields) {
          if (Format.isStaticField (f)) hasClassFields = true;
          if (Format.isObjectField (f)) hasObjectFields = true;
      }

      if (hasClassFields) {
          staticClasses.add (ref);
      }
      if (Trace.CONSOLE_SHOW_CLASSES) {
          println ("|||| loaded class: " + ref.name () + " from " + fileName);
          if (hasClassFields) {
              println ("||||  class fields: ");
              for (Field f : fields)
                  if (Format.isStaticField (f))
                      println ("||||    " + Format.fieldToString(f));
          }
          if (hasClassMethods) {
              println ("||||  class methods: ");
              for (Method m : methods)
                  if (Format.isClassMethod (m))
                      println ("||||    " + Format.methodToString(m, false));
          }
          if (hasConstructors) {
              println ("||||  constructors: ");
              for (Method m : methods)
                  if (Format.isConstructor (m))
                      println ("||||    " + Format.methodToString(m, false));
          }
          if (hasObjectFields) {
              println ("||||  object fields: ");
              for (Field f : fields)
                  if (Format.isObjectField (f))
                      println ("||||    " + Format.fieldToString(f));
          }
          if (hasObjectMethods) {
              println ("||||  object methods: ");
              for (Method m : methods)
                  if (Format.isObjectMethod (m))
                      println ("||||    " + Format.methodToString(m, false));
          }
      }
  }
  public void classUnloadEvent (ClassUnloadEvent event) {
      if (Trace.CONSOLE_SHOW_CLASSES) println ("|||| unloaded class: " + event.className ());
  }
  public void methodEntryEvent (MethodEntryEvent event) {
      Method meth = event.method ();
      ThreadReference thr = event.thread ();
      String calledMethodClassname = meth.declaringType ().name ();
      String specialClassname = TraceGraph.class.getCanonicalName ();
      //System.err.println (calledMethodClassname);
      if (Format.matchesExcludePrefix (calledMethodClassname))
          return;
      if (!specialClassname.equals(calledMethodClassname)) {
          StackFrame currFrame = Format.getFrame (meth, thr);
          if (Trace.CONSOLE_SHOW_STEPS || Trace.CONSOLE_SHOW_STEPS_VERBOSE) {
              values.stackPushFrame (currFrame, thr);
          }
          if (Trace.CONSOLE_SHOW_CALLS) {
              println (thr, ">>>> " + Format.methodToString (meth, true));
              printLocals (currFrame, thr);
          }
      } else {
          boolMap.enteringIgnoredMethod (thr);
          //System.err.println (canonicalName + ":" + traceGraphName + ":" + meth.name ());
          if (!Trace.GRAPHVIZ_SHOW_STEPS) {
              drawGraph(thr, meth);
              printDrawEvent(thr, Graphviz.peekFileName ());
          }
      }
  }
  public void methodExitEvent (MethodExitEvent event) {
	//  MethodExitEvent e = event;
      ThreadReference thr = event.thread ();
      Method meth = event.method ();
      String calledMethodClassname = meth.declaringType ().name ();
      if (Format.matchesExcludePrefix (calledMethodClassname))
          return;
      if (boolMap.leavingIgnoredMethod (thr))
          return;
      if (Trace.CONSOLE_SHOW_CALLS) {
          Type returnType;
          try { returnType = meth.returnType (); } catch (ClassNotLoadedException f) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
          //TODO: changed below line to above line.
         // try { returnType = meth.returnType (); } catch (ClassNotLoadedException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
          if (returnType instanceof VoidType) {
              println (thr, "<<<< " + Format.methodToString (meth, true));
          } else {
              println (thr, "<<<< " + Format.methodToString (meth, true) + " : " + Format.valueToString (event.returnValue ()));
          }
      }
      if (Trace.CONSOLE_SHOW_STEPS || Trace.CONSOLE_SHOW_STEPS_VERBOSE)
          values.stackPopFrame (thr);
      StackFrame currFrame = Format.getFrame (meth, thr);
      if (meth.isConstructor ()) {
          returnValues.put (thr, currFrame.thisObject ());
      } else {
          returnValues.put (thr, event.returnValue ());
      }
  }
  public void stepEvent (StepEvent event) {
      ThreadReference thr = event.thread ();
      if (boolMap.insideIgnoredMethod(thr))
          return;
      Location loc = event.location ();
      String fileName;
      try { fileName = loc.sourcePath (); } catch (AbsentInformationException e) { return; }
      if (Trace.CONSOLE_SHOW_STEPS || Trace.CONSOLE_SHOW_STEPS_VERBOSE) {
          values.stackUpdateFrame (event.location ().method (), thr, this);
          int lineNum = loc.lineNumber ();
          if (Trace.CONSOLE_SHOW_STEPS_VERBOSE) {
              println (thr, Format.shortenFileName (fileName) + ":" + lineNum + codeMap.show (fileName, lineNum));
          } else if (Trace.CONSOLE_SHOW_STEPS) {
              printLineNum (thr, lineNum);
          }
      }
      if (Trace.GRAPHVIZ_SHOW_STEPS) {
          try {
              Value returnVal = returnValues.get (thr);
              Graphviz.drawFrames (0, returnVal, thr.frames (), staticClasses);
              printDrawEvent(thr, Graphviz.peekFileName ());
              returnValues.put (thr, null);
          } catch (IncompatibleThreadStateException e) {
              throw new Error (TraceInternal.BAD_ERROR_MESSAGE);
          }
      }

  }
  public void modificationWatchpointEvent (ModificationWatchpointEvent event) {
      ThreadReference thr = event.thread ();
      if (boolMap.insideIgnoredMethod(thr))
          return;
      if (!Trace.CONSOLE_SHOW_STEPS && !Trace.CONSOLE_SHOW_STEPS_VERBOSE)
          return;
      Field f = event.field ();
      Value value = event.valueToBe (); // value that _will_ be assigned
      String debug = TraceInternal.DEBUG_VARIABLE_PRINTING ? "#5" : "";
      Type type;
      try { type = f.type (); } catch (ClassNotLoadedException e) {
          type = null; // waiting for class to load
      }

      if (type instanceof ArrayType)
          return; // array types are handled separately -- this avoids redundant printing
      ObjectReference objRef = event.object ();
      if (objRef == null) {
          println (thr, "  " + debug + "> " + Format.shortenFullyQualifiedName (f.declaringType ().name ()) + "." + f.name () + " = " + Format.valueToString (value));
      } else {
          // changes to array references are printed by updateFrame
          if (Format.tooManyFields (objRef)) {
              println (thr, "  " + debug + "> " + Format.objectToStringShort (objRef) + "." + f.name () + " = " + Format.valueToString (value));
          } else {
              println (thr, "  " + debug + "> this = " + Format.objectToStringLong (objRef));
          }
      }

  }

  // ---------------------- Graphziv startup code ----------------------------------

  private void drawGraph (ThreadReference thr, Method meth) {
      List<StackFrame> frames;
      try { frames = thr.frames (); } catch (IncompatibleThreadStateException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
      setDrawPrefixFromParameter (Format.getFrame (meth, thr), meth);
      Graphviz.drawFrames (1, null, frames, staticClasses);
  }
  private void setDrawPrefixFromParameter (StackFrame currFrame, Method meth) {
      String prefix = null;
      List<LocalVariable> locals;
      try { locals = currFrame.visibleVariables (); } catch (AbsentInformationException e) { return; }
      if (locals.size () >= 1) {
          Value v = currFrame.getValue (locals.get (0));
          if (!(v instanceof StringReference))
              throw new Error ("\n!!!! " + meth.name () + " must have at most a single parameter." +
                      "\n!!!! The parameter must be of type String");
          prefix = ((StringReference) v).value ();
          if (prefix != null) {
              Graphviz.setBaseFileName (prefix);
          }
      }
  }

  // ---------------------- print locals ----------------------------------

  private void printLocals (StackFrame currFrame, ThreadReference thr) {
      List<LocalVariable> locals;
      try { locals = currFrame.visibleVariables (); } catch (AbsentInformationException e) { return; }
      String debug = TraceInternal.DEBUG_VARIABLE_PRINTING ? "#3" : "";

      ObjectReference objRef = currFrame.thisObject (); // get 'this' object
      if (objRef != null) {
          if (Format.tooManyFields (objRef)) {
              println (thr, "  " + debug + "this: " + Format.objectToStringShort (objRef));
              ReferenceType type = objRef.referenceType (); // get type (class) of object
              List<Field> fields; // use allFields() to include inherited fields
              try { fields = type.fields (); } catch (ClassNotPreparedException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }

              //println (thr, "  fields: ");
              for (Field f : fields) {
                  if (!Format.isObjectField (f))
                      continue;
                  println (thr, "  " + debug + "| " + Format.objectToStringShort (objRef) + "." + f.name () + " = " + Format.valueToString (objRef.getValue (f)));
              }
              if (locals.size () > 0) println (thr, "  locals: ");
          } else {
              println (thr, "  " + debug + "| this = " + Format.objectToStringLong (objRef));
          }
      }
      for (LocalVariable l : locals)
          println (thr, "  " + debug + "| " + l.name () + " = " + Format.valueToString(currFrame.getValue (l)));
  }

  // ---------------------- indented printing ----------------------------------

  private boolean atNewLine = true;
  private static PrintStream out = System.out;
  public static void setFileName (String s) {
      try { 
          Printer.out = new PrintStream (s); 
      } catch (FileNotFoundException e) { 
          System.err.println ("Attempting setFileName \"" + s + "\"");
          System.err.println ("Cannot open file \"" + s + "\" for writing; using the console for output.");
      }
  }
  public static void setFileName () {
      Printer.out = System.out;
  }
  public void println(String string) {
      if (!atNewLine) { atNewLine = true; Printer.out.println(); }
      Printer.out.println(string);
  }
  public void println(ThreadReference  thr, String string) {
      if (!atNewLine) { atNewLine = true; Printer.out.println(); }
      if (values.numThreads () > 1)
          Printer.out.printf ("%-9s: ", thr.name ());
      int numFrames = Trace.CONSOLE_SHOW_CALLS ? values.numFrames (thr) : 0;
      for (int i=1; i<numFrames; i++)
          Printer.out.print ("  ");
      Printer.out.println(string);
  }
  private void printLinePrefix(ThreadReference thr, boolean showLinePrompt) {
      if (atNewLine) {
          atNewLine = false;
          if (values.numThreads () > 1)
              Printer.out.printf ("%-9s: ", thr.name ());
          int numFrames = Trace.CONSOLE_SHOW_CALLS ? values.numFrames (thr) : 0;
          for (int i=1; i<numFrames; i++)
              Printer.out.print ("  ");
          if (showLinePrompt)
              Printer.out.print ("  Line: ");
      }
  }
  public void printLineNum(ThreadReference thr, int lineNum) {
      printLinePrefix (thr, true);
      Printer.out.print(lineNum + " ");
  }
  public void printDrawEvent(ThreadReference thr, String fileName) {
      printLinePrefix (thr, false);
      Printer.out.print("#" + fileName + "# ");
  }
}
/**
* Code for formatting values and other static utilities.
* @author James Riely, jriely@cs.depaul.edu, August 2014
*/
/*private static*/ class Format {
  private Format () { }; // noninstantiable class

  public static StackFrame getFrame (Method meth, ThreadReference thr) {
      Type methDeclaredType = meth.declaringType ();
      int frameNum = -1;
      StackFrame currFrame;
      try {
          do {
              frameNum++;
              currFrame = thr.frame (frameNum);
          } while (methDeclaredType != currFrame.location ().declaringType ());
      } catch (IncompatibleThreadStateException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
      return currFrame;
  }
  private static final String[] EXCLUDE_PREFIXES = new String[TraceInternal.EXCLUDE_PATTERNS.length];
  static {
      for (int i=0; i<EXCLUDE_PREFIXES.length; ++i) {
          EXCLUDE_PREFIXES[i] = TraceInternal.EXCLUDE_PATTERNS[i].substring (0, TraceInternal.EXCLUDE_PATTERNS[i].indexOf ('*'));
      }
  }
  public static boolean matchesExcludePrefix (String typeName) {
      // don't explore objects on the exclude list
      for (String ignoreString : Format.EXCLUDE_PREFIXES)
          if (typeName.startsWith (ignoreString))
              return true;
      return false;
  }
  public static String valueToString (Value value) { return valueToString (false, new HashSet<>(), value); }
  private static String valueToString (boolean inArray, Set<Value> visited, Value value) {
      if (value == null) return "null";
      if (value instanceof PrimitiveValue) return value.toString ();
      if (Trace.SHOW_STRINGS_AS_PRIMITIVE && value instanceof StringReference) return value.toString ();
      if (Trace.SHOW_BOXED_PRIMITIVES_AS_PRIMITIVE && isWrapper (value.type ())) return wrapperToString ((ObjectReference) value);
      return objectToStringLong (inArray, visited, (ObjectReference) value);
  }
  public static boolean isWrapper (Type type) {
      if (!(type instanceof ReferenceType)) return false;
      if (type instanceof ArrayType) return false;
      String fqn = type.name ();
      if (!fqn.startsWith ("java.lang."))
          return false;
      String className = fqn.substring (10);
      if (className.equals ("String"))
          return false;
      return ( className.equals ("Integer")
              || className.equals ("Double")
              || className.equals ("Float")
              || className.equals ("Long")
              || className.equals ("Character")
              || className.equals ("Short")
              || className.equals ("Byte")
              || className.equals ("Boolean")
              );
  }
  public static String wrapperToString (ObjectReference obj) {
      //Object xObject;
      if (obj == null) return "null";
      ReferenceType cz = (ReferenceType) obj.type ();
      //String fqn = cz.name ();
      //String className = fqn.substring (10);
      Field field = cz.fieldByName ("value");
      return obj.getValue (field).toString ();
  }
  public static String objectToStringShort (ObjectReference objRef) {
      if (TraceInternal.CONSOLE_SHOW_TYPE_IN_OBJECT_NAME)
          return shortenFullyQualifiedName (objRef.type ().name ()) + "@" + objRef.uniqueID ();
      else
          return "@" + objRef.uniqueID ();
  }
  private static String emptyArrayToStringShort (ArrayReference arrayRef, int length) {
      if (TraceInternal.CONSOLE_SHOW_TYPE_IN_OBJECT_NAME) {
          String classname = shortenFullyQualifiedName (arrayRef.type ().name ());
          return classname.substring (0, classname.indexOf ("[")) + "[" + length + "]@" + arrayRef.uniqueID ();
      } else {
          return "@" + arrayRef.uniqueID ();
      }
  }
  private static String nonemptyArrayToStringShort (ArrayReference arrayRef, int length) {
      if (TraceInternal.CONSOLE_SHOW_TYPE_IN_OBJECT_NAME)
          return shortenFullyQualifiedName (arrayRef.getValue (0).type ().name ()) + "[" + length + "]@" + arrayRef.uniqueID ();
      else
          return "@" + arrayRef.uniqueID ();
  }

  public static String objectToStringLong (ObjectReference objRef) { return objectToStringLong (false, new HashSet<>(), objRef); }
  private static String objectToStringLong (boolean inArray, Set<Value> visited, ObjectReference objRef) {
      if (!visited.add (objRef))
          return objectToStringShort (objRef);
      StringBuilder result = new StringBuilder ();
      if (objRef == null) {
          return "null";
      } else if (objRef instanceof ArrayReference) {
          ArrayReference arrayRef = (ArrayReference) objRef;
          int length = arrayRef.length ();
          if (length == 0 || arrayRef.getValue (0) == null) {
              if (!inArray || TraceInternal.CONSOLE_SHOW_NESTED_ARRAY_IDS) {
                  result.append (emptyArrayToStringShort (arrayRef, length));
                  result.append (" ");
              }
              result.append ("[ ] ");
          } else {
              if (!inArray || TraceInternal.CONSOLE_SHOW_NESTED_ARRAY_IDS) {
                  result.append (nonemptyArrayToStringShort (arrayRef, length));
                  result.append (" ");
              }
              result.append ("[ ");
              int max = (arrayRef.getValue (0) instanceof PrimitiveValue) ? TraceInternal.CONSOLE_MAX_ARRAY_ELEMENTS_PRIMITIVE : TraceInternal.CONSOLE_MAX_ARRAY_ELEMENTS_OBJECT;
              int i = 0;
              while (i < length && i < max) {
                  result.append (valueToString (true, visited, arrayRef.getValue (i)));
                  i++;
                  if (i < length) result.append (", ");
              }
              if (i < length) result.append ("...");
              result.append (" ]");
          }
      } else {
          result.append (objectToStringShort (objRef));
          ReferenceType type = objRef.referenceType (); // get type (class) of object

          // don't explore objects on the exclude list
          if (!Format.matchesExcludePrefix (type.name ())) {
              Iterator<Field> fields; // use allFields() to include inherited fields
              try { fields = type.fields ().iterator (); } catch (ClassNotPreparedException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
              if (fields.hasNext ()) {
                  result.append (" { ");
                  int i = 0;
                  while (fields.hasNext () && i < TraceInternal.CONSOLE_MAX_FIELDS) {
                      Field f = fields.next ();
                      if (!isObjectField (f))
                          continue;
                      if (i != 0) result.append (", ");
                      result.append (f.name ());
                      result.append ("=");
                      result.append (valueToString (inArray, visited, objRef.getValue (f)));
                      i++;
                  }
                  if (fields.hasNext ()) result.append ("...");
                  result.append (" }");
              }
          }
      }
      return result.toString ();
  }

  // ---------------------- static utilities ----------------------------------

  public static boolean ignoreThread (ThreadReference thr) {
      if (thr.name ().equals ("Signal Dispatcher") || thr.name ().equals ("DestroyJavaVM") || thr.name ().startsWith ("AWT-"))
          return true; // ignore AWT threads
      if (thr.threadGroup ().name ().equals ("system"))
          return true; // ignore system threads
      return false;
  }

  public static boolean isStaticField (Field f) {
      if (!TraceInternal.CONSOLE_SHOW_SYNTHETIC_FIELDS && f.isSynthetic ()) return false;
      return f.isStatic ();
  }
  public static boolean isObjectField (Field f) {
      if (!TraceInternal.CONSOLE_SHOW_SYNTHETIC_FIELDS && f.isSynthetic ()) return false;
      return !f.isStatic ();
  }
  public static boolean isConstructor (Method m) {
      if (!TraceInternal.CONSOLE_SHOW_SYNTHETIC_METHODS && m.isSynthetic ()) return false;
      return m.isConstructor ();
  }
  public static boolean isObjectMethod (Method m) {
      if (!TraceInternal.CONSOLE_SHOW_SYNTHETIC_METHODS && m.isSynthetic ()) return false;
      return !m.isConstructor () && !m.isStatic ();
  }
  public static boolean isClassMethod (Method m) {
      if (!TraceInternal.CONSOLE_SHOW_SYNTHETIC_METHODS && m.isSynthetic ()) return false;
      return m.isStatic ();
  }
  public static boolean tooManyFields (ObjectReference objRef) {
      int count = 0;
      ReferenceType type = ((ReferenceType) objRef.type ());
      for (Field field : type.fields ())
          if (isObjectField (field))
              count++;
      return count > TraceInternal.CONSOLE_MAX_FIELDS;
  }
  public static String shortenFullyQualifiedName (String fqn) {
      if (TraceInternal.SHOW_PACKAGE_IN_CLASS_NAME || !fqn.contains ("."))
          return fqn;
      String className = fqn.substring (1 + fqn.lastIndexOf ("."));
      if (TraceInternal.SHOW_OUTER_CLASS_IN_CLASS_NAME || !className.contains ("$"))
          return className;
      return className.substring (1 + className.lastIndexOf ("$"));
  }
  public static String shortenFileName (String fn) {
      if (!fn.contains ("/"))
          return fn;
      return fn.substring (1 + fn.lastIndexOf ("/"));
  }
  public static String fieldToString (Field f) {
      StringBuilder result = new StringBuilder();
      if (f.isPrivate ())
          result.append ("- ");
      if (f.isPublic ())
          result.append ("+ ");
      if (f.isPackagePrivate ())
          result.append ("~ ");
      if (f.isProtected ())
          result.append ("# ");
      result.append (shortenFullyQualifiedName (f.name ()));
      result.append (" : ");
      result.append (shortenFullyQualifiedName (f.typeName ()));
      return result.toString();
  }
  public static String methodToString (Method m, boolean showClass) {
      String className = shortenFullyQualifiedName (m.declaringType (). name ());
      StringBuilder result = new StringBuilder();
      if (!showClass) {
          if (m.isPrivate ())
              result.append ("- ");
          if (m.isPublic ())
              result.append ("+ ");
          if (m.isPackagePrivate ())
              result.append ("~ ");
          if (m.isProtected ())
              result.append ("# ");
      }
      if (m.isConstructor ()) {
          result.append (className);
      } else if (m.isStaticInitializer ()) {
          result.append (className);
          result.append (".CLASS_INITIALIZER");
          return result.toString ();
      } else {
          if (showClass) {
              result.append (className);
              result.append (".");
          }
          result.append (shortenFullyQualifiedName (m.name ()));
      }
      result.append ("(");
      Iterator<LocalVariable> vars;
      try {
          vars = m.arguments ().iterator ();
          while (vars.hasNext ()) {
              result.append (shortenFullyQualifiedName (vars.next ().typeName ()));
              if (vars.hasNext ()) result.append (", ");
          }
      } catch (AbsentInformationException e) {
          result.append ("??");
      }
      result.append (")");
      //result.append (" from ");
      //result.append (m.declaringType ());
      return result.toString();
  }
}

/**
* A map from filenames to file contents.  Allows lines to be printed.
*
* changes: Riely inlined the "ShowLines" class.
*
* @author Andrew Davison, March 2009, ad@fivedots.coe.psu.ac.th
* @author James Riely
**/
/*private static*/ class CodeMap {
  private TreeMap<String, ArrayList<String>> listings = new TreeMap<> ();

  // add fileName-ShowLines pair to map
  public void addFile (String fileName) {
      if (listings.containsKey (fileName)) {
          //System.err.println (fileName + "already listed");
          return;
      }

      ArrayList<String> code = new ArrayList<> ();
      BufferedReader in = null;
      try {
          in = new BufferedReader (new FileReader ("src/" + fileName));
          String line;
          while ((line = in.readLine ()) != null)
              code.add (line);
      } catch (IOException ex) {
          System.err.println ("\n!!!! Problem reading " + fileName);
      } finally {
          try {
              if (in != null) in.close ();
          } catch (IOException e) {
              throw new Error ("\n!!!! Problem with " + fileName);
          }
      }
      listings.put (fileName, code);
      //println (fileName + " added to listings");
  }

  // return the specified line from fileName
  public String show (String fileName, int lineNum) {
      ArrayList<String> code = listings.get (fileName);
      if (code == null) return (fileName + " not listed");
      if ((lineNum < 1) || (lineNum > code.size ())) return "Line no. out of range";
      return (code.get (lineNum - 1));
  }

}

/**
* Map from threads to booleans.
* @author James Riely, jriely@cs.depaul.edu, August 2014
*/
/*private static*/ class InsideIgnoredMethodMap {
  // Stack is probably unnecessary here.  A single boolean would do.
  private HashMap<ThreadReference,Stack<Boolean>> map = new HashMap<> ();
  public void removeThread (ThreadReference thr) {
      map.remove (thr);
  }
  public void addThread (ThreadReference thr) {
      Stack<Boolean> st = new Stack<> ();
      st.push (false);
      map.put (thr, st);
  }
  public void enteringIgnoredMethod (ThreadReference thr) {
      map.get(thr).push (true);
  }
  public boolean leavingIgnoredMethod (ThreadReference thr) {
      Stack<Boolean> insideStack = map.get (thr);
      boolean result = insideStack.peek ();
      if (result)
          insideStack.pop ();
      return result;
  }
  public boolean insideIgnoredMethod (ThreadReference thr) {
      return map.get (thr).peek ();
  }
}

/** From sedgewick and wayne */
/*private static*/ class Stack<Item> {
  private int N;
  private Node first;
  private class Node { private Item item; private Node next; }
  public Stack() { first = null; N = 0; }
  public boolean isEmpty() { return first == null; }
  public int size() { return N; }
  public void push(Item item) {
      Node oldfirst = first;
      first = new Node();
      first.item = item;
      first.next = oldfirst;
      N++;
  }
  public Item pop() {
      if (isEmpty()) throw new NoSuchElementException("Stack underflow");
      Item item = first.item;
      first = first.next;
      N--;
      return item;
  }
  public Item peek() {
      if (isEmpty()) throw new NoSuchElementException("Stack underflow");
      return first.item;
  }
}


/**
* Keeps track of values in order to spot changes.
* This keeps copies of stack variables (frames) and arrays.
* Does not store objects, since direct changes to fields can be trapped by the JDI.
* @author James Riely, jriely@cs.depaul.edu, August 2014
*/
/*private static*/ class ValueMap {
  private HashMap<ThreadReference,Stack<HashMap<LocalVariable,Value>>> stacks = new HashMap<> ();
  private HashMap<Value,Object[]> arrays = new HashMap<> ();

  public int numThreads () {
      return stacks.size ();
  }
  public int numFrames (ThreadReference thr) {
      return stacks.get (thr).size ();
  }
  public void stackCreate (ThreadReference thr) {
      stacks.put (thr, new Stack<> ());
  }
  public void stackDestroy (ThreadReference thr) {
      stacks.remove (thr);
  }
  public void stackPushFrame (StackFrame currFrame, ThreadReference thr) {
      if (!TraceInternal.CONSOLE_SHOW_VARIABLES)
          return;
      List<LocalVariable> locals;
      try { locals = currFrame.visibleVariables (); } catch (AbsentInformationException e) { return; }

      Stack<HashMap<LocalVariable,Value>> stack = stacks.get (thr);
      HashMap<LocalVariable,Value> frame = new HashMap<> ();
      stack.push (frame);

      for (LocalVariable l : locals) {
          Value v = currFrame.getValue (l);
          frame.put (l, v);
          if (v instanceof ArrayReference)
              registerArray ((ArrayReference) v);
      }
  }

  public void stackPopFrame (ThreadReference thr) {
      if (!TraceInternal.CONSOLE_SHOW_VARIABLES) return;
      Stack<HashMap<LocalVariable,Value>> stack = stacks.get (thr);
      stack.pop ();
      // space leak in arrays HashMap: arrays never removed
  }

  public void stackUpdateFrame (Method meth, ThreadReference thr, IndentPrinter printer) {
      if (!TraceInternal.CONSOLE_SHOW_VARIABLES)
          return;
      StackFrame currFrame = Format.getFrame (meth, thr);
      List<LocalVariable> locals;
      try { locals = currFrame.visibleVariables (); } catch (AbsentInformationException e) { return; }
      Stack<HashMap<LocalVariable,Value>> stack = stacks.get (thr);
      HashMap<LocalVariable,Value> frame = stack.peek ();

      String debug = TraceInternal.DEBUG_VARIABLE_PRINTING ? "#1" : "";
      for (LocalVariable l : locals) {
          Value oldValue = frame.get (l);
          Value newValue = currFrame.getValue (l);
          if (valueHasChanged (oldValue, newValue)) {
              frame.put (l, newValue);
              if (newValue instanceof ArrayReference) registerArray ((ArrayReference) newValue);
              String change = (oldValue == null) ? "|" : ">";
              printer.println (thr, "  " + debug + change + " " + l.name () + " = " + Format.valueToString (newValue));
          }
      }

      ObjectReference thisObj = currFrame.thisObject ();
      if (thisObj != null) {
          boolean show = Format.tooManyFields (thisObj);
          if (arrayFieldHasChanged (show, thr, thisObj, printer) && !show)
              printer.println (thr, "  " + debug + "> this = " + Format.objectToStringLong (thisObj));
      }
  }

  public void registerArray (ArrayReference val) {
      if (!arrays.containsKey (val)) {
          arrays.put (val, copyArray (val));
      }
  }
  private static Object[] copyArray (ArrayReference oldArrayReference) {
      Object[] newArray = new Object[oldArrayReference.length ()];
      for (int i = 0; i < newArray.length; i++) {
          Value val = oldArrayReference.getValue (i);
          if (val instanceof ArrayReference)
              newArray[i] = copyArray ((ArrayReference) val);
          else
              newArray[i] = val;
      }
      return newArray;
  }

  private boolean valueHasChanged (Value oldValue, Value newValue) {
      if (oldValue==null && newValue==null) return false;
      if (oldValue==null && newValue!=null) return true;
      if (oldValue!=null && newValue==null) return true;
      if (!oldValue.equals (newValue)) return true;
      if (!(oldValue instanceof ArrayReference)) return false;
      return arrayValueHasChanged ((ArrayReference) oldValue, (ArrayReference) newValue);
  }
  private boolean arrayFieldHasChanged (Boolean show, ThreadReference thr, ObjectReference objRef, IndentPrinter printer) {
      ReferenceType type = objRef.referenceType (); // get type (class) of object
      List<Field> fields; // use allFields() to include inherited fields
      try { fields = type.fields (); } catch (ClassNotPreparedException e) { throw new Error (TraceInternal.BAD_ERROR_MESSAGE); }
      boolean result = false;
      String debug = TraceInternal.DEBUG_VARIABLE_PRINTING ? "#2" : "";
      String change = ">";
      for (Field f : fields) {
          Boolean print = false;
          Value v = objRef.getValue (f);
          if (!(v instanceof ArrayReference))
              continue;
          ArrayReference a = (ArrayReference) v;
          if (!arrays.containsKey (a)) {
              registerArray (a);
              change = "|";
              result = true;
              print = true;
          } else {
              Object[] objArray = arrays.get (a);
              if (arrayValueHasChangedHelper (objArray, a)) {
                  result = true;
                  print = true;
              }
          }
          if (show && print)
              printer.println (thr, "  " + debug + change + " " + Format.objectToStringShort (objRef) + "." + f.name () + " = " + Format.valueToString (objRef.getValue (f)));
      }
      return result;
  }
  private boolean arrayValueHasChanged (ArrayReference oldArray, ArrayReference newArray) {
      if (oldArray.length () != newArray.length ())
          return true;
     // int len = oldArray.length ();
      if (!arrays.containsKey (newArray)) {
          return true;
      }
      Object[] oldObjArray = arrays.get (newArray);
      //            if (oldObjArray.length != len)
      //                throw new Error (TraceInternal.BAD_ERROR_MESSAGE);
      return arrayValueHasChangedHelper (oldObjArray, newArray);
  }
  private boolean arrayValueHasChangedHelper (Object[] oldObjArray, ArrayReference newArray) {
      int len = oldObjArray.length;
      boolean hasChanged = false;
      for (int i=0; i<len; i++) {
          Object oldObject = oldObjArray[i];
          Value newVal = newArray.getValue (i);
          if (oldObject == null && newVal != null) {
              oldObjArray[i] = newVal;
              hasChanged = true;
          }
          if (oldObject instanceof Value && valueHasChanged ((Value) oldObject, newVal)) {
              //System.out.println ("BOB:" + i + ":" + oldObject + ":" + newVal);
              oldObjArray[i] = newVal;
              hasChanged = true;
          }
          if (oldObject instanceof Object[]) {
              //if (!(newVal instanceof ArrayReference)) throw new Error (TraceInternal.BAD_ERROR_MESSAGE);
              if (arrayValueHasChangedHelper ((Object[]) oldObject, (ArrayReference) newVal)) {
                  hasChanged = true;
              }
          }
      }
      return hasChanged;
  }
}

/*private static*/ class Graphviz {
  private Graphviz () { } // noninstantiable class
  //- This code is based on LJV:
  // LJV.java --- Generate a graph of an object, using Graphviz
  // The Lightweight Java Visualizer (LJV)
  // https://www.cs.auckland.ac.nz/~j-hamer/

  //- Author:     John Hamer <J.Hamer@cs.auckland.ac.nz>
  //- Created:    Sat May 10 15:27:48 2003
  //- Time-stamp: <2004-08-23 12:47:06 jham005>

  //- Copyright (C) 2004  John Hamer, University of Auckland
  //-
  //-   This program is free software; you can redistribute it and/or
  //-   modify it under the terms of the GNU General Public License
  //-   as published by the Free Software Foundation; either version 2
  //-   of the License, or (at your option) any later version.
  //-
  //-   This program is distributed in the hope that it will be useful,
  //-   but WITHOUT ANY WARRANTY; without even the implied warranty of
  //-   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  //-   GNU General Public License for more details.
  //-
  //-   You should have received a copy of the GNU General Public License along
  //-   with this program; if not, write to the Free Software Foundation, Inc.,
  //-   59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.

  //- $Id: LJV.java,v 1.1 2004/07/14 02:03:45 jham005 Exp $
  //

  /** The name of the output file is derived from <code>baseFileName</code> by appending successive integers. */
  public static String peekFileName ()  {  return String.format ("%03d", nextGraphNumber); }
  private static String nextFileName () {  ++nextGraphNumber; return baseFileName + peekFileName (); }
  private static int nextGraphNumber = -1;
  private static String baseFileName = "graph-";
  public static void setBaseFileName (String s) { 
      File f = new File(s);
      String newBaseFileName;
      {
          File parent = (f==null) ? null : f.getParentFile ();
          if (parent == null || parent.canWrite ()) {            
              newBaseFileName = s;
          } else {
              System.err.println ("Attempting setBaseFileName \"" + f + "\"");
              System.err.println ("Cannot open directory \"" + f.getParent() + "\" for writing; using the current directory for graphziv output.");
              newBaseFileName = f.getName ();
          }
      }
      if (!newBaseFileName.equals (baseFileName)) {
          baseFileName = newBaseFileName;
          nextGraphNumber = -1;
      }
  }

  public static final HashMap<String,String> objectAttributeMap = new HashMap<> ();
  public static final HashMap<String,String> staticClassAttributeMap  = new HashMap<> ();
  public static final HashMap<String,String> frameAttributeMap = new HashMap<> ();
  public static final HashMap<String,String> fieldAttributeMap = new HashMap<> ();

  // local copies with correct punctuation
  private static final String graphvizFrameArrowAttributes = (TraceInternal.GRAPHVIZ_FRAME_ARROW_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_FRAME_ARROW_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_FRAME_ARROW_ATTRIBUTES;
  private static final String graphvizFrameBoxAttributes = (TraceInternal.GRAPHVIZ_FRAME_BOX_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_FRAME_BOX_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_FRAME_BOX_ATTRIBUTES;
  private static final String graphvizFrameFrameAttributes = (TraceInternal.GRAPHVIZ_FRAME_FRAME_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_FRAME_FRAME_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_FRAME_FRAME_ATTRIBUTES;
  private static final String graphvizFrameReturnAttributes = (TraceInternal.GRAPHVIZ_FRAME_RETURN_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_FRAME_RETURN_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_FRAME_RETURN_ATTRIBUTES;
  private static final String graphvizArrayArrowAttributes = (TraceInternal.GRAPHVIZ_ARRAY_ARROW_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_ARRAY_ARROW_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_ARRAY_ARROW_ATTRIBUTES;
  private static final String graphvizArrayBoxAttributes = (TraceInternal.GRAPHVIZ_ARRAY_BOX_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_ARRAY_BOX_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_ARRAY_BOX_ATTRIBUTES;
  private static final String graphvizObjectArrowAttributes = (TraceInternal.GRAPHVIZ_OBJECT_ARROW_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_OBJECT_ARROW_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_OBJECT_ARROW_ATTRIBUTES;
  private static final String graphvizObjectBoxAttributes = (TraceInternal.GRAPHVIZ_OBJECT_BOX_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_OBJECT_BOX_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_OBJECT_BOX_ATTRIBUTES;
  private static final String graphvizStaticClassArrowAttributes = (TraceInternal.GRAPHVIZ_STATIC_CLASS_ARROW_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_STATIC_CLASS_ARROW_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_STATIC_CLASS_ARROW_ATTRIBUTES;
  private static final String graphvizStaticClassBoxAttributes = (TraceInternal.GRAPHVIZ_STATIC_CLASS_BOX_ATTRIBUTES == null || "".equals (TraceInternal.GRAPHVIZ_STATIC_CLASS_BOX_ATTRIBUTES))
          ? "" :  "," + TraceInternal.GRAPHVIZ_STATIC_CLASS_BOX_ATTRIBUTES;

  // ----------------------------------- utilities -----------------------------------------------

  private static boolean canTreatAsPrimitive (Value v) {
      if (v == null || v instanceof PrimitiveValue)
          return true;
      if (Trace.SHOW_STRINGS_AS_PRIMITIVE && v instanceof StringReference) return true;
      if (Trace.SHOW_BOXED_PRIMITIVES_AS_PRIMITIVE && Format.isWrapper (v.type ())) return true;
      return false;
  }
  private static boolean looksLikePrimitiveArray (ArrayReference obj) {
      try {
          if (((ArrayType) obj.type()).componentType () instanceof PrimitiveType)
              return true;
      } catch (ClassNotLoadedException e) {
          return false;
      }

      for (int i = 0, len = obj.length (); i < len; i++)
          if (! canTreatAsPrimitive(obj.getValue (i)))
              return false;
      return true;
  }
  private static boolean canIgnoreObjectField (Field field) {
      if (!Format.isObjectField (field)) return true;
      for (String ignoredField : TraceInternal.GRAPHVIZ_IGNORED_FIELDS)
          if (ignoredField.equals (field.name ()))
              return true;
      return false;
  }
  private static boolean canIgnoreStaticField (Field field) {
      if (!Format.isStaticField (field)) return true;
      for (String ignoredField : TraceInternal.GRAPHVIZ_IGNORED_FIELDS)
          if (ignoredField.equals (field.name ()))
              return true;
      return false;
  }

  private static final String canAppearUnquotedInLabelChars = " $&*@#!-+()^%;[],;.=";
  private static boolean canAppearUnquotedInLabel (char c) {
      return canAppearUnquotedInLabelChars.indexOf (c) != -1
              || Character.isLetter (c)
              || Character.isDigit (c)
              ;
  }

  private static final String quotable = "\"<>{}|";
  private static String quote (String s) {
      StringBuffer sb = new StringBuffer ();
      for (int i = 0, n = s.length (); i < n; i++) {
          char c = s.charAt(i);
          if (quotable.indexOf(c) != -1)
              sb.append ('\\').append (c);
          else
              if (canAppearUnquotedInLabel (c))
                  sb.append (c);
              else
                  sb.append("\\\\0u").append (Integer.toHexString (c));
      }
      return sb.toString ();
  }

  // ----------------------------------- values -----------------------------------------------

  private static void processPrimitiveArray (ArrayReference obj, PrintWriter out) {
      out.print (objectDotName (obj) + "[label=\"");
      for (int i = 0, len = obj.length (); i < len; i++) {
          if (i != 0)
              out.print ("|");
          Value v = obj.getValue (i);
          if (v != null)
              out.print (quote(v.toString ()));
      }
      out.println ("\"" + graphvizArrayBoxAttributes + "];");
  }
  private static void processObjectArray (ArrayReference obj, PrintWriter out, Set<ObjectReference> visited) {
      out.print (objectDotName (obj) + "[label=\"");
      int len = obj.length ();
      for (int i = 0; i < len; i++) {
          if (i != 0)
              out.print ("|");
          out.print ("<A" + i + ">");
      }
      out.println ("\"" + graphvizArrayBoxAttributes + "];");
      for (int i = 0; i < len; i++) {
          ObjectReference ref = (ObjectReference) obj.getValue (i);
          if (ref == null)
              continue;
          out.println (objectDotName (obj) + ":f" + i + " -> " + objectDotName (ref)
                  + "[label=\"" + i + "\"" + graphvizArrayArrowAttributes + "];");
          processObject (ref, out, visited);
      }
  }
  private static void processValueStandalone (String source, String attributes, String name, Value val, PrintWriter out, Set<ObjectReference> visited) {
      if (canTreatAsPrimitive(val))
          return;
      ObjectReference objRef = (ObjectReference) val;
      String dotName = objectDotName (objRef);
      out.println (source + " -> " + dotName
              + "[label=\"" + name + "\""
              + attributes
              + "];");
      processObject (objRef, out, visited);
  }
  private static boolean processValueInline (String name, Value val, PrintWriter out) {
      if (!canTreatAsPrimitive(val))
          return false;
      out.print (name);
      if (val == null) {
          out.print (quote ("null"));
      } else if (val instanceof PrimitiveValue) {
          out.print (quote (val.toString ()));
      } else if (Trace.SHOW_STRINGS_AS_PRIMITIVE && val instanceof StringReference) {
          out.print (quote (val.toString ()));
      } else if (Trace.SHOW_BOXED_PRIMITIVES_AS_PRIMITIVE && Format.isWrapper (val.type ())) {
          out.print (quote (Format.wrapperToString ((ObjectReference) val)));
      }
      return true;
  }

  // ----------------------------------- objects -----------------------------------------------

  private static String objectName (ObjectReference obj) {
      if (obj == null) return "";
      String objString = (TraceInternal.GRAPHVIZ_SHOW_OBJECT_IDS) ? "@" + obj.uniqueID () + " : " : "";
      return objString + Format.shortenFullyQualifiedName(obj.type ().name ());
  }
  private static String objectDotName (ObjectReference obj) {
      return "N" + obj.uniqueID ();
  }
  private static boolean objectHasPrimitives (List<Field> fs, ObjectReference obj) {
      for (Field f : fs) {
          if (canIgnoreObjectField(f))
              continue;
          if(canTreatAsPrimitive(obj.getValue (f)))
              return true;
      }
      return false;
  }
  private static void labelObjectWithNoPrimitiveFields (ObjectReference obj, PrintWriter out) {
      String cabs  = objectAttributeMap.get (obj.type ().name ());
      out.println (objectDotName (obj)
              + "[label=\"" + objectName (obj) + "\""
              + graphvizObjectBoxAttributes
              + (cabs == null ? "" : "," + cabs)
              + "];");
  }
  private static void labelObjectWithSomePrimitiveFields(ObjectReference obj, List<Field> fs, PrintWriter out) {
      out.print (objectDotName (obj) + "[label=\"" + objectName (obj) + "|{");
      String sep = "";
      for (Field field : fs) {
          if (! canIgnoreObjectField (field)) {
              String name = TraceInternal.GRAPHVIZ_SHOW_FIELD_NAMES_IN_LABELS ? field.name () + " = " : "";
              if (processValueInline (sep + name, obj.getValue (field), out))
                  sep = "|";
          }
      }
      String cabs  = objectAttributeMap.get (obj.type ().name ());
      out.println ("}\""
              + graphvizObjectBoxAttributes
              + (cabs == null ? "" : "," + cabs)
              + "];");
  }
  private static void processObject (ObjectReference obj, PrintWriter out, Set<ObjectReference> visited) {
      if (visited.add (obj)) {
          if ((TraceInternal.GRAPHVIZ_SHOW_BOXED_PRIMITIVES_SIMPLY || TraceInternal.GRAPHVIZ_SHOW_BOXED_PRIMITIVES_VERY_SIMPLY) && Format.isWrapper (obj.type ()))  {
              String cabs  = objectAttributeMap.get (obj.type ().name ());
              out.print (objectDotName (obj) + "[label=\"");
              if (!TraceInternal.GRAPHVIZ_SHOW_BOXED_PRIMITIVES_VERY_SIMPLY)
                  out.print (objectName (obj) + "|");
              out.println (Format.wrapperToString (obj) +  "\""
                      + graphvizObjectBoxAttributes
                      + (cabs == null ? "" : "," + cabs)
                      + "];");
          } else if (obj instanceof ArrayReference) {
              ArrayReference arr = (ArrayReference) obj;
              if (looksLikePrimitiveArray (arr))
                  processPrimitiveArray (arr, out);
              else
                  processObjectArray (arr, out, visited);
          } else {
              ReferenceType type = (ReferenceType)obj.type ();
              List<Field> fs = type.fields ();
              if (objectHasPrimitives (fs, obj))
                  labelObjectWithSomePrimitiveFields (obj, fs, out);
              else
                  labelObjectWithNoPrimitiveFields (obj, out);

              if (!Format.matchesExcludePrefix (type.name ())) {
                  String source = objectDotName (obj);
                  for (Field f : fs) {
                      if (! canIgnoreObjectField (f)) {
                          String name = f.name ();
                          processValueStandalone (source, graphvizObjectArrowAttributes, name, obj.getValue (f), out, visited);
                      }
                  }
              }
          }
      }
  }

  // ----------------------------------- static classes -----------------------------------------------

  private static String staticClassName (ReferenceType type) {
      return Format.shortenFullyQualifiedName(type.name ());
  }
  private static String staticClassDotName (ReferenceType type) {
      return "S" + type.classObject ().uniqueID ();
  }
  private static boolean staticClassHasPrimitives (List<Field> fs, ReferenceType staticClass) {
      for (Field f : fs) {
          if (canIgnoreStaticField(f))
              continue;
          if(canTreatAsPrimitive(staticClass.getValue (f)))
              return true;
      }
      return false;
  }
  private static void labelStaticClassWithNoPrimitiveFields (ReferenceType type, PrintWriter out) {
      String cabs  = staticClassAttributeMap.get (type.name ());
      out.println (staticClassDotName (type)
              + "[label=\"" + staticClassName (type) + "\""
              + graphvizStaticClassBoxAttributes
              + (cabs == null ? "" : "," + cabs)
              + "];");
  }
  private static void labelStaticClassWithSomePrimitiveFields(ReferenceType type, List<Field> fs, PrintWriter out) {
      out.print (staticClassDotName (type) + "[label=\"" + staticClassName (type) + "|{");
      String sep = "";
      for (Field field : fs) {
          if (! canIgnoreStaticField (field)) {
              String name = TraceInternal.GRAPHVIZ_SHOW_FIELD_NAMES_IN_LABELS ? field.name () + " = " : "";
              if (processValueInline (sep + name, type.getValue (field), out))
                  sep = "|";
          }
      }
      String cabs  = staticClassAttributeMap.get (type.name ());
      out.println ("}\""
              + graphvizStaticClassBoxAttributes
              + (cabs == null ? "" : "," + cabs)
              + "];");
  }
  private static void processStaticClass (ReferenceType type, PrintWriter out, Set<ObjectReference> visited) {
      List<Field> fs = type.fields ();
      if (staticClassHasPrimitives (fs, type))
          labelStaticClassWithSomePrimitiveFields (type, fs, out);
      else
          labelStaticClassWithNoPrimitiveFields (type, out);

      if (!Format.matchesExcludePrefix (type.name ())) {
          String source = staticClassDotName (type);
          for (Field f : fs) {
              if (! canIgnoreStaticField (f)) {
                  String name = f.name ();
                  processValueStandalone (source, graphvizStaticClassArrowAttributes, name, type.getValue (f), out, visited);
              }
          }
      }
  }

  // ----------------------------------- frames -----------------------------------------------

  private static String frameName (int frameNum, ReferenceType type, Method method, int lineNum) {
      String objString = (TraceInternal.GRAPHVIZ_SHOW_OBJECT_IDS) ? "@" + frameNum + " : " : "";
      if (method.isConstructor ()) {
          return objString + Format.shortenFullyQualifiedName(type.name ()) + " # " + lineNum;
      } else {
          return objString + Format.shortenFullyQualifiedName(type.name ()) + "." + method.name () + " # " + lineNum;
      }
  }
  private static String frameDotName (int frameNum) {
      return "F" + frameNum;
  }
  private static boolean frameHasPrimitives (Map<LocalVariable, Value> ls) {
      for (LocalVariable lv : ls.keySet ()) {
          Value v = ls.get (lv);
          if (canTreatAsPrimitive(v))
              return true;
      }
      return false;
  }
  private static void labelFrameWithNoPrimitiveLocals (int frameNum, StackFrame frame, PrintWriter out) {
      Location location = frame.location ();
      ReferenceType type = location.declaringType ();
      Method method = location.method ();
      String attributes  = frameAttributeMap.get (type.name ());
      out.println (frameDotName (frameNum)
              + "[label=\"" + frameName (frameNum, type, method, location.lineNumber ()) + "\""
              + graphvizFrameBoxAttributes
              + (attributes == null ? "" : "," + attributes)
              + "];");
  }
  private static void labelFrameWithSomePrimitiveLocals (int frameNum, StackFrame frame, Map<LocalVariable, Value> ls, PrintWriter out) {
      Location location = frame.location ();
      ReferenceType type = location.declaringType ();
      Method method = location.method ();
      out.print (frameDotName (frameNum)
              + "[label=\"" + frameName (frameNum, type, method, location.lineNumber ()) + "|{");
      String sep = "";
      for (LocalVariable lv: ls.keySet ()) {
          String name = TraceInternal.GRAPHVIZ_SHOW_FIELD_NAMES_IN_LABELS ? lv.name () + " = " : "";
          if (processValueInline (sep + name, ls.get(lv), out))
              sep = "|";
      }
      String cabs  = frameAttributeMap.get (type.name ());
      out.println ("}\""
              + graphvizFrameBoxAttributes
              + (cabs == null ? "" : "," + cabs)
              + "];");
  }
  private static boolean processFrame (int frameNum, StackFrame frame, PrintWriter out, Set<ObjectReference> visited) {
      Location location = frame.location ();
      ReferenceType type = location.declaringType ();
    //  Method method = location.method ();
      if (Format.matchesExcludePrefix (type.name ()))
          return false;

      Map<LocalVariable,Value> ls;
      try { ls = frame.getValues (frame.visibleVariables ()); } catch (AbsentInformationException e) { return false; }
      if (frameHasPrimitives (ls)) {
          labelFrameWithSomePrimitiveLocals (frameNum, frame, ls, out);
      } else {
          labelFrameWithNoPrimitiveLocals (frameNum, frame, out);
      }
      ObjectReference thisObject = frame.thisObject ();
      if (thisObject != null)
          processValueStandalone (frameDotName (frameNum), graphvizFrameArrowAttributes, "this", thisObject, out, visited);
      for (LocalVariable lv : ls.keySet ()) {
          processValueStandalone (frameDotName (frameNum), graphvizFrameArrowAttributes, lv.name (), ls.get (lv), out, visited);
      }
      return true;
  }

  // ----------------------------------- top level -----------------------------------------------

  private static void processFrames (int start, Value returnVal, List<StackFrame> frames, Set<ReferenceType> staticClasses, PrintWriter out) {
      out.println ("digraph Java {");
      Set<ObjectReference> visited = new HashSet<> ();
      for (ReferenceType staticClass : staticClasses) {
          processStaticClass (staticClass, out, visited);
      }
      int len=frames.size ();
      for (int i=len-1, prev=i; i >= start; i--) {
          if (processFrame (len-i, frames.get (i), out, visited)) {
              if (prev != i) {
                  out.println (frameDotName (len-i) + " -> " + frameDotName (len-prev) + "[label=\"\"" + graphvizFrameFrameAttributes + "];");
                  prev = i;
              }
          }
      }
      // show the return value -- without this, it mysteriously disappears when drawing all steps
      if (returnVal != null && !(returnVal instanceof VoidValue)) {
          if (canTreatAsPrimitive (returnVal)) {
              out.print ("returnVal [label=\"returnValue : ");
              processValueInline ("", returnVal, out);
              out.println ( "\""
                      + graphvizFrameReturnAttributes
                      + "];");
              out.println ("returnVal -> " + frameDotName (len) + "[label=\"\"" + graphvizFrameFrameAttributes + "];");
          } else {
              out.println ("returnVal [label=\"returnValue\"" + graphvizFrameReturnAttributes + "];");
              processValueStandalone ("returnVal", graphvizFrameArrowAttributes, "", returnVal, out, visited);
              out.println ("returnVal -> " + frameDotName (len) + "[label=\"\"" + graphvizFrameFrameAttributes + "];");
          }
      }
      out.println ("}");
  }

  public static void drawFrames (int start, Value returnVal, List<StackFrame> frames, Set<ReferenceType> staticClasses) {
      String filenamePrefix = nextFileName ();
      File dotFile = new File (filenamePrefix + ".dot");
      PrintWriter out;
      try {
          out = new PrintWriter (new FileWriter (dotFile));
      } catch (IOException e) {
          throw new Error ("\n!!!! Cannot open " + dotFile + "for writing");
      }
      processFrames (start, returnVal, frames, staticClasses, out);
      out.close ();
      if (Trace.GRAPHVIZ_RUN_DOT) {
          String executable = null;
          for (String s : TraceInternal.GRAPHVIZ_POSSIBLE_DOT_LOCATIONS) {
              if (new File (s).canExecute ()) 
                  executable = s; 
          }
          if (executable != null) {
              ProcessBuilder pb = new ProcessBuilder (executable, "-T", Trace.GRAPHVIZ_DOT_OUTPUT_FORMAT);
              File outFile = new File (filenamePrefix + "." + Trace.GRAPHVIZ_DOT_OUTPUT_FORMAT);
              pb.redirectInput (dotFile);
              pb.redirectOutput(outFile);
              int result = -1;
              try {
                  result = pb.start ().waitFor ();
              } catch (IOException e) {
                  throw new Error ("\n!!!! Cannot execute " + executable +
                          "\n!!!! Make sure you have installed http://www.graphviz.org/" +
                          "\n!!!! Check the value of GRAPHVIZ_DOT_COMMAND in " + Trace.class.getCanonicalName ());
              } catch (InterruptedException e) {
                  throw new Error ("\n!!!! Execution of " + executable + "interrupted");
              }
              if (result == 0) {
                  if (TraceInternal.GRAPHVIZ_REMOVE_DOT_FILES) {
                      dotFile.delete ();
                  }
              } else {
                  outFile.delete ();
              }
          }
      }
  }
}

