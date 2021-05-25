package com.einvoicemerchant.config;

import com.alibaba.fastjson.JSONObject;
import com.einvoicemerchant.monitor.FileChangeMonitor;
import com.einvoicemerchant.utils.base.DateUtils;
import com.einvoicemerchant.utils.net.NetUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Effort
 * @description 系统命令行运行器
 * @date 2020/9/22 2:51 下午
 */
@Component
public class  SystemRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(SystemRunner.class);

    @Autowired
    private SysConfig sysConfig;

    private  int loginCount = 3;

    public static String token;

    private String time = null;

    private static String headByFpqqlsh;

    private static String uploadResultUrl;

    /**
     * 商户登录
     */
    public void login() {
        String nowString = DateUtils.nowString().substring(0,13);
        boolean sameHour = false;
        if (this.time != null) {
            sameHour = this.time.substring(0, 13).equals(nowString);
        }
        if (token != null && !sameHour) {
            token = null;
            logger.info("ensureAccessToken : reset  token");
        }
        if (token == null) {
            Map<String, String> param = new HashMap(2);
            param.put("appId",sysConfig.getAppid());
            param.put("appSecret",sysConfig.getAppSecret());
            String result = NetUtils.getPost(sysConfig.getLoginUrl(), param, "商户端登录");
            if(result != null){
                JSONObject object = JSONObject.parseObject(result);
                Integer code = object.getInteger("code");
                if(code == 0) {
                    token = object.getString("data");
                }
            }
            this.time = DateUtils.nowString();
            logger.info(String.format("登陆成功 : set accessToken : [%s]. [%s]", token, this.time));
        }
    }


    @Override
    public void run(String... strings) throws Exception {
        do {
            loginCount = -- loginCount;
            login();
            if (token == null) {
              logger.error("登录失败正在尝试登录");
              Thread.sleep(5000);
            }
            if(loginCount <= 0) {
                logger.error("登录失败，请联系管理员");
                break;
            }
        }while (token == null);

        if (token != null) {
            folderMonitor();
        }
    }
    public void initUrl() {
        headByFpqqlsh = this.sysConfig.getHeadByFpqqlsh();
        uploadResultUrl = this.sysConfig.getUploadResultUrl();
    }

    public String initFolder() {
        String path = this.sysConfig.getInvoiceResultPath();
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File filePath2 = new File(sysConfig.getInvoiceXmlPath());
        if (!filePath2.exists()) {
            filePath2.mkdirs();
        }
        String tempFilePath = this.sysConfig.getTempFilePath();
        File file = new File(tempFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 文件夹监控
     * @throws Exception
     */
    public void folderMonitor() throws Exception {
        String path = initFolder();
        initUrl();
        long interval = TimeUnit.SECONDS.toMillis(1);
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".xml"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);
        FileAlterationObserver observer = new FileAlterationObserver(new File(path), filter);
        observer.addListener(new FileChangeMonitor());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        monitor.start();
    }

    public static void uploadFileStatus(File file) throws DocumentException {
        String token = SystemRunner.token;
        SAXReader reader = new SAXReader();
        Document read = reader.read(file);
        Element root = read.getRootElement();
        Element responseInvoice = root.element("RESPONSE_COMMON_FPKJ");
        if (responseInvoice != null) {
            String fileName = responseInvoice.elementText("FPQQLSH");
            String record = NetUtils.httpGetString(token, headByFpqqlsh + fileName, "检查是否有该流水号的记录");
            if(record != null) {
                String returnCode = responseInvoice.elementText("RETURNCODE");
                String returnMsg = responseInvoice.elementText("RETURNMSG");
                MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
                if (returnCode!= null && !"0000".equals(returnCode)) {
                    form.add("RETURNCODE",returnCode);
                    form.add("RETURNMSG",returnMsg);
                }
                FileSystemResource fileSystemResource =  new FileSystemResource(file);
                form.add("uploadFile", fileSystemResource);
                form.add("fileName",fileName);
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders httpHeaders = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("multipart/form-data");
                httpHeaders.set("token", token);
                httpHeaders.setContentType(type);
                HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, httpHeaders);
                restTemplate.postForObject(uploadResultUrl, files, String.class);
            }
        }
    }
}
