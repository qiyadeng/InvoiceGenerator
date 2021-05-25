package com.einvoicemerchant.schedule;
import com.einvoicemerchant.config.SysConfig;
import com.einvoicemerchant.config.SystemRunner;
import com.einvoicemerchant.utils.net.NetUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Effort
 * @description 发票定时任务
 * @date 2020/9/21 8:18 下午
 */
@Component
@EnableScheduling
public class InvoiceSchedule {
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private SystemRunner runner;

    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceSchedule.class);

    /**
     * 拉取发票xml信息
     */
    @Scheduled(initialDelay=2000,fixedDelay= 5000)
    public void pushVoiceMessage() {
        String token = runner.token;
        //未登陆
        if(token == null) {
            return;
        }

        JsonObject jsonObject1 = NetUtils.httpGet(token, sysConfig.getPullInvoiceUrl(),null);
        JsonArray jsonArray = jsonObject1.getAsJsonArray("data");
        if(null != jsonArray && jsonArray.size() != 0) {
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                JsonObject head = obj.getAsJsonObject("head");
                try {
                    String urlStr = obj.get("url").getAsString();
                    String fpqqlsh = head.get("fpqqlsh").getAsString();
                    File file = new File(sysConfig.getInvoiceXmlPath(),fpqqlsh+".xml");
                    if (!file.exists()) {
                        URL url = new URL(urlStr);
                        boolean invoices = NetUtils.downloadFileV2(url, sysConfig.getTempFilePath(), fpqqlsh + ".xml", "下载发票列表");
                        if(invoices) {
                            File temp = new File(sysConfig.getTempFilePath(),fpqqlsh+".xml");
                            File target = new File(sysConfig.getInvoiceXmlPath(),fpqqlsh+".xml");
                            if(temp.renameTo(target)) {
                                LOGGER.info("发票文件移动成功");
                                NetUtils.httpGetString(token,sysConfig.getFileDownloadYetUrl()+fpqqlsh,"更改下载文件的状态");
                            }
                        }
                    }
                } catch (IOException e) {
                    LOGGER.error("xml文件下载失败："+e);
                }
            }
        }
    }
}
