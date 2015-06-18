import java.util.concurrent.locks.ReentrantLock;

// TODO
// Use ReentrantLock to protect the count
// http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantLock.html
public class ReentrantCounter extends Counter {
  private final ReentrantLock lock = new ReentrantLock();
    @Override
    public void increment() {
        lock.lock(); // block until condition holds
        try{
            this.count++;
        } finally {
            lock.unlock();
        }
    }
}
