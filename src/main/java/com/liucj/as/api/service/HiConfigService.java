package com.liucj.as.api.service;

import com.github.pagehelper.util.StringUtil;
import com.liucj.as.api.hiconfig.CacheManager;
import com.liucj.as.api.hiconfig.HiConfigModel;
import com.liucj.as.api.mapper.HiConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HiConfigService {
    @Autowired
    private HiConfigMapper hiConfigMapper;

    public List<HiConfigModel> getConfig(String namespace) {
        List<HiConfigModel> models = hiConfigMapper.getConfig(namespace);
        if (models != null && models.size() > 0) {
            CacheManager.getInstance().pubMemoryCache(StringUtil.isEmpty(namespace) ? CacheManager.KET.CONFIG : namespace, models.get(0));
        }
        return models;
    }

    public List<HiConfigModel> getAllConfig() {
        List<HiConfigModel> models = hiConfigMapper.getAllConfig();
        if(models.isEmpty())return null;
        for(HiConfigModel model :models){
            CacheManager.getInstance().pubMemoryCache(model.namespace,model);
        }
        CacheManager.getInstance().needRefreshConfig = false;
        return models;
    }

    public void saveConfig(HiConfigModel model) {
        hiConfigMapper.saveConfig(model);
        CacheManager.getInstance().pubMemoryCache(model.namespace, model);
    }

}
