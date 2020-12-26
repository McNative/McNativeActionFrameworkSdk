package org.mcnative.actionframework.common.action;

public interface MAFActionListener<T extends MAFAction> {

    void onActionReceive(MAFActionExecutor executor,T action);

    @SuppressWarnings("unchecked")
    default void callListener(MAFActionExecutor executor,MAFAction action){
        onActionReceive(executor,(T) action);
    }

}
