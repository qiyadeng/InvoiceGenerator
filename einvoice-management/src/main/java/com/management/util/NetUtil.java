package com.management.util;


import com.management.commons.utils.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Effort
 * @description
 * @date 2020/10/15 5:00 下午
 */
public class NetUtil {

    public static boolean upload(MultipartFile file, String savePath, String fileName, String suffix){
        boolean flag = true;
        File targetFile = new File(savePath + fileName+suffix);
        //创建目录
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(file.getInputStream(), fileOutputStream);
        } catch (IOException e) {
            flag = false;
        } finally {
            if(null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
