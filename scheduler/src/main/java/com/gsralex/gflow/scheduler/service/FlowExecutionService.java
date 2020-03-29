package com.gsralex.gflow.scheduler.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsralex.gflow.common.entity.Flow;
import com.gsralex.gflow.common.entity.FlowExecution;
import com.gsralex.gflow.common.mapper.FlowExecutionMapper;
import com.gsralex.gflow.common.mapper.FlowMapper;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author gsralex
 * @date 2020/1/29
 */
@Service
public class FlowExecutionService {

    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    private FlowExecutionMapper flowExecutionMapper;

    public FlowExecutionService() {
    }

    public Long createFlow(String name, Map<String, Object> params) {
        Flow flow = getFlowByName(name);
        Validate.notNull(flow, "未找到flow");
        FlowExecution flowExecution = new FlowExecution()
                .setFlowId(flow.getId())
                .setStartTime(new Date())
                .setVersionId(flow.getVersionId());
        flowExecutionMapper.insert(flowExecution);
        return flowExecution.getId();
    }

    private Flow getFlowByName(String name) {
        QueryWrapper<Flow> query = new QueryWrapper<>(new Flow()
                .setName(name));
        return flowMapper.selectOne(query);
    }

    public void pause(Long id) {
        FlowExecution execution = flowExecutionMapper.selectById(id);
        Validate.notNull(execution, "未找到flow execution");
        //暂停会执行完当前running的，后续不在执行
    }

    public void stop(Long id) {
        FlowExecution execution = flowExecutionMapper.selectById(id);
        Validate.notNull(execution, "未找到flow execution");
    }
}
