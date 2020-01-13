package java.com.ezio.plugin.navigator.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceNavigator implements ProjectComponent {

    public static final Logger LOG = Logger.getInstance(RestServiceNavigator.class);

    public static final String TOOL_WINDOW_ID = "RestService";


    public RestServiceNavigator(Project project) {
    }

    public static RestServiceNavigator getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RestServiceNavigator.class);
    }


    @Override
    public void initComponent() {

    }
}
