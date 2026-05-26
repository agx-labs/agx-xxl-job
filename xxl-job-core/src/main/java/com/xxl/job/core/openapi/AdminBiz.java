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

    /**
     * 新增任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobAdd(JobInfoRequest request);

    /**
     * 更新任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobUpdate(JobInfoRequest request);

    /**
     * 删除任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobDelete(JobInfoRequest request);

    /**
     * 查询任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<JobInfoResult> jobQuery(JobInfoRequest request);

    /**
     * 开始任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobStart(JobInfoRequest request);

    /**
     * 停止任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobStop(JobInfoRequest request);

    /**
     * 触发任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> jobTrigger(JobInfoRequest request);

    // ---------------------- job log operate ----------------------

    /**
     * 日志查询
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<LogResult> logQuery(LogRequest request);

    /**
     * 杀死任务
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<String> logKill(LogRequest request);

    // ---------------------- job group operate ----------------------

    /**
     * 执行器查询
     * <p></p>
     *
     * @param request 入参 {@link JobInfoRequest}
     * @return 出参 {@link Response}
     * @author gengchen
     * @since 2026/5/26
     */
    public Response<GroupResult> groupQuery(GroupRequest request);

}
