package com.defa.slack.tools.command;

public interface CommandFilter<T extends CommandService> {
    void doFilter(final T service, final CommandIndicator indicator, final String principal) throws CommandException;
}
