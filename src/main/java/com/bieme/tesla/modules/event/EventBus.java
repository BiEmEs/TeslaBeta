package com.bieme.tesla.modules.event;

import com.bieme.tesla.modules.event.events.Subscribe;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {
    private static final Map<Class<?>, List<ListenerWrapper>> listeners = new ConcurrentHashMap<>();

    public static void subscribe(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Subscribe.class)) continue;
            Class<?>[] params = method.getParameterTypes();
            if (params.length != 1) continue;

            Class<?> eventType = params[0];
            method.setAccessible(true);
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(new ListenerWrapper(obj, method));
        }
    }

    public static void unsubscribe(Object obj) {
        for (List<ListenerWrapper> wrappers : listeners.values()) {
            wrappers.removeIf(wrapper -> wrapper.owner.equals(obj));
        }
    }

    public static void post(Object event) {
        List<ListenerWrapper> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null) return;

        for (ListenerWrapper wrapper : new ArrayList<>(eventListeners)) {
            try {
                wrapper.method.invoke(wrapper.owner, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ListenerWrapper {
        final Object owner;
        final Method method;

        ListenerWrapper(Object owner, Method method) {
            this.owner = owner;
            this.method = method;
        }
    }
}