package me.blake.pulsar.actions.impl;

import lombok.AllArgsConstructor;
import me.blake.pulsar.actions.Action;
import me.blake.pulsar.messaging.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandAction extends Action {

    private String command;

    public static Action from(String rawAction) {
        return new CommandAction(rawAction);
    }

    @Override
    public void execute(Player player, Message.Placeholder[] placeholders) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), new Message(command).transform(placeholders).asList().get(0));
    }

    @Override
    public void execute(Player player) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), new Message(command).firstString());
    }
}
