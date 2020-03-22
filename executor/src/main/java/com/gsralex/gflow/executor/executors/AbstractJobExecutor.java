package com.gsralex.gflow.executor.executors;


import com.gsralex.gflow.executor.executors.JobExecutor;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public abstract class AbstractJobExecutor implements JobExecutor {


    @Override
    public abstract void execute() throws Exception;

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public abstract void cancel();
}
