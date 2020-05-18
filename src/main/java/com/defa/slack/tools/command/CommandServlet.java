package com.defa.slack.tools.command;

public interface CommandServlet<T extends CommandService> {
    T getService();
    void register(Commandlet<T> command);
    void filter(CommandFilter<T> filter);
    void serve(final CommandContext<T> context) throws CommandException;
}
