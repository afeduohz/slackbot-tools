package com.defa.slack.tools.command;

import java.util.Map;

public interface CommandContext<T extends CommandService> {
    T getService();
    String getPrincipal();
    String getStream();
    Map<String, Object> getParameters();
    <R> R getParameter(final String key, final Class<R> cls);
    void addParameter(final String key, final Object value);
}
