package com.defa.slack.tools.command;

public interface CommandFilter<T extends CommandService> {
    void doFilter(final CommandContext<T> context, final CommandIndicator indicator) throws CommandException;
}
