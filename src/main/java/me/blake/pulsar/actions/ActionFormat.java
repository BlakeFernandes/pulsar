package me.blake.pulsar.actions;

import me.blake.pulsar.actions.impl.CommandAction;
import me.blake.pulsar.actions.impl.MessageAction;
import me.blake.pulsar.actions.impl.TitleAction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionFormat {

    public static Optional<Action> from(@NotNull String action) {

        String actionType = StringUtils.substringBetween(action, "[", "]");
        String actionData = StringUtils.substringAfter(action, "] ");

        if (actionType == null) {
            Bukkit.getLogger().warning("[Library] Could not parse action " + action + " invalid action type!");
            return Optional.empty();
        }

        switch (actionType.toUpperCase()) {
            case "COMMAND" -> {
                return Optional.ofNullable(CommandAction.from(actionData));
            }

            case "MESSAGE" -> {
                return Optional.ofNullable(MessageAction.from(actionData));
            }

            case "TITLE" -> {
                return Optional.ofNullable(TitleAction.from(actionData));
            }
        }

        return Optional.empty();
    }

    public static List<Action> from(List<String> actionStrList) {
        List<Action> actions = new ArrayList<>();

        for (String actionStr : actionStrList) {
            Optional<Action> actionOptional = from(actionStr);
            actionOptional.ifPresent(actions::add);
        }

        return actions;
    }
}
