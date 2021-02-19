package org.mcnative.actionframework.sdk.client;

public class MAFConnectionFailedException extends RuntimeException{

    public MAFConnectionFailedException(Exception exception) {
        super(exception.getMessage(),exception);
    }
}
