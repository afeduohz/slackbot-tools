package com.defa.slack.tools.command;

import java.util.List;

public class CommandException extends Exception {
    public CommandException(final String message) {
        super(message);
    }

    public static CommandException create(final String msg) {
        return new CommandException(msg);
    }

    public static CommandException internalError(){
        return new CommandException("Internal Error.");
    }

    public static CommandException unknownUser() {
        return new CommandException("Unknown user.");
    }

    public static CommandException illegalArguments() {
        return new CommandException("Illegal Arguments.");
    }

    public static CommandException illegalArguments(final String p) {
        return new CommandException(String.format("Illegal Arguments. %s", p));
    }

    public static CommandException illegalValue(final String p, final List<String> suggestion) {
        final String q = String.join(", ", suggestion);
        return new CommandException(String.format("Illegal Argument value %s, please pick one of [%s]", p, q));
    }
}
