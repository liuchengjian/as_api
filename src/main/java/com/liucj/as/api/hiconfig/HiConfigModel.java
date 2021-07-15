package com.liucj.as.api.hiconfig;

import com.liucj.as.api.utils.DataUtil;
import com.liucj.as.api.utils.DateUtil;
import com.liucj.as.api.utils.HiConfigUtil;
import reactor.util.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HiConfigModel {
    public Map<String,String>contentMap;
    public String id;
    public String namespace;
    public String version;
    public String createTime;
    public String originalUrl;
    public String jsonUrl;
    public String content;
    public static HiConfigModel of(String namespace,String content){
        HiConfigModel model = new HiConfigModel();
        model.namespace = namespace;
        model.version = HiConfigUtil.getVersion();
        model.content = content;
        model.createTime = DateUtil.currentDate();
        model.contentMap = parseContent(content);
        return model;
    }

    /**
     * 建 xxx= xx转成map
     * @param content
     * @return
     */
    private static Map<String, String> parseContent(@NonNull String content) {
        String[] items = content.split("\n");
        Map<String,String> contentMap = new HashMap<>();
        for (String item:items){
            String[] kvd = item.split("=");
            if(kvd.length>1){
                //去除注释
                contentMap.put(kvd[0],kvd[1]);
            }
        }
        return contentMap;
    }
}
