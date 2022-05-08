package me.blake.pulsar.region

import org.bukkit.Location

class CuboidUtil(
    l1: Location,
    l2: Location
) {
    private var worldName: String? = null
    private var x1 = 0; private var y1 = 0; private var z1 = 0
    private var x2 = 0; private var y2 = 0; private var z2 = 0

    init {
        require(l1.world == l2.world) { "Locations must be on the same world" }
        worldName = l1.world!!.name
        x1 = l1.blockX.coerceAtMost(l2.blockX)
        y1 = l1.blockY.coerceAtMost(l2.blockY)
        z1 = l1.blockZ.coerceAtMost(l2.blockZ)
        x2 = l1.blockX.coerceAtLeast(l2.blockX)
        y2 = l1.blockY.coerceAtLeast(l2.blockY)
        z2 = l1.blockZ.coerceAtLeast(l2.blockZ)
    }

    private fun contains(x: Int, y: Int, z: Int): Boolean {
        return x in x1..x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2
    }

    operator fun contains(l: Location): Boolean {
        return if (worldName != l.world!!.name) false else this.contains(l.blockX, l.blockY, l.blockZ)
    }

}