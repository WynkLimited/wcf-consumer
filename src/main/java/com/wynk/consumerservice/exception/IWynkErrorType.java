package com.wynk.consumerservice.exception;

import org.slf4j.Marker;

public interface IWynkErrorType {

    public String getErrorCode();

    public String getErrorTitle();

    public String getErrorMessage();

    public int getHttpResponseStatusCode();

    public Marker getMarker();
}
