package com.restsecure.core.http;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.util.SerializeHelper;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

@ProcessAll
public class BodyProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Object body = context.getSpecification().getBody();
        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> {
                    if (SerializeHelper.isNeedSerialize(body)) {
                        String serializedBody = mapper.serialize(body);
                        context.getSpecification().body(serializedBody);
                    }
                });
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
