package org.mcnative.actionframework.common.action;

public class MAFActionSubscription {

    private final String namespace;
    private final String name;
    private final Class<? extends MAFAction> actionClass;
    private final MAFActionListener<?> listener;

    public MAFActionSubscription(String namespace, String name, Class<? extends MAFAction> actionClass, MAFActionListener<?> listener) {
        this.namespace = namespace;
        this.name = name;
        this.actionClass = actionClass;
        this.listener = listener;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public Class<? extends MAFAction> getActionClass() {
        return actionClass;
    }

    public MAFActionListener<?> getListener() {
        return listener;
    }
}
