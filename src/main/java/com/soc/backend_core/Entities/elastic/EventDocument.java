package com.soc.backend_core.Entities.elastic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(indexName = "soc-events")
public class EventDocument {

    @Id
    private String eventId;

    @Field(type = FieldType.Keyword)
    private String deviceId;

    @Field(type = FieldType.Keyword)
    private String eventType;

    @Field(type = FieldType.Keyword)
    private String sourceIp;

    @Field(type = FieldType.Keyword)
    private String destinationIp;

    @Field(type = FieldType.Keyword)
    private String process;

    @Field(type = FieldType.Keyword)
    private String user;

    @Field(type = FieldType.Keyword)
    private String severity;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    @Field(type = FieldType.Object)
    private Map<String, Object> raw;
}
