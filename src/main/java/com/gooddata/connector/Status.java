package com.gooddata.connector;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import static com.gooddata.connector.Status.Code.ERROR;
import static com.gooddata.connector.Status.Code.SYNCHRONIZED;
import static com.gooddata.connector.Status.Code.USER_ERROR;

/**
 * Connector process status. Deserialization only.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    private final String code;
    private final String detail;
    private final String description;

    @JsonCreator
    Status(@JsonProperty("code") String code, @JsonProperty("detail") String detail,
           @JsonProperty("description") String description) {
        this.code = code;
        this.detail = detail;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true when the status means that the connector process has already finished
     * (no matter if it was successful).
     * NOTE: It also returns false in case of inability to resolve the code (e.g. API change)
     *
     * @return true when the status means that the connector process has already finished, false otherwise
     */
    @JsonIgnore
    public boolean isFinished() {
        return SYNCHRONIZED.name().equalsIgnoreCase(code) || isFailed();
    }

    /**
     * Returns true when the status means that the connector process failed.
     * NOTE: It also returns false in case of inability to resolve the code (e.g. API change)
     *
     * @return true when the status means that the connector process failed, false otherwise
     */
    @JsonIgnore
    public boolean isFailed() {
        return ERROR.name().equalsIgnoreCase(code) || USER_ERROR.name().equalsIgnoreCase(code);
    }


    /**
     * Enum of connector process status codes
     */
    public enum Code {
        NEW, SCHEDULED, DOWNLOADING, DOWNLOADED, TRANSFORMING, TRANSFORMED, UPLOADING, UPLOADED, SYNCHRONIZED,
        ERROR, USER_ERROR;
    }

}
