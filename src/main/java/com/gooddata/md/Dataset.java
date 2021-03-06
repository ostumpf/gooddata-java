/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.md;

import com.gooddata.util.BooleanStringDeserializer;
import com.gooddata.util.BooleanStringSerializer;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents metadata dataset
 */
@JsonTypeName("dataSet")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Dataset extends AbstractObj implements Queryable, Updatable {

    private static final String DATA_UPLOADS_LINK = "dataUploads";
    private static final String UPLOAD_CONFIGURATION_LINK = "uploadConfiguration";

    @JsonProperty("content")
    private final Content content;

    @JsonProperty("links")
    private final Map<String, String> links;

    @JsonCreator
    private Dataset(@JsonProperty("meta") Meta meta, @JsonProperty("content") Content content,
            @JsonProperty("links") Map<String, String> links) {
        super(meta);
        this.content = content;
        this.links = links;
    }

    /* Just for serialization test */
    Dataset(String title) {
        this(new Meta(title), new Content(Collections.<String>emptyList(), null, Collections.<String>emptyList(), Collections.<String>emptyList(),
                Collections.<String>emptyList(), false), null);
    }

    @JsonIgnore
    public List<String> getTies() {
        return content.getTies();
    }

    @JsonIgnore
    public String getMode() {
        return content.getMode();
    }

    @JsonIgnore
    public List<String> getFacts() {
        return content.getFacts();
    }

    @JsonIgnore
    public List<String> getDataLoadingColumns() {
        return content.getDataLoadingColumns();
    }

    @JsonIgnore
    public List<String> getAttributes() {
        return content.getAttributes();
    }

    @JsonIgnore
    public boolean hasUploadConfiguration() {
        return content.hasUploadConfiguration();
    }

    @JsonIgnore
    public String getDataUploadsLink() {
        return links != null ? links.get(DATA_UPLOADS_LINK) : null;
    }

    @JsonIgnore
    public String getUploadConfigurationLink() {
        return links != null ? links.get(UPLOAD_CONFIGURATION_LINK) : null;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private static class Content {
        private final List<String> ties;
        private final String mode;
        private final List<String> facts;
        private final List<String> dataLoadingColumns;
        private final List<String> attributes;
        private final boolean hasUploadConfiguration;

        @JsonCreator
        private Content(@JsonProperty("ties") List<String> ties,
                @JsonProperty("mode") String mode,
                @JsonProperty("facts") List<String> facts,
                @JsonProperty("dataLoadingColumns") List<String> dataLoadingColumns,
                @JsonProperty("attributes") List<String> attributes,
                @JsonProperty("hasUploadConfiguration") @JsonDeserialize(using = BooleanStringDeserializer.class) boolean hasUploadConfiguration) {
            this.ties = ties;
            this.mode = mode;
            this.facts = facts;
            this.dataLoadingColumns = dataLoadingColumns;
            this.attributes = attributes;
            this.hasUploadConfiguration = hasUploadConfiguration;
        }

        public List<String> getTies() {
            return ties;
        }

        public String getMode() {
            return mode;
        }

        public List<String> getFacts() {
            return facts;
        }

        public List<String> getDataLoadingColumns() {
            return dataLoadingColumns;
        }

        public List<String> getAttributes() {
            return attributes;
        }

        @JsonProperty("hasUploadConfiguration")
        @JsonSerialize(using = BooleanStringSerializer.class)
        public boolean hasUploadConfiguration() {
            return hasUploadConfiguration;
        }
    }
}
