package com.liucj.as.api.hiconfig;

import com.liucj.as.api.utils.HiConfigUtil;
import com.liucj.as.api.utils.JsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HiConfigFileUtil {
    private static final String APPEND = "file/as/";
    private static String targetDir = PropertyUtil.getDocPath(APPEND);
    private static final String CDN_PREFIX = PropertyUtil.getCDNPrefix(APPEND);

    public static void saveContent(HiConfigModel configModel) {
        String fileName = getFileName(configModel.namespace, configModel.version);
        configModel.originalUrl = saveContent(fileName, configModel.content);
        configModel.jsonUrl = saveContent(fileName + ".json", JsonUtil.toJsonString(configModel.contentMap));
    }

    public static String saveContent(String fileName, String content) {
        FileOutputStream fos = null;
        File tempFile = null;//临时文件
        String cdnUrl = null;
        try {
            File targetFile = new File(targetDir, fileName);//目标文件
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            tempFile = File.createTempFile(fileName, ".temp", targetFile);
            fos = new FileOutputStream(tempFile);
            fos.write(content.getBytes());
            fos.flush();//刷新下
            tempFile.renameTo(targetFile);//修改文件名
            cdnUrl = CDN_PREFIX + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HiConfigUtil.close(fos);
            HiConfigUtil.delete(tempFile);
        }
        return cdnUrl;
    }

    public static String getFileName(String namespace, String version) {
        return namespace + "_" + version;
    }
}
