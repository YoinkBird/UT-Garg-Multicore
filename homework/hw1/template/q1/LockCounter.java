// TODO
// Use MyLock to protect the count
// This counter can use either BakeryLock or FastMutexLock to protect the count

public class LockCounter extends Counter {
    public MyLock lock;
    public LockCounter(MyLock lock) {
        this.lock = lock;
    }

    @Override
    public void increment() {
        counterThread curThread = (counterThread) counterThread.currentThread();
        int myId = curThread.getMyId();
        this.lock.lock(myId);
        this.count++;
        this.printInfo( " [count] " + this.count + " [myId] " + myId);
        this.lock.unlock(myId);
        return;
    }

    public void printInfo(String info){
        boolean enable = false;
        if(enable) {
            System.out.println(this.getClass() + ":\t" + info);
        }
    }

}
