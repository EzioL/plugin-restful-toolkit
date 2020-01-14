package com.ezio.plugin.handler;

import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Here be dragons !
 * 获取配置文件的属性
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class PropertiesHandler {
    public static final String YAML_SUFFIX = "yaml";
    public static final String PROPERTIES_SUFFIX = "properties";

    public static final ImmutableList<String> CONFIG_FILES = ImmutableList.of("bootstrap", "application");
    public static final ImmutableList<String> FILE_SUFFIX = ImmutableList.of(YAML_SUFFIX, PROPERTIES_SUFFIX);

    private Module module;

    public PropertiesHandler(Module module) {
        this.module = module;
    }

    public String getPort() {
        // TODO: Ezio 2020/1/14
        List<String> configFileNameList =
                CONFIG_FILES.stream().flatMap(e -> FILE_SUFFIX.stream().map(c -> e + "." + c)).collect(Collectors.toList());
        return configFileNameList.stream()
                .map(this::findPsiFileInModule)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .map(psiFile -> {
                    if (psiFile.getName().contains(PROPERTIES_SUFFIX)) {
                        Properties properties = getProperties(psiFile.getText());
                        return properties.getProperty("server.port", RestServiceProject.DEFAULT_PORT);
                    }
                    if (psiFile.getName().contains(YAML_SUFFIX)) {
                        Map<String, Object> yamlMap = getYamlMap(psiFile.getText());
                        Map<String, Object> serverMap = (Map<String, Object>) yamlMap.getOrDefault("server", Maps.newHashMap());
                        return Optional.ofNullable(serverMap.get("port"))
                                .map(String::valueOf)
                                .orElse(RestServiceProject.DEFAULT_PORT);
                    }
                    return RestServiceProject.DEFAULT_PORT;
                })
                .orElse(RestServiceProject.DEFAULT_PORT);
    }

    private Map<String, Object> getYamlMap(String text) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(new StringReader(text));
        return map;
    }

    private Properties getProperties(String text) {
        Properties prop = new Properties();
        try {
            prop.load(new StringReader(text));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    private Optional<PsiFile> findPsiFileInModule(String fileName) {
        return Stream.of(FilenameIndex.getFilesByName(module.getProject(), fileName, GlobalSearchScope.moduleScope(module)))
                .findFirst();
    }


}
