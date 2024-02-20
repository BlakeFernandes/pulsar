package com.github.blakefernandes.pulsar.items.action.types;

import lombok.AllArgsConstructor;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class MessageAction extends Action {
    private String message;

    @Override
    public void execute(Player player) {
        player.sendMessage(Text.colorize(message));
    }
}
