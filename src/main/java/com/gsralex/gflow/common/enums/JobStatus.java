package com.gsralex.gflow.common.enums;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public enum JobStatus {
    PENDING,
    RUNNING,
    PAUSED,
    SUCCESS,
    FAILED,
    STOPPED;

    public boolean isFinished() {
        switch (this) {
            case SUCCESS:
            case FAILED:
            case STOPPED: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
