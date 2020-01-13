package java.com.ezio.plugin.resolver;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;

import java.com.ezio.plugin.navigator.domain.RestServiceItem;
import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class SpringResolver extends AbsServiceResolver {


    public SpringResolver(Module module) {
        this.module = module;
    }

    public SpringResolver(Project project) {
        this.project = project;
    }


    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project, GlobalSearchScope globalSearchScope) {
        return null;
    }
}
