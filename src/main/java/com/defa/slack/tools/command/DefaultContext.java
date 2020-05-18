package com.defa.slack.tools.command;

import java.util.HashMap;
import java.util.Map;

public class DefaultContext<T extends CommandService> implements CommandContext<T> {
    private final T service;
    private final String principal;
    private final String stream;
    private final Map<String, Object> parameters;

    public DefaultContext(final T service, final String principal, final String stream) {
        this.service = service;
        this.principal = principal;
        this.stream = stream;
        this.parameters = new HashMap<>();
    }

    @Override
    public T getService() {
        return this.service;
    }

    @Override
    public String getPrincipal(){
        return this.principal;
    }

    @Override
    public String getStream() {
        return this.stream;
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public Object getParameter(final String key) {
        return this.parameters.getOrDefault(key, null);
    }

    @Override
    public <S> S getParameter(final String key, final Class<S> cls) {
        return cls.cast(getParameter(key)) ;
    }

    @Override
    public void addParameter(final String key, final Object value) {
        this.parameters.put(key, value);
    }

}
