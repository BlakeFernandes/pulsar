package me.blake.pulsar.region.selection

import org.bukkit.Location

abstract class AbstractSelection(
    val selection: Selection
) {
    fun plotLine(p1: Location, p2: Location): List<Location> {
        val vectors: ArrayList<Location> = ArrayList()

        val length: Int = p1.distance(p2).toInt()
        val points: Double = (length / 0.5) + 1
        val gap = length / (points - 1)
        val gapVector: Location = p2.clone().subtract(p1).multiply(gap).multiply(1.0 / length)

        for (i in 0 until points.toInt()) {
            val currentPoint: Location = p1.clone().add(gapVector.clone().multiply(i.toDouble())).clone()
            vectors.add(currentPoint)
        }
        return vectors
    }

    fun addLocation(location: Location, type: Selection.Type) {
        selection.setLocation(type, location)
    }

    fun isSimilar(type: Selection.Type, location: Location): Boolean {
        return selection.getLocation(type)?.equals(location) ?: false
    }
}