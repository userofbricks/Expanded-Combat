package com.userofbricks.expanded_combat.util;

import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.*;

public class ECPluginFinder {
    private static final Logger LOGGER = LogManager.getLogger();

    private ECPluginFinder() {

    }

    public static List<IExpandedCombatPlugin> getECPlugins() {
        List<IExpandedCombatPlugin> pluginList = getInstances(ECPlugin.class, IExpandedCombatPlugin.class);
        pluginList.sort(Comparator.comparingInt(IExpandedCombatPlugin::loadOrder));
        return pluginList;
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> List<T> getInstances(Class<?> annotationClass, Class<T> instanceClass) {
        Type annotationType = Type.getType(annotationClass);
        Set<String> pluginClassNames = getPluginClassNames(annotationType);
        List<T> instances = new ArrayList<>();
        for (String className : pluginClassNames) {
            try {
                Class<?> asmClass = Class.forName(className);
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                Constructor<? extends T> constructor = asmInstanceClass.getDeclaredConstructor();
                T instance = constructor.newInstance();
                instances.add(instance);
            } catch (ReflectiveOperationException | LinkageError e) {
                LOGGER.error("Failed to load: {}", className, e);
            }
        }
        return instances;
    }

    @NotNull
    private static Set<String> getPluginClassNames(Type annotationType) {
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Set<String> pluginClassNames = new LinkedHashSet<>();
        for (ModFileScanData scanData : allScanData) {
            Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();
            for (ModFileScanData.AnnotationData a : annotations) {
                if (Objects.equals(a.annotationType(), annotationType)) {
                    boolean readyToLoad = true;
                    if (a.annotationData().get("required") != null) {
                        for (String modId : (ArrayList<String>) a.annotationData().get("required")) {
                            if (!ModList.get().isLoaded(modId)) {
                                readyToLoad = false;
                                LOGGER.debug("Mod " + modId + " is not loaded for EC Plugin Class " + a.memberName() + " so it was not loaded");
                                break;
                            }
                        }
                    }
                    if (readyToLoad) {
                        String memberName = a.memberName();
                        pluginClassNames.add(memberName);
                    }
                }
            }
        }
        return pluginClassNames;
    }
}