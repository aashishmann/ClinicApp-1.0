package com.springapp.mvc.utils;

/**
 * Created by aashish on 5/1/17.
 */
public class Test1 {

        public static void main(String[] args) {

            System.out.println("Thread Main");

            PrintNumberAndCharacters pnc = new PrintNumberAndCharacters();
            Thread t1 = new Thread(new PrintNumberFactory(10,true,pnc,1));
            Thread t2 = new Thread(new PrintNumberFactory(10,false,pnc,2));
            Thread t3 = new Thread(new PrintNumberFactory(10,null,pnc,3));

            t1.setName("thread1");
            t2.setName("thread2");
            t3.setName("thread3");
            t1.start();
            t2.start();
            t3.start();

            System.out.println("done.!!");
        }


    public static class PrintNumberFactory implements Runnable {

        Boolean isOdd;
        int max;
        PrintNumberAndCharacters pnc;
        int i;

        PrintNumberFactory(int max, Boolean isOdd, PrintNumberAndCharacters pnc, int i) {
            this.isOdd = isOdd;
            this.max = max;
            this.pnc = pnc;
            this.i = i;
        }

        @Override
        public void run() {

            int number = i == 1 ? 1 : 2;
            while (number < max) {
                if (i == 1) {
                    pnc.printOdd(number);
                    number += 2;
                } else if (i == 3)
                    pnc.printCharacter();
                else {
                    pnc.printEven(number);
                    number += 2;
                }
            }
        }
    }

    public static class PrintNumberAndCharacters {

        int i = 65;
        int position = 1;
        boolean oddFlag = false;

        public synchronized  void printCharacter() {
            //synchronized (this) {
                //System.out.println(Thread.currentThread());
                if (position % 2 != 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                System.out.println((char) i);
                i++;
                position++;
                notifyAll();
            }
        }

        public synchronized void printEven(int number) {

            //System.out.println(Thread.currentThread());
            if (position % 2 == 0 || oddFlag == false) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (position % 2 == 1 && oddFlag == true) {
                System.out.println(number);
                position++;
                oddFlag = false;
                notifyAll();
            }
        }


        public synchronized void printOdd(int number) {

            //System.out.println(Thread.currentThread());
            if (position % 2 == 0 || oddFlag == true) {
                try {
                    wait();
                }
                catch (InterruptedException e) {
                }

                }
            if (position % 2 == 1 && oddFlag == false) {
                System.out.println(number);
                position++;
                oddFlag = true;
                notifyAll();
            }
        }
    }
}
