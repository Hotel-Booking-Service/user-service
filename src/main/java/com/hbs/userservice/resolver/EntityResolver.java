package com.hbs.userservice.resolver;

public interface EntityResolver<T> {
    T resolveById(Long id);
}
