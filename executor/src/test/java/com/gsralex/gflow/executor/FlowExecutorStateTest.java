package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.entity.FlowJob;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author gsralex
 * @date 2020/4/5
 */
public class FlowExecutorStateTest {

    @Test
    public void checkCircularDependency() {

        List<FlowJob> jobs = new ArrayList<>();
        jobs.add(new FlowJob().setId(1L).setNextJobs("2,5"));
        jobs.add(new FlowJob().setId(2L).setPreJobs("1").setNextJobs("3"));
        jobs.add(new FlowJob().setId(3L).setPreJobs("2").setNextJobs("4"));
        jobs.add(new FlowJob().setId(4L).setPreJobs("3").setNextJobs("5"));
        jobs.add(new FlowJob().setId(5L).setPreJobs("4"));
        FlowExecutorState state = new FlowExecutorState(1L, jobs, null);
        Assert.assertFalse(state.checkCircularDependency());



        List<FlowJob> circularJobs = new ArrayList<>();
        circularJobs.add(new FlowJob().setId(1L).setNextJobs("2,5,6"));
        circularJobs.add(new FlowJob().setId(2L).setPreJobs("1").setNextJobs("3"));
        circularJobs.add(new FlowJob().setId(3L).setPreJobs("2").setNextJobs("4"));
        circularJobs.add(new FlowJob().setId(4L).setPreJobs("3").setNextJobs("5"));
        circularJobs.add(new FlowJob().setId(5L).setPreJobs("4").setNextJobs("1"));
        circularJobs.add(new FlowJob().setId(6L).setPreJobs("1"));
        FlowExecutorState circularState = new FlowExecutorState(1L, circularJobs, null);
        Assert.assertTrue(circularState.checkCircularDependency());
    }
}