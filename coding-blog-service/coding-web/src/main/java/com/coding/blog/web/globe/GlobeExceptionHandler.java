package com.coding.blog.web.globe;

import com.coding.blog.common.eception.ForumAdviceException;
import com.coding.blog.common.util.EmailUtil;
import com.coding.blog.service.vo.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/10 18:58
 * @className com.coding.blog.web.globe.GlobeExceptionHandler
 */
@RestControllerAdvice
public class GlobeExceptionHandler {

    @Value("${alarm.user}")
    private String to;
    @Value("${spring.mail.from}")
    private String from;

    @ExceptionHandler(value = ForumAdviceException.class)
    public ResultObject<String> resolveException(ForumAdviceException e) {
        return ResultObject.failed(e.getStatus().getMsg());
    }

    @ExceptionHandler(value = Exception.class)
    public void catchExceptionSendMsg(Exception e) {
        // 记录异常时间
        ExceptionLogger.exceptions.add(LocalDateTime.now());
        ExceptionLogger.exceptionMessages.add(transformExceptionMsg(e));

        // 移除超过一小时的异常记录
        ExceptionLogger.exceptions.removeIf(time -> time.isBefore(LocalDateTime.now().minusHours(1)));
        ExceptionLogger.exceptionMessages.removeIf(msg -> ExceptionLogger.exceptions.get(ExceptionLogger.exceptionMessages.indexOf(msg)).isBefore(LocalDateTime.now().minusHours(1)));

        // 检查最近一小时内的异常次数
        if (ExceptionLogger.exceptions.size() >= 3) {
            // 调用邮件发送服务
            // emailService.sendEmail("your-email@example.com", "一小时内连续出现三次异常", e.getMessage());
            EmailUtil.sendMail(from,"连续异常请处理！",to,buildExceptionMsg(ExceptionLogger.exceptionMessages));
            // 清空异常记录
            ExceptionLogger.exceptions.clear();
            ExceptionLogger.exceptionMessages.clear();
        }
    }

    private String buildExceptionMsg(List<String> list){
        StringBuilder builder = new StringBuilder();
        for (String msg : list) {
            builder.append(LocalDateTime.now() + "\t" + msg);
            builder.append("<br>");
            builder.append("<br>");
            builder.append("<br>");
            builder.append("<br>");
        }
        return builder.toString();
    }

    private String transformExceptionMsg(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
