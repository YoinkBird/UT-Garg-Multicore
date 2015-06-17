/**
 * Created by Lee on 6/15/2015.
 */
public class counterThread extends Thread {
    int myId;
    MyLock lock;
    Counter counter;
    int incNum;
    // default is increment one
    public counterThread(int threadId, Counter counter){
        this(threadId,counter,1);
    }
    public counterThread(int threadId, Counter counter, int incNum){
        this(threadId,counter,null,1);
    }
    // input: name of thread, a counter to run, number of times to increment
    public counterThread(int threadId, Counter counter, MyLock lock, int incNum){
        // set proper name for thread
        // http://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setName-java.lang.String-
        this.setName(String.valueOf(threadId));
        this.myId = threadId;
        // define which counter to run
        this.counter = counter;
        // define which lock to use
        this.lock = lock;
        // number of times to increment
        this.incNum = incNum;
    }
    public void run(){
        for (int n = 0; n < this.incNum; n++){
            this.printInfo(
            //System.out.println("counterThread: "
                    " [myId] " + this.myId
                    + " [startingCount] " + this.counter.getCount());
            // TODO: this should all be in lockcounter
            this.counter.increment();
        }
    }
    public void printInfo(String info){
        System.out.println(this.getClass() + ":\t" + info);
        return;
    }

    public  int getMyId(){
        return this.myId;
    }
}
