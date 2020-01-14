package com.ezio.plugin.handler;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.ezio.plugin.resolver.SpringResolver;
import com.ezio.plugin.utils.Optionals;
import com.intellij.openapi.module.Module;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Here be dragons !
 * 识别接口 和 端口
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class ModuleHandler {


    public static RestServiceProject handle(Module module) {
        // 这边要不要把没有接口的干掉？
        List<RestServiceItem> restServices = buildRestServiceItemList(module);

        return Optionals.ofPredicable(restServices, CollectionUtils::isNotEmpty)
                .map(e -> {

                    PropertiesHandler propertiesHandler = new PropertiesHandler(module);
                    return new RestServiceProject(propertiesHandler.getPort(), module, restServices);
                })
                .orElse(null);
    }

    private static List<RestServiceItem> buildRestServiceItemList(Module module) {
        SpringResolver springResolver = new SpringResolver(module);
        return springResolver.findAllSupportedServiceItemsInModule();
    }


}
