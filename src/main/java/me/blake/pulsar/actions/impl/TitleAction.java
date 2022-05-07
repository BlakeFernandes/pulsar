package me.blake.pulsar.actions.impl;

import lombok.AllArgsConstructor;
import me.blake.pulsar.actions.Action;
import me.blake.pulsar.messaging.Message;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class TitleAction extends Action {

    private String title;
    private String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public static Action from(String rawAction) {
        String[] rawActionValues = rawAction.split(";");

        System.out.println("Raw Action Values: " + rawActionValues.length);
//        switch (rawActionValues.length) {
//            case
//        }
        return new TitleAction(rawAction, "", 0, 70, 0);
    }

    @Override
    public void execute(Player player, Message.Placeholder[] placeholders) {
        player.sendTitle(
                Text.colorize(new Message(title).transform(placeholders).firstString()),
                Text.colorize(new Message(subtitle).transform(placeholders).firstString()),
                fadeIn,
                stay,
                fadeOut
        );
    }

    @Override
    public void execute(Player player) {
        player.sendTitle(Text.colorize(title),
                subtitle,
                fadeIn,
                stay,
                fadeOut
        );
    }
}
