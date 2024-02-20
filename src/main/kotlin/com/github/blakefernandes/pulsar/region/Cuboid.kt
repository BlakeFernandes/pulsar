package com.github.blakefernandes.pulsar.region

import com.github.blakefernandes.pulsar.utils.LocationUtil
import org.bukkit.Location

class Cuboid constructor(
    val location1: Location? = null,
    val location2: Location? = null
) {
    data class Builder(
        private var location1: Location? = null,
        private var location2: Location? = null
    ) {
        fun location1(location1: Location?) = apply { this.location1 = location1 }
        fun location2(location2: Location?) = apply { this.location2 = location2 }
        fun build() = Cuboid(location1, location2)
    }

    companion object {
        fun from(str: String): Cuboid {
            val str = str.split(";")
            val location1 = LocationUtil.locFromString(str[0])
            val location2 = LocationUtil.locFromString(str[1])
            return Cuboid(location1, location2)
        }
    }

    fun center(): Location? {
        if (location1 == null && location2 == null) return null;
        if (location2 == null) return location1;
        if (location1 == null) return location2;

        val x = (location1.x + location2.x) / 2
        val y = (location1.y + location2.y) / 2
        val z = (location1.z + location2.z) / 2
        return Location(location1.world, x, y, z)
    }

    private fun contains(x: Double, y: Double, z: Double): Boolean {
        return if (location1 == null || location2 == null) false
        else x in location1.x .. location2.x && y >= location1.y && y <= location2.y && z >= location1.z && z <= location2.z
    }

    fun contains(l: Location): Boolean {
        return if (location1?.world?.name != l.world!!.name) false else this.contains(l.x, l.y, l.z)
    }

    fun clone(): Cuboid {
        return Cuboid(location1, location2)
    }

    override fun toString(): String {
        return LocationUtil.stringFromLoc(location1) + ";" + LocationUtil.stringFromLoc(location2)
    }
}