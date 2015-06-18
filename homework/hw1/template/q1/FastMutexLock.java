// TODO 
// Implement Fast Mutex Algorithm

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class FastMutexLock implements MyLock {
    private static final Logger log = Logger.getLogger(FastMutexLock.class.getName() );
    private volatile int X;
    private volatile int Y;
    private AtomicBoolean[] flag; //flag: array[1..n] of {down, up};

    public FastMutexLock(int numThread) {
        // TODO: initialize your algorithm
        this.X = -1;
        this.Y = -1;
        flag = new AtomicBoolean[numThread];
        for (int i = 0; i < numThread; i++) {
            flag[i] = new AtomicBoolean();
        }
    }

    @Override
    public void lock(int myId) {
        // TODO: the locking algorithm
        while (true){
            flag[myId].set(true);      // raise flag
            X = myId;
            if (Y != -1) {             // splitter’s left
                flag[myId].set(false); //lower flag
                while(Y != -1);        //# waitUntil(Y == -1)
                continue;
            }
            else {
                Y = myId;
                if (X == myId) {// success with splitter
                    return; // fast path
                }
                else { // splitter’s right
                    flag[myId].set(false);
                    // forall j:
                    for (int j = 0; j < flag.length; j++){
                        while (flag[j].get() != true) ; //# waitUntil(flag[j] == down);
                    }
                    if (Y == myId){
                        return;  // slow path
                    }
                    else {
                        while(Y != -1);     //# waitUntil(Y == -1)
                        continue;
                    }
                }
            }
        }
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
        Y = -1;
        flag[myId].set(false);
    }
}
