package com.xxl.job.core.openapi.model;

/**
 *
 * <p></p>
 *
 * @author gengchen
 * @since 2026/10/25
 */
public class JobLogRequest {

    private long id;

    private int jobGroup;

    private int jobId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
