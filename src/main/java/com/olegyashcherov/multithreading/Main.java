package com.olegyashcherov.multithreading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final String fileName = "arrayInt.txt";
//        Task task = new Task(fileName);
        try {
            new Task(fileName).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class Task {

    private List<Integer> integerList;
    private int amountLine;
    private int counter;

    public Task(String fileName) {
        integerList = getFileContent(fileName);
        amountLine = integerList.size();
        counter = 0;
    }

    private List<Integer> getFileContent(String fileName) {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Integer.valueOf(line));
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return list;
    }

    private synchronized void doWork(String threadName) {
        if (counter < amountLine) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(threadName + ": counter " + counter);
            System.out.println("Factorial for " + integerList.get(counter) + " is " + getFactorial(integerList.get(counter)));
            counter++;
        }
    }

    public void start() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counter < amountLine) {
                    doWork("Thread1");
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counter < amountLine) {
                    doWork("Thread2");
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Task finished");

    }

//    public long getFactorial(int f) {
//        if (f <= 1) {
//            return 1;
//        }
//        else {
//            return f * getFactorial(f - 1);
//        }
//    }

//    public long getFactorial(int f) {
//        int result = 1;
//        for (int i = 1; i <= f; i++) {
//            result = result * i;
//        }
//        return result;
//    }

//    private BigInteger getFactorial(int f) {
//        if (f < 2) {
//            return BigInteger.valueOf(1);
//        }
//        else {
//            return IntStream.rangeClosed(2, f).mapToObj(BigInteger::valueOf).reduce(BigInteger::multiply).get();
//        }
//    }

    public BigInteger getFactorial(int f) {
        if (f <= 1) {
            return BigInteger.valueOf(1);
        }
        else {
            return BigInteger.valueOf(f).multiply(getFactorial(f - 1));
        }
    }

//    public static BigInteger getFactorial(int f) {
//        BigInteger result = BigInteger.ONE;
//        for (int i = 1; i <= f; i++)
//            result = result.multiply(BigInteger.valueOf(i));
//        return result;
//    }
}
