// TODO
// Use MyLock to protect the count
// This counter can use either BakeryLock or FastMutexLock to protect the count

import java.util.logging.Level;
import java.util.logging.Logger;

public class LockCounter extends Counter {
    private static final Logger log = Logger.getLogger(LockCounter.class.getName() );
    public MyLock lock;
    public LockCounter(MyLock lock) {
        this.lock = lock;
    }

    @Override
    public void increment() {
        counterThread curThread = (counterThread) counterThread.currentThread();
        int myId = curThread.getMyId();
        this.lock.lock(myId);
        log.log(Level.FINE,(String.format("[count-] %10d\t[myId] %d",this.count, myId)));
        this.count++;
        log.log(Level.FINE,(String.format("[count+] %10d\t[myId] %d",this.count, myId)));
        this.lock.unlock(myId);
        return;
    }
}
