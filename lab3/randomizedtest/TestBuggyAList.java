package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correctList = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();
        int a;
        int b;

        for (int i = 0; i < 3; i++) {
            correctList.addLast(i);
            buggyList.addLast(i);
        }

        for (int i = 0; i < 3; i++) {
            a = correctList.removeLast();
            b = buggyList.removeLast();
            assertEquals(a, b);
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyList.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size1 = L.size();
                int size2 = buggyList.size();
                assertEquals(size1, size2);
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    int last1 = L.getLast();
                    int last2 = buggyList.getLast();
                    assertEquals(last1, last2);
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    int last1 = L.removeLast();
                    int last2 = buggyList.removeLast();
                    assertEquals(last1, last2);
                }
            }
        }

    }
}
