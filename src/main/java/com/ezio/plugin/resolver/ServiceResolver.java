package com.ezio.plugin.resolver;


import com.ezio.plugin.navigator.domain.RestServiceItem;

import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public interface ServiceResolver {

    /**
     * 获取module 中所有的服务列表
     */
    List<RestServiceItem> findAllSupportedServiceItemsInModule();

    /**
     * 获取project 中所有的服务列表
     */
    List<RestServiceItem> findAllSupportedServiceItemsInProject();

}
