package me.blake.pulsar.commands;

import com.google.common.collect.ImmutableList;
import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommand {
    protected final TerminableConsumer terminableConsumer;
    private final String[] commands;
    private final String permission;
    private final Map<String, Command> commandMap;
    private final Map<Command, List<String>> permissionMap;

    protected AbstractCommand(TerminableConsumer consumer, String permission, String... commands) {
        this.terminableConsumer = consumer;
        this.permission = permission;
        this.commands = commands;
        this.commandMap = new HashMap<>();
        this.permissionMap = new HashMap<>();
    }

    public void addCommand(Command commandModule, String... args) {
        for (String arg : args) {
            this.commandMap.put(arg, commandModule);
        }
        this.permissionMap.put(commandModule, List.of(args));
    }

    public abstract void handleCommand(CommandContext<CommandSender> handler, ImmutableList<String> args);

    public void register() {
        Commands.create()
                .assertPermission(permission)
                .handler(h -> {
                    if (h.args().size() == 0) {
                        this.handleCommand(h, h.args());
                        return;
                    }

                    Command command = this.commandMap.get(h.rawArg(0));

                    if (command != null) {
                        if (hasPermission(h.sender(), command)) {
                            command.execute(h.sender(), h.args());
                        } else {
                            h.sender().sendMessage("&cYou do not have permission to use this command.");
                        }
                    } else {
                        this.handleCommand(h, h.args());
                    }
                }).registerAndBind(terminableConsumer, commands);
    }

    private boolean hasPermission(CommandSender player, Command command) {
        List<String> commandNames = this.permissionMap.get(command);
        for (String cmd : commandNames) {
            if (player.hasPermission(this.permission + "." + cmd)) {
                return true;
            }
        }
        return false;
    }
}
