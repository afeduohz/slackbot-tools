package com.defa.slack.tools.command;

public abstract class AbstractCommandlet<T extends CommandService> implements Commandlet<T>{
    private final String name;

    public AbstractCommandlet() {
        Command cmd = this.getClass().getAnnotation(Command.class);
        if(cmd == null) {
            throw new CommandMissingNameException();
        } else {
            this.name = cmd.value();
        }
    }

    protected AbstractCommandlet(final String name) {
        this.name = name;
    }

    @Override
    public String getName(){
        return this.name;
    }

}
