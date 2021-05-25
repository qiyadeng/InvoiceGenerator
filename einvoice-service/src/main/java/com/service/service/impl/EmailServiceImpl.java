package com.service.service.impl;

import com.service.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Effort
 * @description 邮件发送服务实现
 * @date 2020/10/11 3:34 下午
 */
@Service
@Async
public class EmailServiceImpl implements EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender sender;

    @Override
    public Future<Boolean> sendAttachmentMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setFrom(fromEmail);
        message.setText(content);
        try {
            sender.send(message);
            logger.info("发送给：{}的邮件发送成功",to);
            return new AsyncResult<Boolean>(true);
        }catch (Exception e) {
            logger.error("发送给：{}的邮件发送失败,失败原因:{}",to,e.getMessage());
            return new AsyncResult<Boolean>(false);
        }
    }

    @Override
    public Future<Boolean> sendAttachmentMail(String to, String subject, String content, String filePath) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper  = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            if (filePath != null) {
                try {
                    FileSystemResource file = new FileSystemResource(filePath);
                    String fileName = file.getFilename();
                    helper.addAttachment(fileName, file);
                    sender.send(mimeMessage);
                    logger.info("发送给：{}的邮件发送成功",to);
                } catch (Exception e) {
                    logger.error("发送给：{}的邮件发送失败,失败原因:{}",to,e.getMessage());
                    return new AsyncResult<Boolean>(false);
                }
                return new AsyncResult<Boolean>(true);
            }else {
                //没有附件不发送
                return new AsyncResult<Boolean>(false);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            return new AsyncResult<Boolean>(false);
        }
    }
}
