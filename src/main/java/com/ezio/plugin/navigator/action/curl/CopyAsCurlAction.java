package com.ezio.plugin.navigator.action.curl;

import com.ezio.plugin.helper.PsiMethodHelper;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.utils.LogUtils;
import com.ezio.plugin.utils.RestApiDataKeys;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import okhttp3.Request;

import java.awt.datatransfer.StringSelection;
import java.util.Objects;
import java.util.Optional;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/6
 */
public class CopyAsCurlAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Optional<RestServiceItem> optional = Objects.requireNonNull(RestApiDataKeys.SERVICE_ITEMS.getData(e.getDataContext()))
                .stream().findFirst();



        if (optional.isPresent()) {
            RestServiceItem item = optional.get();
            PsiMethodHelper psiMethodHelper = new PsiMethodHelper(item.getPsiMethod(), item.getModule());
            psiMethodHelper.getParameterList();

            Request.Builder requestBuilder = new Request.Builder()
                    .url(item.getFullUrl());
            switch (item.getMethod()) {
                case POST:
                    requestBuilder.post(null);
                    break;
                case PUT:
                    requestBuilder.put(null);
                    break;
            }

            String command = new CurlBuilder(requestBuilder.build()).build();
            CopyPasteManager.getInstance().setContents(new StringSelection(command));
            LogUtils.showInfo("copied to pasteboard success");
        } else {
            LogUtils.showInfo("copied to pasteboard fail");
        }
    }
}
