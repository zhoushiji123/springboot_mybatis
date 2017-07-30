package com.zsj.nettys;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zsj on 2017/7/30.
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService serverpool = Executors.newFixedThreadPool(1);
        serverpool.execute(new TcpServer());

        ScheduledExecutorService clientpool = Executors.newScheduledThreadPool(1);
        clientpool.scheduleWithFixedDelay(new TcpClient(),0,2, TimeUnit.SECONDS);
    }
}
