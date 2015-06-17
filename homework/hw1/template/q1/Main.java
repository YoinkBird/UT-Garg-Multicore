import java.util.concurrent.*;

public class Main {
    public static void main (String[] args) {
        Counter counter = null;
        MyLock lock = null;
        long executeTimeMS = 0;
        int numThread = 6;
        int numTotalInc = 1200000;

        if (args.length < 3) {
            System.err.println("Provide 3 arguments");
            System.err.println("\t(1) <algorithm>: fast/bakery/synchronized/"
                    + "reentrant");
            System.err.println("\t(2) <numThread>: the number of test thread");
            System.err.println("\t(3) <numTotalInc>: the total number of "
                    + "increment operations performed");
            System.exit(-1);
        }

        if (args[0].equals("fast")) {
            lock = new FastMutexLock(numThread);
            counter = new LockCounter(lock);
        } else if (args[0].equals("bakery")) {
            lock = new BakeryLock(numThread);
            counter = new LockCounter(lock);
        } else if (args[0].equals("synchronized")) {
            counter = new SynchronizedCounter();
        } else if (args[0].equals("reentrant")) {
            counter = new ReentrantCounter();
        } else {
            System.err.println("ERROR: no such algorithm implemented");
            System.exit(-1);
        }

        numThread = Integer.parseInt(args[1]);
        numTotalInc = Integer.parseInt(args[2]);

        // TODO
        // Please create numThread threads to increment the counter
        // Each thread executes numTotalInc/numThread increments
        // Please calculate the total execute time in millisecond and store the
        // result in executeTimeMS

        // use the following but with 'fixed' threadpool
        // static ExecutorService exec = Executors.newCachedThreadPool();
        //ExecutorService exec = Executors.newFixedThreadPool(numThread);

        counterThread[] threads = new counterThread[numThread];
        int threadPid = 0;

        long startTime = System.nanoTime();

        System.out.println("main: [numThreads]" + numThread + "[numTotalInc]" + numTotalInc);

        // create threads
        for(int i = 0; i < threads.length; i++){
            threads[i] = new counterThread(threadPid++, counter, (numTotalInc / numThread) );
        }

        // batch submit all threads
        for (counterThread myThread : threads){
            myThread.start();
        }

        // batch wait for threads
        for (counterThread myThread : threads){
            try {
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        executeTimeMS =  endTime - startTime;



        // all threads finish incrementing
        // Checking if the result is correct
        if (counter == null ||
            counter.getCount() != (numTotalInc/numThread) * numThread) {
          System.err.println("Error: The counter is not equal to the number "
              + "of total increment");
          System.err.println(
                  String.format("[counter] %d [numTotalInc] %d", counter.getCount(), numTotalInc)
          );
            System.err.println(
                    String.format("counter.getCount() != (numTotalInc/numThread) * numThread")
                    + String.format("\n%d != (%d/%d) * %d", counter.getCount(), numTotalInc, numThread , numThread)
                    + String.format("\n%d != %d", counter.getCount(), (numTotalInc/numThread * numThread))
                );
        } else {
          // print total execute time if the result is correct
          System.out.println(executeTimeMS);
        }
    }
}
