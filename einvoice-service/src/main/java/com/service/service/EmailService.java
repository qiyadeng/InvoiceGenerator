package com.service.service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Effort
 * @description 邮件服务
 * @date 2020/10/11 3:33 下午
 */
public interface EmailService {
    /**
     * 发送简单文本邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return boolean 成功返回true，失败返回false
     */
    public Future<Boolean> sendAttachmentMail(String to, String subject, String content);
    /**
     * 发送带单个或多个附件的邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param filePath 包含附件路径的地址的数组
     * @param content 邮件内容
     * @return boolean 成功返回true，失败返回false
     */
    public Future<Boolean> sendAttachmentMail(String to, String subject, String content,String filePath);
}
