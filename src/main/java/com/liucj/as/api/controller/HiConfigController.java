package com.liucj.as.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.liucj.as.api.entity.ResponseCode;
import com.liucj.as.api.entity.ResponseEntity;
import com.liucj.as.api.hiconfig.HiConfigFileUtil;
import com.liucj.as.api.mapper.HiConfigMapper;
import com.liucj.as.api.hiconfig.HiConfigModel;
import com.liucj.as.api.service.HiConfigService;
import com.liucj.as.api.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/config")
@Api(tags = {"HiConfig"})
public class HiConfigController {
    @Autowired
    private HiConfigService service;

    @ApiOperation(value = "更新配置中心")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestParam(value = "namespace") @ApiParam("命名空间") String namespace,
                                 @RequestParam(value = "config") @ApiParam("配置") String config) {
        if(StringUtil.isEmpty(namespace)||StringUtil.isEmpty(config)){
            return ResponseEntity.of(ResponseCode.RC_CONFIG_INVALID);
        }
        try {
            HiConfigModel model = HiConfigModel.of(namespace,config);
            HiConfigFileUtil.saveContent(model);
            service.saveConfig(model);
            return ResponseEntity.success(model);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(ResponseCode.RC_ERROR).setMessage("保存配置失败");
        }
    }


    @ApiOperation(value = "配置中心")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getConfig(@RequestParam(value = "namespace", required = false) @ApiParam("命名空间") String namespace,
                                    @RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页") int pageIndex,
                                    @RequestParam(value = "pageSize", required = true, defaultValue = "10")
                                    @ApiParam("每页显示的数量") int pageSize) {
        try {
            List<HiConfigModel> list;
            PageHelper.startPage(pageIndex, pageSize);
            if (StringUtil.isEmpty(namespace)) {
                list = service.getAllConfig();
            } else {
                list = service.getConfig(namespace);
            }
            return ResponseEntity.success(DataUtil.getPageData(list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.errorMessage("查找配置中心列表失败");
        }

    }
}
