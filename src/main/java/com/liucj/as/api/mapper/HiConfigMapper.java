package com.liucj.as.api.mapper;

import com.liucj.as.api.hiconfig.HiConfigModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiConfigMapper {
    List<HiConfigModel> getConfig(String namespace);
    List<HiConfigModel> getAllConfig();
    void saveConfig(HiConfigModel model);

}
