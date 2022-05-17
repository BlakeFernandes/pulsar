package me.blake.pulsar.utils

import org.bukkit.Bukkit
import org.bukkit.Location

class LocationUtil {
    companion object {
        fun stringFromLoc(loc: Location?): String {
            if (loc == null) return ""

            return (loc.world!!.name + ":" + loc.x + ":" + loc.y + ":" + loc.z + ":" + loc.yaw
                    + ":" + loc.pitch)
        }

        fun locFromString(string: String): Location? {
            if (!string.contains(":")) return null
            val loc = string.split(":").toTypedArray()
            return Location(Bukkit.getWorld(loc[0]), loc[1].toDouble(), loc[2].toDouble(), loc[3].toDouble(), loc[4].toDouble().toFloat(), loc[5].toDouble().toFloat())
        }
    }
}