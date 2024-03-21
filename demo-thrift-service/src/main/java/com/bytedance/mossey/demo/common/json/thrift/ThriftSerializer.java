package com.bytedance.mossey.demo.common.json.thrift;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TSimpleJSONProtocol;

import java.io.IOException;

public class ThriftSerializer extends JsonSerializer<TBase> {
    @Override
    public void serialize(TBase value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        try {
            TProtocolFactory f = new TSimpleJSONProtocol.Factory();
            String s = new TSerializer(f).toString(value);
            jgen.writeRawValue(s);
        }
        catch (TException e) {
            throw new IOException(e);
        }
    }
}