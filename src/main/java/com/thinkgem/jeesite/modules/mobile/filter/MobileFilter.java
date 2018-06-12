package com.thinkgem.jeesite.modules.mobile.filter;


import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.mobile.entity.SysToken;
import com.thinkgem.jeesite.modules.mobile.service.DmUserService;
import com.thinkgem.jeesite.modules.mobile.service.SysTokenService;
import com.thinkgem.jeesite.modules.mobile.utils.MobileUtils;
import com.thinkgem.jeesite.modules.mobile.utils.MobileResult;


import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/*
移动token过滤器
 */
public class MobileFilter implements Filter {


    private SysTokenService sysTokenService;

    private DmUserService dmUserService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);

        if(cxt != null && cxt.getBean("sysTokenService") != null && sysTokenService == null)
            sysTokenService = (SysTokenService) cxt.getBean("sysTokenService");

        if(cxt != null && cxt.getBean("dmUserService") != null && dmUserService == null)
            dmUserService = (DmUserService) cxt.getBean("dmUserService");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        MobileResult mobileResult = new MobileResult();
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        if(req.getRequestURI().endsWith("/mobile/login") || req.getRequestURI().endsWith("/mobile/register")|| req.getRequestURI().endsWith("/mobile/getLoginCode")){
            filterChain.doFilter(req, resp);
            return;
        }
        String token=req.getHeader("token");
        SysToken sysToken = sysTokenService.getSysTokenByToken(token);
        if(sysToken!=null){
            SysToken  sysTokenNew =  sysTokenService.getSysTokenByUserId(sysToken.getDmuserId()).get(0);
            if(sysToken.getExportTime().getTime()>=sysTokenNew.getExportTime().getTime()) {
                Date data = new Date();
                if (data.getTime() < sysToken.getExportTime().getTime()) {
                    SysToken sysTokenEe = new SysToken();
                    sysTokenEe.setToken(token);
                    sysTokenEe.setDmuserId(sysToken.getDmuserId());
                    sysTokenEe.setExportTime(DateUtils.getDataNext(MobileUtils.Export_TIME));
                    //刷新token
                    int isSuccess = sysTokenService.updateSysTokenByToken(sysTokenEe);
                    if (isSuccess == 1) {
                        servletRequest.setAttribute("dmUser", dmUserService.get(sysToken.getDmuserId()));
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    } else {
                        output(JsonMapper.toJsonString(MobileResult.error(1010, MobileUtils.STATUS_1012)), resp);
                    }

                } else {
                    sysTokenService.deleteSysTokenByToken(token);
                    output(JsonMapper.toJsonString(MobileResult.error(1010, MobileUtils.STATUS_1011)), resp);
                }
            }else{
                sysTokenService.deleteSysTokenByToken(token);
                output(JsonMapper.toJsonString(MobileResult.error(1020, MobileUtils.STATUS_1020)), resp);
            }
        }else{
            output(JsonMapper.toJsonString(MobileResult.error(1010,MobileUtils.STATUS_1010)), resp);
        }
    }

    @Override
    public void destroy() {

    }

    public void output(String jsonStr,HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();

    }
}
