package com.ezio.plugin.navigator.component;

import com.ezio.plugin.navigator.domain.NodeDataContext;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.utils.RestServiceDataKeys;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBColor;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class RestServiceNavigatorPanel extends SimpleToolWindowPanel implements DataProvider {

    private SimpleTree myTree;

    public RestServiceNavigatorPanel(SimpleTree tree) {
        super(true, true);
        this.myTree = tree;

        myTree.setBorder(BorderFactory.createLineBorder(new JBColor(new Color(11, 6, 39),
                new Color(36, 38, 39))));
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(JBColor.RED));

        final ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("API-LIST Navigator Toolbar",
                (DefaultActionGroup) actionManager
                        .getAction("Ezio.NavigatorToolbar"),
                true);
        setToolbar(actionToolbar.getComponent());

//        Splitter splitter = new Splitter(true, .5f);
//        splitter.setShowDividerControls(true);
//        splitter.setDividerWidth(10);
//        splitter.setBorder(BorderFactory.createLineBorder(JBColor.RED));
//        splitter.setFirstComponent(scrollPane);
//        //   splitter.setSecondComponent(myRestServiceDetail);
        setContent(scrollPane);

        myTree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(e.getSource() instanceof SimpleTree)) {
                    return;
                }
                SimpleNode selectedNode = ((SimpleTree) e.getSource()).getSelectedNode();
                if (!(selectedNode instanceof RestServiceStructure.ServiceNode)) {
                    return;
                }
                RestServiceStructure.ServiceNode serviceNode = (RestServiceStructure.ServiceNode) selectedNode;
                AnAction action = actionManager.getAction("Ezio.GotoRequestMappingAction");


                AnActionEvent newEvent =
                        new AnActionEvent(null, new NodeDataContext(serviceNode.getRestServiceItem()), ActionPlaces.UNKNOWN, new Presentation(""),
                                actionManager, 0);
                action.actionPerformed(newEvent);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    @Override
    @Nullable
    public Object getData(@NotNull @NonNls String dataId) {

        if (RestServiceDataKeys.SERVICE_ITEMS.is(dataId)) {
            return extractServices();
        }

        return super.getData(dataId);
    }


    private List<RestServiceItem> extractServices() {
        List<RestServiceItem> result = Lists.newArrayList();

        Collection<? extends RestServiceStructure.BaseSimpleNode> selectedNodes = getSelectedNodes(RestServiceStructure.BaseSimpleNode.class);
        for (RestServiceStructure.BaseSimpleNode selectedNode : selectedNodes) {
            if (selectedNode instanceof RestServiceStructure.ServiceNode) {
                result.add(((RestServiceStructure.ServiceNode) selectedNode).restServiceItem);
            }
        }
        return result;
    }

    private Collection<? extends RestServiceStructure.BaseSimpleNode> getSelectedNodes(Class<RestServiceStructure.BaseSimpleNode> aClass) {
        return RestServiceStructure.getSelectedNodes(myTree, aClass);
    }


}
