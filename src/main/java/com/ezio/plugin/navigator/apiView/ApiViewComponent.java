package com.ezio.plugin.navigator.apiView;

import com.intellij.ide.util.FileStructurePopup;
import com.intellij.ide.util.treeView.NodeRenderer;
import com.intellij.ide.util.treeView.smartTree.SmartTreeStructure;
import com.intellij.ide.util.treeView.smartTree.TreeElementWrapper;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.TreeSpeedSearch;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.tree.TreeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class ApiViewComponent extends SimpleToolWindowPanel implements DataProvider, ApiView.Scrollable {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiViewComponent.class);

    private final TreeModelWrapper myTreeModelWrapper;
    private final Project myProject;
    private final ApiViewModel myTreeModel;
    private final Tree myTree;
    private final AsyncTreeModel myAsyncTreeModel;
    private final SmartTreeStructure myTreeStructure;
    private final StructureTreeModel myStructureTreeModel;


    public ApiViewComponent(@NotNull ApiViewModel structureViewModel,
                            @NotNull Project project,
                            boolean showRootNode) {
        super(true, true);

        myProject = project;
        myTreeModel = structureViewModel;
        myTreeModelWrapper = new TreeModelWrapper(myTreeModel);


        myTreeStructure = new SmartTreeStructure(project, myTreeModelWrapper) {
            @Override
            public void rebuildTree() {
//                if (isDisposed()) {
//                    return;
//                }
                super.rebuildTree();
            }

            @Override
            public boolean isToBuildChildrenInBackground(@NotNull final Object element) {
                return Registry.is("ezio.apiView.StructureViewTreeStructure.BuildChildrenInBackground") ||
                        getRootElement() == element;
            }

            @NotNull
            @Override
            protected TreeElementWrapper createTree() {
                return null;
                // return new MyNodeWrapper(myProject, myModel.getRoot(), myModel);
            }

            @Override
            public String toString() {
                return "api view tree structure(model=" + myTreeModel + ")";
            }
        };

        myStructureTreeModel = new StructureTreeModel<>(myTreeStructure, this);

        myAsyncTreeModel = new AsyncTreeModel(myStructureTreeModel, this);
        myTree = new MyTree(myAsyncTreeModel);
        myTree.setRootVisible(showRootNode);
        myTree.getEmptyText().setText("api is empty");
        setContent(ScrollPaneFactory.createScrollPane(myTree));
        setupTree();
    }

    private void setupTree() {
        myTree.setCellRenderer(new NodeRenderer());
        myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        myTree.setShowsRootHandles(true);
//        myProject.getMessageBus().connect(this)
//                .subscribe(UISettingsListener.TOPIC, o -> rebuild());
        TreeUtil.installActions(getTree());
        new TreeSpeedSearch(getTree(), treePath -> {
            Object userObject = TreeUtil.getLastUserObject(treePath);
            return userObject != null ? FileStructurePopup.getSpeedSearchText(userObject) : null;
        });

    }

    private JTree getTree() {
        return myTree;
    }

    @NotNull
    @Override
    public ApiViewModel getTreeModel() {
        return getTreeModel();
    }

    @Override
    public Dimension getCurrentSize() {
        return getTree().getSize();
    }

    @Override
    public void setReferenceSizeWhileInitializing(Dimension size) {

    }

    @Override
    public void dispose() {

    }

}
