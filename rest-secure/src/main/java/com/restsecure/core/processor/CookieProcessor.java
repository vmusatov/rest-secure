package com.restsecure.core.processor;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.core.util.SerializeHelper;

@ProcessAll
public class CookieProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        serializeCookies(context);
    }

    private void serializeCookies(RequestContext context) {
        MultiKeyMap<String, Object> cookiesWithValueToSerialize = context.getSpecification().getCookiesWithValueToSerialize();
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> SerializeHelper.serializeValuesIfNeed(cookiesWithValueToSerialize, mapper));

        cookiesWithValueToSerialize.forEach(cookie -> headers.put("Cookie", cookie.getKey() + "=" + cookie.getValue().toString()));
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 99;
    }
}
