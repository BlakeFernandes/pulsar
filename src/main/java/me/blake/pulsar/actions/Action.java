package me.blake.pulsar.actions;

import me.blake.pulsar.messaging.Message;
import org.bukkit.entity.Player;

public abstract class Action {
    public abstract void execute(Player player, Message.Placeholder... placeholders);
    public abstract void execute(Player player);
}
