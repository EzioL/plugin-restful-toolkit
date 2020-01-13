package com.ezio.plugin.resolver;


import com.ezio.plugin.navigator.domain.RestServiceItem;

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
    java.util.List<RestServiceItem> findAllSupportedServiceItemsInModule();

    /**
     * 获取project 中所有的服务列表
     */
    java.util.List<RestServiceItem> findAllSupportedServiceItemsInProject();

}
