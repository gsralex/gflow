package com.gsralex.gflow.entity;

import java.util.Date;

/**
 * @author gsralex
 * @date 2020/2/27
 */
public class Flow {
    private Long id;
    private String name;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public Flow setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Flow setName(String name) {
        this.name = name;
        return this;
    }

    public Long getVersionId() {
        return versionId;
    }

    public Flow setVersionId(Long versionId) {
        this.versionId = versionId;
        return this;
    }
}
