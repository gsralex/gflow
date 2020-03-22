package com.gsralex.gflow.executor.executors;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public interface JobExecutor {

    /**
     * 执行
     */
    void execute() throws Exception;

    /**
     * 是否能取消
     * @return
     */
    boolean isCanceled();

    /**
     * 取消
     */
    void cancel();


}
