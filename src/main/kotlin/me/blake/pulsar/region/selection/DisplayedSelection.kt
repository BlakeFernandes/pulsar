package me.blake.pulsar.region.selection

import me.blake.pulsar.messaging.Message
import me.lucko.helper.Events
import me.lucko.helper.Schedulers
import me.lucko.helper.scheduler.Task
import me.lucko.helper.terminable.composite.CompositeTerminable
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector


class DisplayedSelection(
    private val selection: Selection,
    private val itemStack: ItemStack,
    private val particle: Particle = Particle.REDSTONE,
    private val redrawInterval: Long = 10L
): Runnable {
    private val composite: CompositeTerminable = CompositeTerminable.create()

    init {
        registerEvent()
        registerScheduler()
    }

    private fun registerEvent() {
        Events.subscribe(PlayerInteractEvent::class.java)
            .filter { e -> e.clickedBlock != null }
            .filter { e -> e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.LEFT_CLICK_BLOCK }
            .filter { e -> e.player.inventory.itemInMainHand.isSimilar(itemStack) }
            .handler { e ->
                e.isCancelled = true

                val clickedLocation = e.clickedBlock!!.location
                val type: String

                if (e.action == Action.RIGHT_CLICK_BLOCK) {
                    type = "2nd"
                    if (selection.equals(Selection.Type.SECOND_POSITION, clickedLocation)) return@handler
                    selection.setLocation(Selection.Type.SECOND_POSITION, clickedLocation)
                } else {
                    type = "1st"
                    if (selection.equals(Selection.Type.FIRST_POSITION, clickedLocation)) return@handler
                    selection.setLocation(Selection.Type.FIRST_POSITION, clickedLocation)
                }

                Message.getOrDefault("selected_location", Message("&aYou have selected the %type% position!"))
                    .send(e.player, Message.Placeholder("type", type))
            }.bindWith(composite)
    }

    private fun registerScheduler() {
        val task: Task = Schedulers.sync().runRepeating(this, 0, redrawInterval)
        task.bindWith(composite)
    }

    private fun plotLine(p1: Location, p2: Location): List<Location> {
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

    override fun run() {
        val loc1: Location? = selection.getLocation(Selection.Type.FIRST_POSITION)?.clone()
        val loc2: Location? = selection.getLocation(Selection.Type.SECOND_POSITION)?.clone()

        loc1?.add(0.5,0.5,0.5);
        loc2?.add(0.5,0.5,0.5);

        if (loc2 != null) {
            if (loc1 == null) {
                loc2.world?.spawnParticle(particle, loc2, 1, Particle.DustOptions(Color.AQUA, 1F))
            }

            if (loc1 != null) {
                val vectors: List<Location> = plotLine(loc1, loc2)

                for (vector in vectors) {
                    loc2.world?.spawnParticle(particle, vector, 1, Particle.DustOptions(Color.AQUA, 1F))
                }
            }
        }

        if (loc1 != null) {
            if (loc2 == null) {
                loc1.world?.spawnParticle(particle, loc1, 1, Particle.DustOptions(Color.AQUA, 1F))
            }
        }
    }
}