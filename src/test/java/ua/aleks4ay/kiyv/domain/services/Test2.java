package ua.aleks4ay.kiyv.domain.services;

import ua.aleks4ay.kiyv.copiller_db.reader.CopyClient;

public class Test2 extends Thread{
    public static void main(String[] args) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Test2().start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Test2().start();

    }

    public void run() {
        CopyClient.getInstance().doCopyNewRecord();
    }
}
