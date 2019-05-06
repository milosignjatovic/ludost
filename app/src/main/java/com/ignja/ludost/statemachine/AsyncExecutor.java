package com.ignja.ludost.statemachine;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by milos on 5/6/19.
 */
public class AsyncExecutor implements Executor {

    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void execute(@NonNull Runnable task) {
        executor.execute(task);
    }
}
