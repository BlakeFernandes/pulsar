package me.blake.pulsar.regions.selection;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SelectionData {
    @Getter
    private static final SelectionData instance = new SelectionData();
    private final Map<Player, SelectionLocation> selections = new HashMap<>();

    public void addLocation(Player player, Location location, Type type) {
        SelectionLocation selectionLocation = getSelectionLocation(player);

        if (selectionLocation == null) {
            selectionLocation = new SelectionLocation();
        }

        selectionLocation.setLocation(type, location);
        selections.put(player, selectionLocation);
    }

    public SelectionLocation getSelectionLocation(Player player) {
        return selections.getOrDefault(player, null);
    }

    public boolean hasLocation(Player player) {
        if (!selections.containsKey(player)) return false;
        SelectionLocation selectionLocation = selections.get(player);
        return selectionLocation == null || selectionLocation.hasLocation();
    }

    public boolean isSimilar(Player player, Type type, Location location) {
        SelectionLocation selectionLocation = selections.get(player);
        if (selectionLocation == null) return false;
        if (!selectionLocation.hasLocation()) return false;
        if (selectionLocation.getLocation(type) == null) return false;

        return selectionLocation.getLocation(type).equals(location);
    }

    public enum Type {
        FIRST_POSITION,
        SECOND_POSITION
    }
}
