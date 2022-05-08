package me.blake.pulsar.region.selection

import org.bukkit.Location

class Selection(
    private var location1: Location? = null,
    private var location2: Location? = null
) {
    fun setLocation(type: Type, location: Location) {
        if (type == Type.FIRST_POSITION) {
            location1 = location
        } else {
            location2 = location
        }
    }

    fun getLocation(type: Type): Location? {
        return if (type == Type.FIRST_POSITION) {
            location1
        } else {
            location2
        }
    }

    fun hasLocation(): Boolean {
        return location1 != null && location2 != null
    }

    fun equals(type: Type, location: Location): Boolean {
        return getLocation(type)?.equals(location) ?: false
    }

    enum class Type {
        FIRST_POSITION,
        SECOND_POSITION
    }
}