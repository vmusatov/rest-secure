package com.restsecure.core.http.header;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.configuration.configs.OverwriteAllHeadersConfig;
import com.restsecure.core.configuration.configs.OverwriteHeadersConfig;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.core.util.SerializeHelper;

import java.util.List;

import static com.restsecure.core.http.OverwriteValuesHelper.overwriteAllValues;
import static com.restsecure.core.http.OverwriteValuesHelper.overwriteValues;

@ProcessAll
public class HeadersProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> SerializeHelper.serializeValuesIfNeed(headers, mapper));

        Boolean needOverwriteAll = context.getConfigValue(OverwriteAllHeadersConfig.class);
        if (needOverwriteAll) {
            overwriteAllValues(headers);
        } else {
            List<String> headersToOverwrite = context.getConfigValue(OverwriteHeadersConfig.class);
            overwriteValues(headers, headersToOverwrite);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
