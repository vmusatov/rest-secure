package com.restsecure.core.processor;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.SerializeHelper;

@ProcessAll
public class BodyProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Object body = context.getRequestSpec().getBody();
        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> {
                    if (SerializeHelper.isNeedSerialize(body)) {
                        String serializedBody = mapper.serialize(body);
                        context.getRequestSpec().body(serializedBody);
                    }
                });
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
