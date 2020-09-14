package com.qmw.service;

import com.qmw.entity.Token;

public interface TokenService<T> {

    String TOKEN_KEY = "token";

    Token init(boolean create);

    Token init();

    void set(T entity);

    T get();

    void remove();

}
