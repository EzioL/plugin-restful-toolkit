package com.ezio.plugin.navigator.apiView;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.pom.Navigatable;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public interface ApiViewTreeElement extends TreeElement, Navigatable {

    ApiViewTreeElement[] EMPTY_ARRAY = new ApiViewTreeElement[0];

    /**
     * Returns the data object (usually a PSI element) corresponding to the
     * structure view element.
     *
     * @return the data object instance.
     */
    Object getValue();
}
