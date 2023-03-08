package com.wynk.consumerservice.exception;

import lombok.Getter;
import org.apache.http.HttpStatus;
import org.slf4j.Marker;

@Getter
public enum WynkErrorType implements IWynkErrorType{
    MUS020(
            "User doesn't exist",
            "User doesn't exist",
            HttpStatus.SC_PRECONDITION_FAILED,
            BaseLoggingMarkers.APPLICATION_INVALID_USECASE);
    private String errorTitle;
    private String errorMsg;
    private int httpResponseStatusCode;
    private Marker marker;



    /**
     * Instantiates a new wynk error type.
     *
     * @param errorTitle the error title
     * @param errorMsg the error msg
     * @param httpResponseStatus the http response status
     */
    WynkErrorType(String errorTitle, String errorMsg, int httpResponseStatus, Marker marker) {
        this.errorTitle = errorTitle;
        this.errorMsg = errorMsg;
        this.httpResponseStatusCode = httpResponseStatus;
        this.marker = marker;
    }

    public static WynkErrorType getWynkErrorType(String name) {
        return WynkErrorType.valueOf(name);
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    @Override
    public String getErrorCode() {
        return this.name();
    }

    /**
     * Gets the error title.
     *
     * @return the error title
     */
    @Override
    public String getErrorTitle() {
        return errorTitle;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    @Override
    public String getErrorMessage() {
        return errorMsg;
    }

    /**
     * Gets the http response status.
     *
     * @return the http response status
     */
    @Override
    public int getHttpResponseStatusCode() {
        return httpResponseStatusCode;
    }

    @Override
    public Marker getMarker() {
        return marker;
    }
}
