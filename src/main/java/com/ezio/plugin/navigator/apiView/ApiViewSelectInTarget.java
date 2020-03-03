package com.ezio.plugin.navigator.apiView;

import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class ApiViewSelectInTarget implements SelectInTarget {

    private final Project myProject;


    public ApiViewSelectInTarget(Project myProject) {
        this.myProject = myProject;
    }

    @Override
    public boolean canSelect(SelectInContext context) {
        return context.getFileEditorProvider() != null;
    }

    private ApiViewWrapper getApiViewWrapper() {
        return ApiViewFactoryEx.getInstanceEx(myProject).getApiViewWrapper();
    }

    @Override
    public void selectIn(SelectInContext context, boolean requestFocus) {


//        StructureViewWrapper customStructureView = CUSTOM_STRUCTURE_VIEW_KEY.get(context.getVirtualFile());
//        if (customStructureView != null) {
//            customStructureView.selectCurrentElement(fileEditor, context.getVirtualFile(), requestFocus);
//            return;
//        }

        ToolWindowManager windowManager = ToolWindowManager.getInstance(context.getProject());


        final Runnable runnable = () -> ApiViewFactoryEx.getInstanceEx(myProject)
                .runWhenInitialized(
                        () -> {
                            System.out.println("xxx");
                        });


        if (requestFocus) {
            windowManager.getToolWindow(ToolWindowId.STRUCTURE_VIEW).activate(runnable);
        } else {
            runnable.run();
        }

    }
}
