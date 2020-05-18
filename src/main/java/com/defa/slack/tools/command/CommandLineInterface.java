package com.defa.slack.tools.command;

import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class CommandLineInterface<T extends CommandService> implements CommandServlet<T> {
    private final List<CommandFilter<T>> filters;
    private final Map<String, Commandlet<T>> commands;
    private final T service;

    public CommandLineInterface(final T service){
        if(service == null) throw new IllegalArgumentException();
        this.service = service;
        this.commands = new HashMap<>();
        this.filters = new ArrayList<>();
        CommandPackage pkg = this.getClass().getAnnotation(CommandPackage.class);
        if(pkg != null) {
            final String cmdPackage = pkg.value();
            Reflections f = new Reflections(cmdPackage);
            Set<Class<? extends AbstractCommandlet>> set = f.getSubTypesOf(AbstractCommandlet.class);
            set.forEach(cls->{
                try {
                    ParameterizedType type = (ParameterizedType) cls.getGenericSuperclass();
                    Class<?> c = (Class<?>) type.getActualTypeArguments()[0];
                    if(c.equals(service.getClass())){
                        AbstractCommandlet<T> let = (AbstractCommandlet<T>) cls.newInstance();
                        this.register(let);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public T getService() {
        return this.service;
    }

    @Override
    public void register(final Commandlet<T> command) {
        if(command != null) {
            commands.putIfAbsent(command.getName(), command);
        }
    }

    @Override
    public void filter(final CommandFilter<T> filter) {
        if(filter != null) {
            filters.add(filter);
        }
    }

    @Override
    public void serve(final CommandContext<T> context) throws CommandException {
        final String command = context.getStream();
        final String principal = context.getPrincipal();
        CommandIndicator indicator = CommandIndicator.create(command);
        for (CommandFilter<T> filter : filters) {
            filter.doFilter(service, indicator, principal);
        }
        this.dispatch(context, indicator);
    }

    private void dispatch(final CommandContext<T> context, CommandIndicator indicator) throws CommandException {
        final String app = indicator.getApp();
        final String[] args = indicator.getArgs();
        if(commands.containsKey(app)) {
            commands.get(app).execute(context, args);
        }
        throw new CommandException(String.format("Unexpect command %s.", app));
    }

}
