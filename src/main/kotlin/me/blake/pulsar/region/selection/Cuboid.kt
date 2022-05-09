package me.blake.pulsar.region.selection

import org.bukkit.Location

class Cuboid private constructor(
    val location1: Location?,
    val location2: Location?
) {
    data class Builder(
        private var location1: Location? = null,
        private var location2: Location? = null) {
        fun location1(location1: Location?) = apply { this.location1 = location1 }
        fun location2(location2: Location?) = apply { this.location2 = location2 }
        fun build() = Cuboid(location1, location2)
    }

    private fun contains(x: Double, y: Double, z: Double): Boolean {
        return if (location1 == null || location2 == null) false
        else x in location1.x .. location2.x && y >= location1.y && y <= location2.y && z >= location1.z && z <= location2.z
    }

    operator fun contains(l: Location): Boolean {
        return if (location1?.world?.name != l.world!!.name) false else this.contains(l.x, l.y, l.z)
    }
}