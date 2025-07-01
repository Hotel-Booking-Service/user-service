package com.hbs.userservice.resolver;

public interface EntityResolver<T, K> {
    T resolveById(K id);

}
