package com.github.blakefernandes.pulsar.items.action;

import com.github.blakefernandes.pulsar.items.action.types.Action;
import me.lucko.helper.menu.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.Map;

public class ItemActions {
    private Map<ClickType, List<Action>> actions;

    public ItemActions() {
    }

    public Item.Builder applyActions(Item.Builder itemBuilder) {
        for (Map.Entry<ClickType, List<Action>> entry : actions.entrySet()) {
            itemBuilder.bind(entry.getKey(), e -> {
                entry.getValue().forEach(action -> action.execute((Player) e.getWhoClicked()));
            });
        }

        return itemBuilder;
    }
}
