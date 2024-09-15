package com.sh.oauth2.converters;

public interface ProviderUserConverter<T, R> {

    R convert(T t);
}
