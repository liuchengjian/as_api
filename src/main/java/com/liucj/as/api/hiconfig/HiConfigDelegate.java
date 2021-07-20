package com.liucj.as.api.hiconfig;

import com.github.pagehelper.util.StringUtil;
import com.liucj.as.api.utils.HiConfigUtil;
import com.liucj.as.api.utils.JsonUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.util.annotation.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 探针功能
 */
public class HiConfigDelegate {
    public static final String HI_CONFIG_HEY = "hi-config";
    public static final String HI_NAMESPACE = "namespace";

    /**
     * 探针模式添加，判断是否需要携带元数据
     * @param extra
     */
    public static void bindConfig(@NonNull Map<String, Object> extra) {
        Map configHeader = getConfigHeader();
        if(configHeader==null)return;
        String namespace = (String) configHeader.get(HI_NAMESPACE);
        //直接放内存里，读取数据比较高速
        HiConfigModel configModel = CacheManager.getInstance().getCache(namespace,HiConfigModel.class);
        if(configModel==null)return;
        String version =(String) configHeader.get("version");
        if(configModel.namespace.equals(namespace)&&
                HiConfigUtil.compareVersion(configModel.version,version)){
            extra.put("hiConfig",configModel);
        }
    }

    /**
     * 从每次请求中获取元数据
     * @return
     */
    private static Map getConfigHeader() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
            String hiConfig = request.getHeader(HI_CONFIG_HEY);
            if (StringUtil.isEmpty(hiConfig)) return null;
            Map<String, String> config = JsonUtil.fromJson(hiConfig, Map.class);
            String namespace = config.get(HI_NAMESPACE);
            return namespace != null ? config : null;
        }
        return null;
    }
}
