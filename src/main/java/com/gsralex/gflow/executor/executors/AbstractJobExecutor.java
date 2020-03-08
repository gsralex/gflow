package com.gsralex.gflow.executor.executors;

import java.io.IOException;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class AbstractJobExecutor implements JobExecutor {



    @Override
    public void execute() throws Exception {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public void cancel() {

    }
}
