package com.liucj.as.api.service;

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
        return hiConfigMapper.getConfig(namespace);
    }
    public List<HiConfigModel> getAllConfig() {
        return hiConfigMapper.getAllConfig();
    }
    public void saveConfig(HiConfigModel model) {
        hiConfigMapper.saveConfig(model);
    }

}
