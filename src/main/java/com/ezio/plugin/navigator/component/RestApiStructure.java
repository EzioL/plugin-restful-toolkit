package com.ezio.plugin.navigator.component;

import com.ezio.plugin.constant.Icons;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.google.common.collect.Lists;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Here be dragons !
 * 参考 ExternalProjectsStructure
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class RestApiStructure extends SimpleTreeStructure implements Disposable {


    private final Project myProject;
    private final Tree myTree;
    private StructureTreeModel myTreeModel;
    private RootNode myRoot;
    private final RestApiProjectManager projectManager;

    public RestApiStructure(Project myProject, RestApiProjectManager projectManager, Tree myTree) {
        this.myProject = myProject;
        this.projectManager = projectManager;
        this.myTree = myTree;
        this.myRoot = new RootNode();
        SimpleTreeStructure treeStructure = new SimpleTreeStructure.Impl(this.myRoot);
        this.myTreeModel = new StructureTreeModel<>(treeStructure, this);
        myTree.setModel(new AsyncTreeModel(this.myTreeModel, this));
        // TreeUtil.expand(myTree, 1);
    }

    public void update() {
        List<RestServiceProject> projects = projectManager.getServiceProjectList();
        updateProjects(projects);
    }

    private void updateProjects(List<RestServiceProject> projects) {
        myRoot.updateProjectNodes(projects);
    }

    public void updateFrom(SimpleNode node) {
        if (node != null) {
            myTreeModel.invalidate(node, true);
        }
    }

    public void updateUpTo(SimpleNode node) {
        SimpleNode each = node;
        while (each != null) {
            this.myTreeModel.invalidate(each, false);
            each = each.getParent();
        }
    }

    @NotNull
    @Override
    public Object getRootElement() {
        return myRoot;
    }

    @Override
    public void dispose() {
        this.myRoot = null;
    }

    public static <T extends BaseSimpleNode> List<T> getSelectedNodes(SimpleTree tree, Class<T> nodeClass) {
        List<T> filtered = new ArrayList<>();
        for (SimpleNode node : getSelectedNodes(tree)) {
            if ((nodeClass != null) && (!nodeClass.isInstance(node))) {
                filtered.clear();
                break;
            }
            //noinspection unchecked
            filtered.add((T) node);
        }
        return filtered;
    }

    private static List<SimpleNode> getSelectedNodes(SimpleTree tree) {
        return Optional.ofNullable(tree.getSelectionPaths())
                .map(Lists::newArrayList)
                .map(treePaths -> treePaths.stream()
                        .map(tree::getNodeFor)
                        .collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }


    public abstract class BaseSimpleNode extends CachingSimpleNode {
        protected BaseSimpleNode(SimpleNode aParent) {
            super(aParent);
        }

        protected void childrenChanged() {
            BaseSimpleNode node = this;
            while (Objects.nonNull(node)) {
                node.cleanUpCache();
                node = (BaseSimpleNode) node.getParent();
            }
            updateUpTo(this);
        }
    }

    public class RootNode extends BaseSimpleNode {

        private List<ProjectNode> projectNodes = Lists.newArrayList();

        public RootNode() {
            super(null);
            getTemplatePresentation().setIcon(AllIcons.Actions.ModuleDirectory);
        }

        @Override
        protected SimpleNode[] buildChildren() {
            return projectNodes.toArray(new SimpleNode[projectNodes.size()]);
        }

        @Override
        public String getName() {
            if (projectNodes.size() > 0) {
                int sum = projectNodes.stream().map(ProjectNode::getServiceNodeList)
                        .filter(Objects::nonNull).mapToInt(List::size).sum();
                return "Found " + projectNodes.size() + " Module, " + sum + " API";
            }
            return null;
        }


        public void updateProjectNodes(List<RestServiceProject> projects) {
            projectNodes.clear();
            for (RestServiceProject project : projects) {
                ProjectNode projectNode = new ProjectNode(this, project);
                projectNodes.add(projectNode);
            }

            updateFrom(getParent());
            childrenChanged();
        }
    }


    public class ProjectNode extends BaseSimpleNode {

        private RestServiceProject restServiceProject;

        private List<ServiceNode> serviceNodeList;

        public ProjectNode(SimpleNode aParent, RestServiceProject restServiceProject) {
            super(aParent);
            this.restServiceProject = restServiceProject;
            this.serviceNodeList = Lists.newArrayList();
            getTemplatePresentation().setIcon(Icons.MODULE);
            updateServiceNodes(restServiceProject.getRestServiceItemList());
        }

        private void updateServiceNodes(List<RestServiceItem> serviceItems) {
            this.serviceNodeList.clear();
            List<ServiceNode> collect = serviceItems.stream().map(e -> new ServiceNode(this, e))
                    .collect(Collectors.toList());
            this.serviceNodeList = collect;
        }

        @Override
        protected SimpleNode[] buildChildren() {

            return serviceNodeList.toArray(new SimpleNode[serviceNodeList.size()]);
        }

        @Override
        public String getName() {
            return restServiceProject.getModuleName() + " (port: " + restServiceProject.getPort()
                    + ", API count: " + Optional.ofNullable(serviceNodeList).map(List::size).orElse(0) + ")";
        }

        @Override
        public void handleSelection(SimpleTree tree) {
            super.handleSelection(tree);
        }

        public List<ServiceNode> getServiceNodeList() {
            return serviceNodeList;
        }
    }


    public class ServiceNode extends BaseSimpleNode {

        RestServiceItem restServiceItem;

        public ServiceNode(SimpleNode parent, RestServiceItem serviceItem) {
            super(parent);
            this.restServiceItem = serviceItem;
            getTemplatePresentation().setIcon(Icons.METHOD.get(serviceItem.getMethod()));
        }

        @Override
        public String getName() {
            return this.restServiceItem.getName();
        }

        @Override
        public void handleSelection(SimpleTree tree) {
            Optional.ofNullable((ServiceNode) tree.getSelectedNode())
                    .ifPresent(serviceNode -> showDetail(serviceNode.restServiceItem));
        }

        private void showDetail(RestServiceItem serviceItem) {
        }

        @Override
        protected SimpleNode[] buildChildren() {
            return new SimpleNode[0];
        }

        public RestServiceItem getRestServiceItem() {
            return restServiceItem;
        }
    }


}
