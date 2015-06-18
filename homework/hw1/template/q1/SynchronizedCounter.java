// TODO 
// Use synchronized to protect count
// http://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
public class SynchronizedCounter extends Counter {
    @Override
    public synchronized void increment() {
      this.count++;
    }
}
