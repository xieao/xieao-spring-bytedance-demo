package com.bytedance.mossey.demo.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableTraceSerializer extends JsonSerializer<Throwable> {

    @Override
    public void serialize(Throwable exception, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString().replace("\n", "\\n");
        gen.writeRawValue("\"" + stackTrace + "\"");
    }


}