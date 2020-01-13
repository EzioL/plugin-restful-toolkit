package java.com.ezio.plugin.navigator.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.com.ezio.plugin.navigator.domain.RestServiceProject;
import java.com.ezio.plugin.utils.ServiceUtils;
import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceProjectManager implements ProjectComponent {

    protected final Project project;

    public RestServiceProjectManager(Project project) {
        super();
        this.project = project;
    }

    public static RestServiceProjectManager getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RestServiceProjectManager.class);
    }

    public List<RestServiceProject> getServiceProjectList() {
        return DumbService.getInstance(project).runReadActionInSmartMode(() ->
                ServiceUtils.buildRestServiceProjectList(project));
    }


}
