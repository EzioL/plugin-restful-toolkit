package com.ezio.plugin.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/5
 */
public class LogUtils {

    public static void showError(String content) {
        Notifications.Bus.notify(new Notification("REST API Helper", "REST API Helper", content, NotificationType.ERROR));
    }

    public static void showInfo(String content) {
        Notifications.Bus.notify(new Notification("REST API Helper", "REST API Helper", content, NotificationType.INFORMATION));
    }
}
