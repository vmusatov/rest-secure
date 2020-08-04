package com.restsecure.core.http;

import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

@ProcessAll
public class BodyProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Object body = context.getSpecification().getBody();
        SerializeConfig serializeConfig = context.getConfig(SerializeConfig.class);

        if (SerializeHelper.isNeedSerialize(body)) {
            String serializedBody = serializeConfig.getSerializer().serialize(body);
            context.getSpecification().body(serializedBody);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
