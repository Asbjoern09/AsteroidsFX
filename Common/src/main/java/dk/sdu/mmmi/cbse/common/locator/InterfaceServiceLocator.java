package dk.sdu.mmmi.cbse.common.locator;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class InterfaceServiceLocator {

    private static final InterfaceServiceLocator instance = new InterfaceServiceLocator();
    private List<ServiceLoader<?>> serviceLoaders = new ArrayList<>();

    private InterfaceServiceLocator() {
    }
    public static InterfaceServiceLocator getInstance() {
        return instance;
    }

    public synchronized <T> List<T> locateAllInterfaces(Class<T> service) {
        ServiceLoader<T> loader = findOrCreateLoader(service);
        List<T> list = new ArrayList<>();
        for (T instance : loader) {
            list.add(instance);
        }
        return list;
    }

    private synchronized <T> ServiceLoader<T> findOrCreateLoader(Class<T> service) {
        for (ServiceLoader<?> loader : serviceLoaders) {
            if (loader.iterator().hasNext() && loader.iterator().next().getClass().equals(service)) {
                return (ServiceLoader<T>) loader;
            }
        }
        ServiceLoader<T> newLoader = ServiceLoader.load(service);
        serviceLoaders.add(newLoader);
        return newLoader;
    }
}
