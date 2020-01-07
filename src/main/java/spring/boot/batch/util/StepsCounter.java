package spring.boot.batch.util;

public class StepsCounter {

    public int countSteps(int size, int chunk) {
        int count = size/chunk;
        if(size % chunk > 0) {
            count++;
        }
        return count;
    }
}
