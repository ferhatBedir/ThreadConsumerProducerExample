package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConsumerProducer {

    private Random random = new Random();
    private List<Integer> store = new ArrayList<>(10);
    private Object key1 = new Object();
    private Object key2 = new Object();


    Thread producer = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                addValuseToStore();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    Thread consumer = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                removeValueFromStore();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    private void addValuseToStore() throws InterruptedException {
        synchronized (key1) {
            while (true) {
                if (store.size() < 10) {
                    System.out.println("Depoya ürün ekleniyor");
                    Thread.sleep(750);
                    store.add(0, random.nextInt(10));
                    System.out.println(store.get(0) + " depoya eklendi. Depo Boyutu --> " + store.size() + " 'e ulaştı..");
                } else {
                    System.out.println("Depo kapasiesi maksimuma erişti 4 saniye beklenecek.");
                    Thread.sleep(4000);
                }

            }
        }
    }

    private void removeValueFromStore() throws InterruptedException {
        synchronized (key2) {
            Thread.sleep(4000);
            while (true) {
                if (store.size() > 0) {
                    System.out.println("Depodan ürün çıkartılıyor.");
                    Thread.sleep(750);
                    store.remove(0);
                    System.out.println("Depodan ürün çıkarma işlemi tamamlandı. Güncel Depo boyutu --> " + store.size() + " 'dır.");
                } else if (store.size() == 0) {
                    System.out.println("Depoda ürün kalmadı 5 saniye bekleniyor..");
                    Thread.sleep(5000);
                }
            }
        }
    }

    public void runThread() throws InterruptedException {
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }


}
