package com.ezio.plugin.utils;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.intellij.openapi.actionSystem.DataKey;

import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class RestServiceDataKeys {


    public static final DataKey<List<RestServiceItem>> SERVICE_ITEMS = DataKey.create("SERVICE_ITEM");

    private RestServiceDataKeys() {
    }
}
