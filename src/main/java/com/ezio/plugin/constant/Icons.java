package com.ezio.plugin.constant;

import com.ezio.plugin.method.HttpMethod;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class Icons {

    public static class METHOD {
        public static Icon get(HttpMethod method) {
            if (method == null) {
                return UNDEFINED;
            }
            if (method.equals(HttpMethod.GET)) {
                return METHOD.GET;
            } else if (method.equals(HttpMethod.POST)) {
                return METHOD.POST;
            } else if (method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
                return METHOD.PUT;
            } else if (method.equals(HttpMethod.DELETE)) {
                return METHOD.DELETE;
            }
            return UNDEFINED;
        }

        public static final Icon GET = IconLoader.getIcon("/icons/method/g.png");
        public static final Icon PUT = IconLoader.getIcon("/icons/method/p2.png");
        public static final Icon POST = IconLoader.getIcon("/icons/method/p.png");
        public static final Icon PATCH = IconLoader.getIcon("/icons/method/p3.png");
        public static final Icon DELETE = IconLoader.getIcon("/icons/method/d.png");
        public static final Icon UNDEFINED = IconLoader.getIcon("/icons/method/undefined.png");
    }

    public static final Icon MODULE = AllIcons.Modules.SourceRoot;
    public static final Icon Refresh = AllIcons.Actions.Refresh;
    public static final Icon SERVICE = IconLoader.getIcon("/icons/service.png");

}
