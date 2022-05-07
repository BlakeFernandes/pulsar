package me.blake.pulsar.actions.impl;

import lombok.AllArgsConstructor;
import me.blake.pulsar.actions.Action;
import me.blake.pulsar.messaging.Message;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class MessageAction extends Action {

    private String message;

    public static Action from(String rawAction) {
        return new MessageAction(rawAction);
    }

    @Override
    public void execute(Player player, Message.Placeholder[] placeholders) {
        new Message(message).send(player, placeholders);
    }

    @Override
    public void execute(Player player) {
        new Message(message).send(player);
    }
}
