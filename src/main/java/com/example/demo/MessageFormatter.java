package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.debezium.DebeziumConstants;

import java.util.HashMap;
import java.util.Map;

public class MessageFormatter implements Processor {

    public void process(Exchange exchange) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        final Map message = new HashMap();
        final Map value = exchange.getMessage().getBody(Map.class);
        message.put("Body", value);
        final Map meta = exchange.getMessage().getHeader(DebeziumConstants.HEADER_SOURCE_METADATA, Map.class);
        if (meta != null) {
            message.put(DebeziumConstants.HEADER_SOURCE_METADATA, meta);
        }
        final Map identifier = exchange.getMessage().getHeader(DebeziumConstants.HEADER_IDENTIFIER, Map.class);
        if (identifier != null) {
            message.put(DebeziumConstants.HEADER_IDENTIFIER, identifier);
        }
        final Map key = exchange.getMessage().getHeader(DebeziumConstants.HEADER_KEY, Map.class);
        if (key != null) {
            message.put(DebeziumConstants.HEADER_KEY, key);
        }
        final Map operation = exchange.getMessage().getHeader(DebeziumConstants.HEADER_OPERATION, Map.class);
        if (operation != null) {
            message.put(DebeziumConstants.HEADER_OPERATION, operation);
        }
        final Map timestamp = exchange.getMessage().getHeader(DebeziumConstants.HEADER_TIMESTAMP, Map.class);
        if (timestamp != null) {
            message.put(DebeziumConstants.HEADER_TIMESTAMP, timestamp);
        }
        final Map before = exchange.getMessage().getHeader(DebeziumConstants.HEADER_BEFORE, Map.class);
        if (before != null) {
            message.put(DebeziumConstants.HEADER_BEFORE, before);
        }
        final Map ddl = exchange.getMessage().getHeader(DebeziumConstants.HEADER_DDL_SQL, Map.class);
        if (ddl != null) {
            message.put(DebeziumConstants.HEADER_DDL_SQL, ddl);
        }
        exchange.getIn().setBody(gson.toJson(message));
    }
}
