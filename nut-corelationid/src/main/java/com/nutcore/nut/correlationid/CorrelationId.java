package com.nutcore.nut.correlationid;

/**
 * Created by davidecerbo on 07/11/2016.
 */
public class CorrelationId
{
    private final String id;
    private final String source;
    private final Long creationTime;

    public CorrelationId(String id, String source, Long creationTime)
    {
        this.id = id;
        this.source = source;
        this.creationTime = creationTime;
    }

    public String getId()
    {
        return id;
    }

    public String getSource()
    {
        return source;
    }

    public Long getCreationTime()
    {
        return creationTime;
    }
}
