package me.blake.pulsar.region.selection

import me.blake.pulsar.region.Cuboid
import org.bukkit.Location

class Selection(
    private var cuboid: Cuboid
) {
    fun setLocation(type: Type, location: Location?) {
        cuboid = if (type == Type.FIRST_POSITION) {
            Cuboid.Builder().location1(location).location2(cuboid.location2).build()
        } else {
            Cuboid.Builder().location1(cuboid.location1).location2(location).build()
        }
    }

    fun getLocation(type: Type): Location? {
        return if (type == Type.FIRST_POSITION) {
            cuboid.location1
        } else {
            cuboid.location2
        }
    }

    fun hasLocation(): Boolean {
        return cuboid.location1 != null && cuboid.location2 != null
    }

    fun equals(type: Type, location: Location): Boolean {
        return getLocation(type)?.equals(location) ?: false
    }

    fun contains(location: Location): Boolean {
        return cuboid.contains(location)
    }

    fun clone(): Selection {
        return Selection(cuboid.clone())
    }

    enum class Type {
        FIRST_POSITION,
        SECOND_POSITION
    }
}