package com.ezio.plugin.navigator.component;

import com.ezio.plugin.constant.Icons;
import com.ezio.plugin.utils.ToolkitUtil;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.SimpleTree;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.util.Optional;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestApiNavigator implements ProjectComponent {

    public static final String TOOL_WINDOW_ID = "REST API Helper";

    protected final Project project;

    private ToolWindowEx toolWindowEx;

    protected RestApiStructure restApiStructure;

    private SimpleTree simpleTree;

    private RestApiProjectManager projectManager;

    public RestApiNavigator(Project project, RestApiProjectManager projectManager) {
        this.project = project;
        this.projectManager = projectManager;

    }

    private void initStructure() {
        restApiStructure = new RestApiStructure(project, projectManager, simpleTree);
    }


    public void scheduleStructureUpdate() {
        scheduleStructureRequest(() -> restApiStructure.update());

    }

    private void scheduleStructureRequest(Runnable r) {

        DumbService.getInstance(project).smartInvokeLater(() -> {
            if (!toolWindowEx.isVisible()) {
                return;
            }
            if (!Optional.ofNullable(restApiStructure).isPresent()) {
                initStructure();
            }
            r.run();
        });

    }

    @Override
    public void initComponent() {
        ToolkitUtil.runWhenInitialized(project, () -> {
            if (!project.isDisposed()) {
                initToolWindow();
            }
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

        JPanel panel = new RestApiNavigatorPanel(simpleTree);
        ContentFactory contentFactory = ServiceManager.getService(ContentFactory.class);
        Content content = contentFactory.createContent(panel, "", false);
        ContentManager contentManager = toolWindowEx.getContentManager();
        contentManager.addContent(content);
        contentManager.setSelectedContent(content, false);

        project.getMessageBus().connect().subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
            boolean wasVisible = false;

            @Override
            public void stateChanged() {
                if (toolWindowEx.isDisposed()) {
                    return;
                }
                boolean visible = toolWindowEx.isVisible();
                if (!visible || wasVisible) {
                    return;
                }
                scheduleStructureUpdate();
                wasVisible = true;
            }
        });
    }


}