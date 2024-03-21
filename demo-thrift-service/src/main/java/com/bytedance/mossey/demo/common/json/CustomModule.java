package com.bytedance.mossey.demo.common.json;

import com.bytedance.mossey.demo.common.json.serializer.ThrowableTraceSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomModule {

    public static final SimpleModule ThrowableTraceSerializerModule = new SimpleModule()
        .addSerializer(Throwable.class, new ThrowableTraceSerializer());
}
