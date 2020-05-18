package com.defa.slack.tools.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandIndicator {
    private static final String CMD_FMT = "^\\s*(<@\\w+>)\\s*(/[a-zA-Z0-9\\-]+)(\\s+(.*))?$";
    private static final Pattern P = Pattern.compile(CMD_FMT);

    private String bot;
    private String app;
    private String[] args;

    private CommandIndicator() {}

    public static CommandIndicator create(final String command) throws CommandException{
        final String msg = "Bad formatted command.";
        if(command == null) throw CommandException.create(msg);
        Matcher matcher = P.matcher(command);
        if(!matcher.find()) throw new CommandException(msg);
        CommandIndicator indicator = new CommandIndicator();
        indicator.bot = matcher.group(1);
        indicator.app = matcher.group(2);
        indicator.args = (matcher.group(3) == null ? "": matcher.group(3)).split("\\s");

        return indicator;
    }

    public String getBot() {
        return bot;
    }

    public String getApp() {
        return app;
    }

    public String[] getArgs() {
        return args;
    }
}
