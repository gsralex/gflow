package com.gsralex.gflow.executor.executors;

import com.gsralex.gflow.common.exception.CommonException;
import com.gsralex.gflow.executor.ExecuteNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class ShellJobExecutor implements JobExecutor {
    private static final int EXIT_SUCCESS_CODE = 0;
    private List<String> commandLines = new ArrayList<>();
    private Process process;
    private ExecuteNode node;
    private static final String LINE_DELIM = "\n";

    public ShellJobExecutor(ExecuteNode node) {
        this.node = node;
    }


    @Override
    public void start() throws Exception {
        String[] lines = StringUtils.split(node.getJobDesc(), LINE_DELIM);
        for (String line : lines) {
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            while (tokenizer.hasMoreTokens()) {
                commandLines.add(tokenizer.nextToken());
            }
            executeProcess(commandLines);
        }
    }

    private void executeProcess(List<String> commandLines) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commandLines);
        process = processBuilder.start();
        process.waitFor();
        if (process.exitValue() != EXIT_SUCCESS_CODE) {
            throw new CommonException("Shell 执行失败");
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
