package com.example.mds.resolver;

import com.example.mds.entity.RequestMappingInfo;

public class ResolverFactory {
    public static Resolver getResolver(RequestMappingInfo mappingInfo) {
        if (mappingInfo.isRestController()) {
            return new MessageConverter();
        } else {
            return new ViewResolver();
        }
    }
}
