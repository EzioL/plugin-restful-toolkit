package com.ezio.plugin.method;

import com.google.common.collect.ImmutableMap;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public enum HttpMethod {
    //

    GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE, CONNECT;

    private static final ImmutableMap<String, HttpMethod> METHOD_MAP = ImmutableMap.<String, HttpMethod>builder()
            .put(GET.name(), GET)
            .put(POST.name(), POST)
            .put(PUT.name(), PUT)
            .put(DELETE.name(), DELETE)
            .put(PATCH.name(), PATCH)
            .put(HEAD.name(), HEAD)
            .put(OPTIONS.name(), OPTIONS)
            .put(TRACE.name(), TRACE)
            .put(CONNECT.name(), CONNECT)
            .build();


    public static HttpMethod getByRequestMethod(String method) {
        if (method == null || method.isEmpty()) {
            return null;
        }

        String[] split = method.split("\\.");

        if (split.length > 1) {
            method = split[split.length - 1].toUpperCase();
            return HttpMethod.valueOf(method);
        }

        return HttpMethod.valueOf(method.toUpperCase());
    }


}
