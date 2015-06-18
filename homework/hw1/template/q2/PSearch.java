import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.*;
import java.util.logging.*;

public class PSearch implements Callable<Integer> {
  private static final Logger log = Logger.getLogger(PSearch.class.getName());
  // TODO: Declare variables
  int x;
  int[] A;
  int begin;
  int end;
  public PSearch(int x, int[] A, int begin, int end) {
    // TODO: The constructor for PSearch 
    // x: the target that you want to search
    // A: the array that you want to search for the target
    // begin: the beginning index (inclusive)
    // end: the ending index (exclusive)
    this.x = x;
    this.A = A;
    this.begin = begin;
    this.end = end;
  }

  public Integer call() throws Exception {
    // TODO: your algorithm needs to use this method to get results
    // You should search for x in A within begin and end
    // Return -1 if no such target

    log.log(Level.INFO,String.format(
      "tid[%d]	x[%d]	begin[%d]	end[%d]	%d	%d", Thread.currentThread().getId(),x,begin, end, A[begin], A[end])
    );
//    List<Integer> list = Arrays.asList(A);
//    List<Integer> arraySplice = Arrays.asList((Integer)A).subList(begin,end);
//    arraySplice.toString()
//    log.log(Level.INFO,String.format(
//      "array%s", Arrays.toString(arraySplice))
//    );
    //loop through array from begin to end, return index if match
    int index = -1;
    for (int i = begin; i <= end; i++) {
      if (A[i] == x) {
        index = i;
        log.log(Level.INFO,String.format(
          "tid[%d]	x[%d]	A[%d]	end[%d]	%d	%d", Thread.currentThread().getId(),x,index, end, A[begin], A[end])
        );
      }
    }
    return Integer.valueOf(index);
  }

  //TODO: implement simple search first
  //TODO: implement threadable search
  public static int parallelSearch(int x, int[] A, int n) {
    // TODO: your search algorithm goes here
    // You should create a thread pool with n threads
    // Then you create PSearch objects and submit those objects to the thread
    // pool
    ExecutorService threadPool = Executors.newFixedThreadPool(n);
    int index =  -1; // return -1 if the target is not found;



    // get array segment sizes, inc 'i' at this increment,
    //  use 'i' as the length
    //    unless it is >A.length
    int arraySegmentSize = A.length / n;
    //TODO: TESTING ONLY!
//    for(int i = 0; i <= A.length; i+=3){
    // store callables
    List<Callable<Integer>> callablesList = new ArrayList<Callable<Integer>>();
    // store futures
    List<Future<Integer>> futuresList = new ArrayList<Future<Integer>>();
    log.log(Level.INFO,String.format(
      "tid[%d],x[%d],A.length[%d],numThreads[%d]", Thread.currentThread().getId(),x,A.length, n)
    );

    // calculate array-segment-indices based on arraySegmentSize
    // populate list of callables with PSearch objects
    for(int i = 0; i < A.length; i++) {
      int begin = i;
      int end = i + arraySegmentSize;
      if (end >= A.length) {
        end = A.length - 1;
      }
      i += arraySegmentSize; // update i
      PSearch pSearch = new PSearch(x, A, begin, end);
      //TODO: callable, future, etc
      Callable<Integer> callable = pSearch;
//      future = threadPool.submit(callable);
      callablesList.add(callable);
    }

    // submit all
    for (Callable tmpCallable : callablesList){
      futuresList.add(threadPool.submit(tmpCallable));
    }
    //TODO: implement in non-blocking fashion
//    if(true){
    for (Future tmpFuture : futuresList){
      int tmpIndex = -1;
      try {
//        index = future.get();
        tmpIndex = (int) tmpFuture.get();
        if(tmpIndex > index){
          index = tmpIndex;
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }

    threadPool.shutdown();


    return index;
  }


}
