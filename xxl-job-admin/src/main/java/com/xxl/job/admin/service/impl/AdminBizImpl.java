package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.constant.Consts;
import com.xxl.job.admin.model.XxlJobGroup;
import com.xxl.job.admin.model.XxlJobInfo;
import com.xxl.job.admin.model.XxlJobLog;
import com.xxl.job.admin.scheduler.config.XxlJobAdminBootstrap;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.openapi.AdminBiz;
import com.xxl.job.core.openapi.ExecutorBiz;
import com.xxl.job.core.openapi.model.*;
import com.xxl.sso.core.model.LoginInfo;
import com.xxl.tool.response.Response;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Admin业务实现
 * <p></p>
 *
 * @author xuxueli
 * @author gengchen
 * @since 2026/5/26
 */
@Service
public class AdminBizImpl implements AdminBiz {
    private static final Logger logger = LoggerFactory.getLogger(AdminBizImpl.class);

    @Resource
    private XxlJobService xxlJobService;

    /**
     * 每次调用新建实例，避免多线程共享可变对象的风险
     */
    private static LoginInfo systemLogin() {
        List<String> roleList = new ArrayList<>();
        roleList.add(Consts.ADMIN_ROLE);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName("open_api");
        loginInfo.setRoleList(roleList);
        return loginInfo;
    }

    // ---------------------- callback / registry ----------------------

    @Override
    public Response<String> callback(List<CallbackRequest> callbackRequestList) {
        return XxlJobAdminBootstrap.getInstance().getJobCompleteHelper().callback(callbackRequestList);
    }

    @Override
    public Response<String> registry(RegistryRequest registryRequest) {
        return XxlJobAdminBootstrap.getInstance().getJobRegistryHelper().registry(registryRequest);
    }

    @Override
    public Response<String> registryRemove(RegistryRequest registryRequest) {
        return XxlJobAdminBootstrap.getInstance().getJobRegistryHelper().registryRemove(registryRequest);
    }

    // ---------------------- job CRUD ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobAdd(JobInfoRequest req) {
        if (req == null) {
            return Response.ofFail("param is null");
        }
        XxlJobInfo jobInfo = new XxlJobInfo();
        BeanUtils.copyProperties(req, jobInfo);
        return xxlJobService.add(jobInfo, systemLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobUpdate(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        XxlJobInfo jobInfo = new XxlJobInfo();
        BeanUtils.copyProperties(req, jobInfo);
        return xxlJobService.update(jobInfo, systemLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobDelete(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        return xxlJobService.remove(req.getId(), systemLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<JobInfoResult> jobQuery(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        XxlJobInfo jobInfo = XxlJobAdminBootstrap.getInstance().getXxlJobInfoMapper().loadById(req.getId());
        if (jobInfo == null) {
            return Response.ofFail("job not found");
        }
        JobInfoResult jobInfoResult = new JobInfoResult();
        BeanUtils.copyProperties(jobInfo, jobInfoResult);
        return Response.ofSuccess(jobInfoResult);
    }

    // ---------------------- job lifecycle ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobStart(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        return xxlJobService.start(req.getId(), systemLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobStop(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        return xxlJobService.stop(req.getId(), systemLogin());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> jobTrigger(JobInfoRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("job id is invalid");
        }
        return xxlJobService.trigger(systemLogin(), req.getId(), req.getExecutorParam(), req.getAddressList());
    }

    // ---------------------- log ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<LogResult> logContextQuery(LogRequest req) {
        if (req == null || req.getLogId() <= 0) {
            return Response.ofFail("logId is invalid");
        }
        try {
            XxlJobLog jobLog = XxlJobAdminBootstrap.getInstance().getXxlJobLogMapper().load(req.getLogId());
            if (jobLog == null) {
                return Response.ofFail("log not found");
            }

            ExecutorBiz executorBiz = XxlJobAdminBootstrap.getExecutorBiz(jobLog.getExecutorAddress());
            if (executorBiz == null) {
                return Response.ofFail("executor address is invalid");
            }

            long logDateTim = req.getLogDateTim() > 0
                    ? req.getLogDateTim()
                    : jobLog.getTriggerTime().getTime();

            Response<LogResult> logResult = executorBiz.log(
                    new LogRequest(logDateTim, req.getLogId(), req.getFromLineNum()));

            // 标记是否结束
            if (logResult.getData() != null
                    && logResult.getData().getFromLineNum() > logResult.getData().getToLineNum()
                    && jobLog.getHandleCode() > 0) {
                logResult.getData().setEnd(true);
            }
            return logResult;
        } catch (Exception e) {
            logger.error("logQuery error, logId={}", req.getLogId(), e);
            return Response.ofFail(e.getMessage());
        }
    }


    @Override
    public Response<JobLogResult> logQuery(JobLogRequest req) {
        if (req == null || req.getId() <= 0) {
            return Response.ofFail("logId is invalid");
        }
        XxlJobLog jobLog = XxlJobAdminBootstrap.getInstance().getXxlJobLogMapper().load(req.getId());
        if (jobLog == null) {
            return Response.ofFail("log not found");
        }
        JobLogResult jobLogResult = new JobLogResult();
        BeanUtils.copyProperties(jobLog, jobLogResult);
        return Response.ofSuccess(jobLogResult);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Response<String> logKill(LogRequest logRequest) {
        if (logRequest == null || logRequest.getLogId() <= 0) {
            return Response.ofFail("logId 无效");
        }
        try {
            // 1、加载日志记录
            XxlJobLog jobLog = XxlJobAdminBootstrap.getInstance().getXxlJobLogMapper().load(logRequest.getLogId());
            if (jobLog == null) {
                return Response.ofFail("日志记录不存在");
            }

            // 2、加载任务信息
            XxlJobInfo jobInfo = XxlJobAdminBootstrap.getInstance().getXxlJobInfoMapper().loadById(jobLog.getJobId());
            if (jobInfo == null) {
                return Response.ofFail("任务不存在");
            }

            // 3、只有触发成功才可以 kill（触发失败的任务根本没有在执行器上运行）
            if (XxlJobContext.HANDLE_CODE_SUCCESS != jobLog.getTriggerCode()) {
                return Response.ofFail("触发未成功，无法终止");
            }

            // 4、向执行器发送 kill 指令
            ExecutorBiz executorBiz = XxlJobAdminBootstrap.getExecutorBiz(jobLog.getExecutorAddress());
            if (executorBiz == null) {
                return Response.ofFail("执行器地址无效");
            }
            Response<String> killResult = executorBiz.kill(new KillRequest(jobInfo.getId()));

            // 5、kill 成功后更新日志状态，标记为人工终止
            if (XxlJobContext.HANDLE_CODE_SUCCESS == killResult.getCode()) {
                jobLog.setHandleCode(XxlJobContext.HANDLE_CODE_FAIL);
                jobLog.setHandleMsg("人工终止：" + (killResult.getMsg() != null ? killResult.getMsg() : ""));
                jobLog.setHandleTime(new java.util.Date());
                XxlJobAdminBootstrap.getInstance().getJobCompleter().complete(jobLog);
                return Response.ofSuccess(killResult.getMsg());
            } else {
                return Response.ofFail(killResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("logKill error, logId={}", logRequest.getLogId(), e);
            return Response.ofFail(e.getMessage());
        }
    }

    // ---------------------- group ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<GroupResult> groupQuery(GroupRequest groupRequest) {
        if (groupRequest == null) {
            return Response.ofFail("param is null");
        }
        GroupResult groupResult = new GroupResult();
        // 优先按 id 精确查
        if (groupRequest.getId() > 0) {
            XxlJobGroup group = XxlJobAdminBootstrap.getInstance().getXxlJobGroupMapper().load(groupRequest.getId());
            if (group == null) {
                return Response.ofFail("group not found");
            }
            BeanUtils.copyProperties(group, groupResult);
            return Response.ofSuccess(groupResult);
        }
        XxlJobGroup group = XxlJobAdminBootstrap.getInstance().getXxlJobGroupMapper().loadByAppName(groupRequest.getAppname());
        if (group == null) {
            return Response.ofFail("group not found");
        }
        BeanUtils.copyProperties(group, groupResult);
        return Response.ofSuccess(groupResult);
    }


}
