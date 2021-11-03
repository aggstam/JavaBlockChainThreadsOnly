// -------------------------------------------------------------
//
// This class creates as threads are required and executes
// the retrieved Runnable for each one.
//
// Author: Aggelos Stamatiou, November 2019
//
// --------------------------------------------------------------

package com.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Workers {

    public static void work(Runnable task, int workersCount) throws InterruptedException {
        // Create and start Workers.
        Thread[] workers = new Thread[workersCount];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(task, String.valueOf(i));
            workers[i].start();
        }
        // Main thread waits Workers completion.
        for (int i = 0; i < workers.length; i++) {
            workers[i].join();
        }
    }

    // Block chain is divided to threadsCount parts and each thread retrieves one.
    public static List<ProductBlock> retrieveWorkerList(int threadCount, int workerId, List<ProductBlock> workList) {
        int interval = workList.size() / threadCount;
        int remainder = workList.size() % threadCount;
        if (interval != 0) {
            // Each thread retrieves an interval sized sublist of the Block Chain.
            List<ProductBlock> workerList = new ArrayList<>(workList.subList(workerId * interval, workerId * interval + interval));
            // If remainder exists, the first remainder number threads get one more Block.
            if (workerId < remainder) workerList.add(workList.get(workList.size()-(workerId + 1)));
            return workerList;
        } else {
            List<ProductBlock> workerList = new ArrayList<>();
            if (workerId < remainder) workerList.add(workList.get(workerId));
            return workerList;
        }
    }
}
