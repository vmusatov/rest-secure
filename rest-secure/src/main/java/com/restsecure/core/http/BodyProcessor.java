package com.restsecure.core.http;

import com.restsecure.core.configuration.configs.SerializerConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.mapping.serialize.Serializer;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

@ProcessAll
public class BodyProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Object body = context.getSpecification().getBody();
        Serializer serializer = context.getConfigValue(SerializerConfig.class);

        if (SerializeHelper.isNeedSerialize(body)) {
            String serializedBody = serializer.serialize(body);
            context.getSpecification().body(serializedBody);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
