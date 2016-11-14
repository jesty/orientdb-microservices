package com.nutcore.nut.correlationid;

/**
 * Created by davidecerbo on 04/11/2016.
 */
public class CorrelationIdUtil
{

    private static final ThreadLocal<CorrelationId> db = new ThreadLocal<>();

    public static CorrelationId getId()
    {
        return db.get();
    }

    static void setId(CorrelationId oObjectDatabaseTx)
    {
        db.set(oObjectDatabaseTx);
    }

}
