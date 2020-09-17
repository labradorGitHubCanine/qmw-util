package com.qmw.service.impl;

import com.alibaba.fastjson.JSON;
import com.qmw.entity.ResponseStatus;
import com.qmw.entity.Token;
import com.qmw.exception.CustomException;
import com.qmw.service.TokenService;
import com.qmw.util.JSONUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenServiceMemory<T> implements TokenService<T> {

    private static final Map<String, Token> TOKEN_MAP = new HashMap<>();

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Override
    public Token init(boolean create) {
        Token token = TOKEN_MAP.get(request.getHeader(TOKEN_KEY));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (token == null) {
            if (!create)
                return null;
            String id = UUID.randomUUID().toString();
            token = new Token()
                    .setId(id)
                    .setUpdateTime(now)
                    .setCreateTime(now);
            TOKEN_MAP.put(id, token);
        } else {
            token.setUpdateTime(now);
        }
        response.setHeader("Access-Control-Expose-Headers", TOKEN_KEY);
        response.addHeader(TOKEN_KEY, token.getId());
        System.out.println(TOKEN_MAP);
        return token;
    }

    @Override
    public Token init() {
        return init(true);
    }

    @Override
    public void set(T entity) {
        init().setContent(JSON.toJSONString(entity));
    }

    @Override
    public T get() {
        Token token = init(false);
        if (token == null || !JSONUtil.isValidObject(token.getContent()))
            throw new CustomException(ResponseStatus.RELOGIN);
        T entity;
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericInterfaces()[0];
            Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
            entity = JSON.parseObject(token.getContent(), clazz);
            if (entity == null)
                throw new CustomException(ResponseStatus.RELOGIN);
        } catch (Exception e) {
            throw new CustomException(ResponseStatus.RELOGIN);
        }
        return entity;
    }

    @Override
    public void remove() {
    }

}
