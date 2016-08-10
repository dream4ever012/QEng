package tracer;

public class TraceTestExample {
    public static void main(String[] args) {
        X x = new X(1);
        zzz = 42;
        x.f (2);
        //stdlib.StdOut.println ("x=" + x);
        //threadTest();
    }
    
    private TraceTestExample() { }
    static int zzz = 5;
    static X lastX = null;

    static class Counter {
        private final String name;
        private int count;
        public Counter(String id) { name = id; }
        public void increment() { count++; }
        public int tally() { return count; }
        public String toString() { return count + " " + name; }
    }
    static class X {
        int[][] b = new int[3][2];
        public Object object = new Object ();
        protected Integer integer = new Integer (42);
        private Boolean boolean1 = false;
        Character character = 'a';
        Short short1 = 1;
        Long long1 = 1L;
        Float float1 = 1.0F;
        Double double1 = 1.0;
        int intVal = 1;
        Object nil = null;
        String string = "dog";
        private void g () { }
        void h ( ) { }
        protected void p () { }
        public String toString() {
            return "" + intVal;
        }
        private class Inner implements Runnable {
            public void run () {
                b[1][1] = 5;
                long1 = 46L;
            }
        }
        private class InnerWithConstructor implements Runnable {
            int x;
            public InnerWithConstructor (int x) { this.x = x; }
            public void run () {
                b[1][1] = 5;
                long1 = (long) x;
            }
        }
        public X (int i) {intVal = i; }
        public int f (int x) {
            TraceTestExample.lastX = this;
            int z = 33;
            float ff = 65;
            b[0][1] = 2;
            b[1][0] = 2;
            b[1][0] = 2;
            b[1][0] = 2;
            Counter c = new Counter ("x");
            c.increment ();
            System.out.println (c);
            InnerWithConstructor inner1 = new InnerWithConstructor (3);
            inner1.run ();
            Inner inner2 = new Inner ();
            inner1.run ();
            //stdlib.In xIn = new stdlib.In ("/tmp/x");
            TraceGraph.draw();
            if (x>0)
                return f(x-1) + 1;
            else
                return 42;
        }
    }
    private static void threadTest() {
        Counter c = new Counter ("c1");
        Thread t1 = new T (c);
        Thread t2 = new T (c);
        t1.start ();
        t2.start ();
        for (int i=0; i<50; i++) {
            c.increment ();
        }
        try {
            t1.join ();
            t2.join ();
        } catch (InterruptedException e) { throw new Error (); }
    }
    static class T extends Thread {
        private Counter c;
        public T (Counter c) { this.c = c; }
        public void run () {
            for (int i=0; i<50; i++) {
                c.increment ();
            }
            X x = new X(1);
            System.out.println ("hi");
        }
    }
}
