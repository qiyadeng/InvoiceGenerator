package com.einvoicemerchant.monitor;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

import static com.einvoicemerchant.config.SystemRunner.uploadFileStatus;

/**
 * @author: Effort
 * @date: 2021-04-16
 * @time: 5:37 下午
 * @description: 文件夹监控
 */
public class FileChangeMonitor extends FileAlterationListenerAdaptor {
    private final Logger log = LoggerFactory.getLogger(FileChangeMonitor.class);

    @Override
    public void onFileCreate(File file) {
        log.info("[发票机生成开票信息完成，正在更新文件状态]");
        try {
            uploadFileStatus(file);
        } catch (DocumentException e) {
            log.error("发票机生成文件解析失败",e);
        }
    }
}