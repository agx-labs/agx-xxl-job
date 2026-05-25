package com.xxl.job.core.openapi;

import com.xxl.job.core.openapi.model.*;
import com.xxl.tool.response.Response;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminBiz {


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackRequestList
     * @return
     */
    public Response<String> callback(List<CallbackRequest> callbackRequestList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryRequest
     * @return
     */
    public Response<String> registry(RegistryRequest registryRequest);

    /**
     * registry remove
     *
     * @param registryRequest
     * @return
     */
    public Response<String> registryRemove(RegistryRequest registryRequest);


    // ---------------------- job operate ----------------------

    // jobAdd
    public Response<String> jobAdd(JobInfoRequest request);

    // jobUpdate
    public Response<String> jobUpdate(JobInfoRequest request);

    // jobDelete
    public Response<String> jobDelete(JobInfoRequest request);

    // jobQuery
    public Response<JobInfoResult> jobQuery(JobInfoRequest request);

    // jobStart
    public Response<String> jobStart(JobInfoRequest request);

    // jobStop
    public Response<String> jobStop(JobInfoRequest request);

    // jobTrigger
    public Response<String> jobTrigger(JobInfoRequest request);

    // ---------------------- job log operate ----------------------

    // logQuery
    public Response<LogResult> logQuery(LogRequest request);

    public Response<String> logKill(LogRequest request);

    // ---------------------- job group operate ----------------------
    public Response<GroupResult> groupQuery(GroupRequest request);

}
