package com.ezio.plugin.navigator.component;

import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import org.jetbrains.annotations.NotNull;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/14
 */
public class RestServiceStructure extends SimpleTreeStructure {


    private SimpleTree simpleTree;

    @NotNull
    @Override
    public Object getRootElement() {
        return null;
    }
}
