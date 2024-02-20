package com.github.blakefernandes.pulsar.region.selection

import com.github.blakefernandes.pulsar.region.Cuboid
import org.bukkit.Location
import org.bukkit.entity.Player

abstract class AbstractListSelection(
    private var selections: HashMap<Player, Selection> = HashMap<Player, Selection>()
) {
    fun addLocation(player: Player, location: Location, type: Selection.Type) {
        val selection = getSelectionLocation(player) ?: Selection(Cuboid.Builder().build())
        selection.setLocation(type, location)
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