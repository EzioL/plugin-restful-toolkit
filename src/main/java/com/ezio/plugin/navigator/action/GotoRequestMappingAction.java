package com.ezio.plugin.navigator.action;

import com.ezio.plugin.navigator.domain.NodeDataContext;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.intellij.ide.actions.GotoActionAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/4
 */
public class GotoRequestMappingAction extends GotoActionAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (!(e.getDataContext() instanceof NodeDataContext)) {
            return;
        }
        NodeDataContext dataContext = (NodeDataContext) e.getDataContext();
        RestServiceItem restServiceItem = dataContext.getData();
        restServiceItem.navigate(true);
    }
}
