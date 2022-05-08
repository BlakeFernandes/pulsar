package me.blake.pulsar.region.selection

import org.bukkit.Location
import org.bukkit.entity.Player

class DefaultSelection {
    private var selections: HashMap<Player, Selection> = HashMap<Player, Selection>()

    fun addLocation(player: Player, loccation: Location, type: Selection.Type) {
        val selection = getSelectionLocation(player) ?: Selection()
        selection.setLocation(type, loccation)
        selections[player] = selection
    }

    fun getSelectionLocation(player: Player): Selection? {
        return selections[player]
    }

    fun hasLocation(player: Player): Boolean {
        return selections[player]?.hasLocation() ?: false
    }

    fun isSimilar(player: Player, type: Selection.Type, location: Location): Boolean {
        return selections[player]?.getLocation(type)?.equals(location) ?: false
    }
}