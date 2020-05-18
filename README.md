# slackbot-tools
Useful tools for slack bot.
##Command

###Command line interface stub
You only need to create a stub class for commands, and an annotation `@CommandPackage` which identify the package where commands defined.

In additionally, you can register any `filter` to restrict the message flow. These filters are global for every messages.
```java
@CommandPackage("x.x.commands")
public final class Cli extends CommandLineInterface<Service> {

    public Cli(Service service) {
        super(service);

        /*
         * Register filters for restrictions.
         */
        filter((context, indicator)->{
            //blah blah ...
        });

        filter((context, indicator)->{
            //If it gonna false positive, just throw CommandException or its subclass.
            if(null == context.getService().getUserBySlackId(context.getService().getPrincipal())) throw new AccessDeniedException("You cannot access!");
        });

    }
}
```
In commands definition package, `@Command` annotation define the actual command name. Your class just extend from `AbstractCommandlet<T>`, in which T extends from CommandService.

### Command package definition
You can create classes extend from AbstractCommandlet. that's all.

./commands/BasicCommand.java
```java
public class BasicCommand extends AbstractCommandlet<Service> {

    @Override
    public void execute(CommandContext<Service> context, String[] args) throws CommandException {
        final String principal = context.getPrincipal();
        final Methods methods = context.getParameter("methods", Methods.class);
        final String channel = context.getParameter("channel", String.class);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("blocks", product(context, args));
            printResponse(params.get("blocks"));
            methods.chatPostEphemeral(channel, ":smile:", principal, params, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String product(CommandContext<Service> context, String[] args) throws CommandException;

}
```
./commands/CommandSelectData.java
```java
@Command("/select-data")
public final class CommandSelectData extends BasicCommand {

    protected String product(final CommandContext<Service> context, final String[] args) throws CommandException{
        final Service service = context.getService();
        final String principal = context.getPrincipal();
        Blocks blocks = Blocks.create();
        ...
        return blocks.format();
    }

}
```
./commands/CommandUpdateData.java
```java
@Command("/update-data")
public final class CommandUpdateData extends BasicCommand {

    protected String product(final CommandContext<Service> context, final String[] args) throws CommandException{
        final Service service = context.getService();
        final String principal = context.getPrincipal();
        Blocks blocks = Blocks.create();
        ...
        return blocks.format();
    }

}
```