package com.xxl.job.admin.scheduler.openapi;

import com.xxl.job.admin.scheduler.config.XxlJobAdminBootstrap;
import com.xxl.job.core.constant.Const;
import com.xxl.job.core.openapi.AdminBiz;
import com.xxl.job.core.openapi.model.*;
import com.xxl.sso.core.annotation.XxlSso;
import com.xxl.tool.core.StringTool;
import com.xxl.tool.json.GsonTool;
import com.xxl.tool.response.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xuxueli on 17/5/10.
 */
@Controller
public class OpenApiController {

    @Resource
    private AdminBiz adminBiz;

    /**
     * api
     */
    @RequestMapping("/api/{uri}")
    @ResponseBody
    @XxlSso(login = false)
    public Object api(HttpServletRequest request,
                      @PathVariable("uri") String uri,
                      @RequestHeader(value = Const.XXL_JOB_ACCESS_TOKEN, required = false) String accesstoken,
                      @RequestBody(required = false) String requestBody) {

        // valid
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return Response.ofFail("invalid request, HttpMethod not support.");
        }
        if (StringTool.isBlank(uri)) {
            return Response.ofFail("invalid request, uri-mapping empty.");
        }
        if (StringTool.isBlank(requestBody)) {
            return Response.ofFail("invalid request, requestBody empty.");
        }

        // valid token
        if (StringTool.isNotBlank(XxlJobAdminBootstrap.getInstance().getAccessToken())
                && !XxlJobAdminBootstrap.getInstance().getAccessToken().equals(accesstoken)) {
            return Response.ofFail("The access token is wrong.");
        }

        // dispatch request
        try {
            switch (uri) {
                case "callback": {
                    List<CallbackRequest> callbackParamList = GsonTool.fromJson(requestBody, List.class, CallbackRequest.class);
                    return adminBiz.callback(callbackParamList);
                }
                case "registry": {
                    RegistryRequest registryParam = GsonTool.fromJson(requestBody, RegistryRequest.class);
                    return adminBiz.registry(registryParam);
                }
                case "registryRemove": {
                    RegistryRequest registryParam = GsonTool.fromJson(requestBody, RegistryRequest.class);
                    return adminBiz.registryRemove(registryParam);
                }
                case "jobAdd": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobAdd(jobInfoRequest);
                }
                case "jobUpdate": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobUpdate(jobInfoRequest);
                }
                case "jobDelete": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobDelete(jobInfoRequest);
                }
                case "jobQuery": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobQuery(jobInfoRequest);
                }
                case "jobStart": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobStart(jobInfoRequest);
                }
                case "jobStop": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobStop(jobInfoRequest);
                }
                case "jobTrigger": {
                    JobInfoRequest jobInfoRequest = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    return adminBiz.jobTrigger(jobInfoRequest);
                }
                case "logQuery": {
                    LogRequest logRequest = GsonTool.fromJson(requestBody, LogRequest.class);
                    return adminBiz.logQuery(logRequest);
                }
                case "logKill": {
                    LogRequest logRequest = GsonTool.fromJson(requestBody, LogRequest.class);
                    return adminBiz.logKill(logRequest);
                }
                case "groupQuery": {
                    GroupRequest groupRequest = GsonTool.fromJson(requestBody, GroupRequest.class);
                    return adminBiz.groupQuery(groupRequest);
                }
                default:
                    return Response.ofFail("invalid request, uri-mapping(" + uri + ") not found.");
            }
        } catch (Exception e) {
            return Response.ofFail("openapi invoke error: " + e.getMessage());
        }

    }

}
