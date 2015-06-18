
/**
 * Created by Lee on 6/17/2015.
 */
public class Main {
    public static void main(String[] args){
      //TODO: convert to junit tests
      // int a[] = {1,2,3,4,5,6,7,8,9,10};      // even-number:PASS
      // int a[] = {0,1,2,3,4,5,6,7,8,9,10,11}; // even-number:PASS
//      int a[] = {0,1,2,3,4,5,6,7,8,9,10}; // odd-number:PASS
      int a[] = {0,2,3,3,4,5,1,6,2,7,9,8,9,10}; // odd-number:FAIL

      try {
        // int numThreads = 1;
        int numThreads = 6; // leads to smallest slice number
        // numThreads = 3;
        int testStart = 5; // start somewhere...
        int testStop = testStart;
        if(true){
          // initialise with cornercases
          testStart = -1;
          testStop = a.length + 2;
        }
        for(int i = testStart; i <= testStop; i++) {
          int answer = PSearch.parallelSearch(i, a, numThreads);
          System.out.println(String.format("index[%d],target[%d]",answer, i));
        }
      }
      catch (Exception e) { System.err.println(e);}

    }
}
