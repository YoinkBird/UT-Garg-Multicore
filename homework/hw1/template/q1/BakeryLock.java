// TODO
// Implement the bakery algorithm

import java.util.Arrays;

public class BakeryLock implements MyLock {
    int numThread;
    boolean[] choosing; // doorway: indicates that process is choosing a number
    int[] number; //TODO: Integer vs int?
    public BakeryLock(int numThread) {
        this.numThread = numThread;
        // array length matches number of threads; only works with fixed-number of threads
        choosing = new boolean[this.numThread];
        number   = new int[this.numThread];

        // default: not entering critical section. initialise arrays to false and zero, respectively
        for(int j = 0; j < this.numThread; j++){
            choosing[j] = false;
            number[j]   = 0;
        }
    }

    //request critical section
    @Override
    public void lock(int myId) {
        // step1: choose a number
        choosing[myId] = true;

        for (int j = 0; j < this.numThread; j++){
            if(this.number[j] > this.number[myId]){
                this.number[myId] = this.number[j];
            }
        }
        this.number[myId]++;
        choosing[myId] = false;

        // step2: check if my number is smallest
        // i.e. does this thread have priority
        for (int j = 0; j < this.numThread; j++){
//            this.printInfo( " [myId] " + myId + " lock wait. [choosing] " + Arrays.toString(choosing));
//            this.printInfo( " [j] " + j
//                    + " [number[myId]] " + number[myId]
//                    + " [number[j]] " + number[j]);
            while(choosing[j]); // wait if another process "j" is in the doorway
            while (
                    (number[j] != 0) &&
                    (
                        (number[j] < number[myId]) ||
                        (
                            (number[j] == number[myId]) &&
                            (j < myId)
                        )
                    )
                  ); // busy wait until conditions are satisfied
        }
        //System.out.println(this.toString()
        this.printInfo( " [myId] " + myId + " lock acquired" );
    }

    @Override
    public void unlock(int myId) {
        this.printInfo(" [myId] " + myId + " unlock" );
        // 0 indicates no longer in critical section
        this.number[myId] = 0;
    }

    public void printInfo(String info) {
        boolean enable = false;
        this.printInfo(enable, info);
    }
    public void printInfo(boolean enable, String info){
        if(enable) {
            System.out.println(this.getClass() + ":\t" + info);
        }
        return;
    }
}
