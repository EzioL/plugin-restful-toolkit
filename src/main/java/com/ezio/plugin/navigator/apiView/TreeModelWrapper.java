package com.ezio.plugin.navigator.apiView;

import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.Grouper;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class TreeModelWrapper implements ApiViewModel {

    private final ApiViewModel myModel;

    public TreeModelWrapper(ApiViewModel myModel) {
        this.myModel = myModel;
    }

    @NotNull
    @Override
    public TreeElement getRoot() {
        return myModel.getRoot();
    }

    @NotNull
    @Override
    public Grouper[] getGroupers() {
        return new Grouper[0];
    }

    @NotNull
    @Override
    public Sorter[] getSorters() {
        return new Sorter[0];
    }

    @NotNull
    @Override
    public Filter[] getFilters() {
        return new Filter[0];
    }

    @Override
    public void dispose() {

    }
}
