package com.ezio.plugin.navigator.component;

import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.JBColor;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class RestServiceNavigatorPanel extends SimpleToolWindowPanel implements DataProvider {

    @Nullable
    private RestServiceStructure myStructure;
    private SimpleTree myTree;

    public RestServiceNavigatorPanel(SimpleTree tree) {
        super(true, true);
        this.myTree = tree;

        myTree.setBorder(BorderFactory.createLineBorder(new JBColor(new Color(11, 6, 39),
                new Color(36, 38, 39))));
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(JBColor.RED));

        Splitter splitter = new Splitter(true, .5f);
        splitter.setShowDividerControls(true);
        splitter.setDividerWidth(10);
        splitter.setBorder(BorderFactory.createLineBorder(JBColor.RED));
        splitter.setFirstComponent(scrollPane);
        //   splitter.setSecondComponent(myRestServiceDetail);
        setContent(splitter);

//        simpleTree.addMouseListener(new PopupHandler() {
//            @Override
//            public void invokePopup(Component comp, int x, int y) {
//
//            }
//        });


    }


}
