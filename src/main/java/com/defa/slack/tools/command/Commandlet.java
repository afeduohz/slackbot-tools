package com.defa.slack.tools.command;

public interface Commandlet<T extends CommandService> {
    String getName();
    void execute(final CommandContext<T> context, final String[] args) throws CommandException;
}
