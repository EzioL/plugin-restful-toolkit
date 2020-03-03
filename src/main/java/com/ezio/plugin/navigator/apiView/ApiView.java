package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.Disposable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public interface ApiView extends Disposable {


    @NotNull
    ApiViewModel getTreeModel();

    interface Scrollable extends ApiView {
        Dimension getCurrentSize();

        void setReferenceSizeWhileInitializing(Dimension size);
    }


}
