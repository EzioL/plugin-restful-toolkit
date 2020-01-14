package com.ezio.plugin.navigator.component;

import com.ezio.plugin.constant.Icons;
import com.ezio.plugin.helper.ServiceHelper;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.ezio.plugin.navigator.domain.node.RootNode;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.util.DisposeAwareRunnable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.util.List;
import java.util.Optional;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceNavigator implements ProjectComponent {

    public static final Logger LOG = Logger.getInstance(RestServiceNavigator.class);

    public static final String TOOL_WINDOW_ID = "RestService";

    protected final Project project;

    private ToolWindowEx toolWindowEx;

    protected RestServiceStructure serviceStructure;

    private SimpleTree simpleTree;

    public RestServiceNavigator(Project project) {
        this.project = project;
    }

    public static RestServiceNavigator getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RestServiceNavigator.class);
    }


    @Override
    public void initComponent() {
//        List<RestServiceProject> serviceProjectList =
////                RestServiceProjectManager.getInstance(project).getServiceProjectList();
//
//        LOG.info(serviceProjectList.toString());
        //    initToolWindow();

        Optional.ofNullable(project)
                .filter(e -> !e.isDisposed())
                .filter(e -> !e.isInitialized())
                .ifPresent(e -> {
                    Runnable runnable = DisposeAwareRunnable.create(this::initToolWindow, project);
                    StartupManager.getInstance(project).registerPostStartupActivity(runnable);
                });

    }

    private void initTree() {
        simpleTree = new SimpleTree();
        simpleTree.getEmptyText().clear();
        simpleTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    private void initToolWindow() {

        ToolWindowManagerEx manager = ToolWindowManagerEx.getInstanceEx(project);
        toolWindowEx = (ToolWindowEx) manager.getToolWindow(TOOL_WINDOW_ID);
        initTree();
        toolWindowEx = (ToolWindowEx) manager.registerToolWindow(TOOL_WINDOW_ID, false,
                ToolWindowAnchor.RIGHT, project, true);
        toolWindowEx.setIcon(Icons.SERVICE);
        JPanel panel = new RestServiceNavigatorPanel(simpleTree);
        Content content = ServiceManager.getService(ContentFactory.class).createContent(panel, "", false);
        ContentManager contentManager = toolWindowEx.getContentManager();
        contentManager.addContent(content);
        contentManager.setSelectedContent(content, false);

        RestServiceStructure restServiceStructure = new RestServiceStructure(project, simpleTree);
        RootNode rootNode = new RootNode(null);
        List<RestServiceProject> restServiceProjects = ServiceHelper.buildRestServiceProjectList(project);
        rootNode.updateProjectNodes(restServiceProjects);

        StructureTreeModel<SimpleTreeStructure> structureTreeModel =
                new StructureTreeModel<>(new SimpleTreeStructure.Impl(rootNode), project);
        rootNode.update();
        System.out.println("1");
    }


}
