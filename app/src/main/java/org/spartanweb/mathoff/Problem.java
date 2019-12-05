package org.spartanweb.mathoff;

import java.util.Random;

public class Problem {

    private Random rand;
    private String problem;
    private int value;
    private int ans;

    public Problem() {
        rand = new Random();
        generate();
    }

    public Problem(Random random) {
        rand = random;
        generate();
    }

    private void generate() {
        int op = rand.nextInt(4);
        switch(op) {
            case 0: {
                int a = rand.nextInt(20) + 1;
                int b = rand.nextInt(21);
                ans = a + b;
                value = ans / 3;
                problem = a + " + " + b;
                break;
            }
            case 1: {
                int a = rand.nextInt(40) + 1;
                int b = rand.nextInt(30);
                ans = a - b;
                value = a / 3 + b / 3;
                problem = a + " - " + b;
                break;
            }
            case 2: {
                int a = rand.nextInt(15) - 5;
                int b = rand.nextInt(11);
                ans = a * b;
                value = (a + 5) / 3 + b + 10;
                problem = a + " * " + b;
                break;
            }
            case 3: {
                int b = rand.nextInt(9) + 1;
                ans = rand.nextInt(10) - 5;
                if(ans == 0) { ans = 5; }
                int a = ans * b;
                value = b * 2 + 5;
                problem = a + " / " + b;
                break;
            }
        }
    }

    public String getProblem() {
        return problem;
    }

    public int getValue() {
        return value;
    }

    public int getAnswer() {
        return ans;
    }

}
