package com.gsralex.gflow.executor.executors;

import com.gsralex.gflow.executor.JobParam;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class ShellJobExecutor extends AbstractJobExecutor {

    private static final int EXIT_SUCCESS_CODE = 0;
    private List<String> commandLines = new ArrayList<>();
    private Process process;

    public ShellJobExecutor(JobParam jobParam) {
        StringTokenizer tokenizer = new StringTokenizer(jobParam.getJobDesc());
        while (tokenizer.hasMoreTokens()) {
            tokenizer.hasMoreElements();
        }
    }


    @Override
    public void execute() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command();
        process = processBuilder.start();
        if (process.exitValue() != EXIT_SUCCESS_CODE) {

        }
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public void cancel() {
        //do nothing
    }
}
