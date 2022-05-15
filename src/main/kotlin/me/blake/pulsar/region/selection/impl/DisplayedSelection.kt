package me.blake.pulsar.region.selection.impl

import me.blake.pulsar.messaging.Message
import me.blake.pulsar.region.selection.AbstractSelection
import me.blake.pulsar.region.selection.Selection
import me.lucko.helper.Events
import me.lucko.helper.Schedulers
import me.lucko.helper.scheduler.Task
import me.lucko.helper.terminable.composite.CompositeTerminable
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.ItemStack


class DisplayedSelection private constructor (
    private val _selection: Selection,
    private val player: Player,
    private val itemStack: ItemStack,
    private val selectedSelectionParticle: Particle = Particle.REDSTONE,
    private val savedSelectionParticle: Particle = Particle.REDSTONE,
    private val redrawInterval: Long = 10L,
): AbstractSelection(_selection), Runnable {
    private val composite: CompositeTerminable = CompositeTerminable.create()
    private val savedSelections: ArrayList<Selection> = ArrayList<Selection>()

    init {
        registerEvent()
        registerScheduler()
    }

    data class Builder(
        private var selection: Selection,
        private var player: Player,
        private var itemStack: ItemStack
    ) {
        private var selectedSelectionParticle: Particle = Particle.REDSTONE
        private var savedSelectionParticle: Particle = Particle.REDSTONE
        private var redrawInterval: Long = 10L

        fun player(player: Player) = apply { this.player = player }
        fun itemStack(itemStack: ItemStack) = apply { this.itemStack = itemStack }
        fun selectedSelectionParticle(particle: Particle) = apply { this.selectedSelectionParticle = particle }
        fun savedSelectionParticle(particle: Particle) = apply { this.savedSelectionParticle = particle }
        fun redrawInterval(redrawInterval: Long) = apply { this.redrawInterval = redrawInterval }
        fun build(): DisplayedSelection = DisplayedSelection(selection, player, itemStack, selectedSelectionParticle, savedSelectionParticle, redrawInterval)
    }

    fun saveCurrentSelection() {
        savedSelections.add(selection.clone())
    }

    fun clearCurrentSelection() {
        selection.setLocation(Selection.Type.FIRST_POSITION, null)
        selection.setLocation(Selection.Type.SECOND_POSITION, null)
    }

    fun stop() {
        composite.close()
    }

    private fun registerScheduler() {
        val task: Task = Schedulers.sync().runRepeating(this, 0, redrawInterval)
        task.bindWith(composite)
    }

    override fun run() {
        val loc1: Location? = selection.getLocation(Selection.Type.FIRST_POSITION)?.clone()
        val loc2: Location? = selection.getLocation(Selection.Type.SECOND_POSITION)?.clone()

        loc1?.add(0.5,1.2,0.5);
        loc2?.add(0.5,1.2,0.5);

        if (loc2 != null) {
            if (loc1 == null) {
                loc2.world?.spawnParticle(selectedSelectionParticle, loc2, 1, Particle.DustOptions(Color.AQUA, 1F))
            }

            if (loc1 != null) {
                val vectors: List<Location> = plotLine(loc1, loc2)

                for (vector in vectors) {
                    loc2.world?.spawnParticle(selectedSelectionParticle, vector, 1, Particle.DustOptions(Color.AQUA, 1F))
                }
            }
        }

        if (loc1 != null) {
            if (loc2 == null) {
                loc1.world?.spawnParticle(selectedSelectionParticle, loc1, 1, Particle.DustOptions(Color.AQUA, 1F))
            }
        }
    }

    /** EVENTS **/
    private fun registerEvent() {
        Events.subscribe(PlayerInteractEvent::class.java)
            .filter { it.clickedBlock != null }
            .filter { it.action == Action.RIGHT_CLICK_BLOCK || it.action == Action.LEFT_CLICK_BLOCK }
            .filter { it.player.inventory.itemInMainHand.isSimilar(itemStack) }
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

                Message.getOrDefault("selected_location", Message("&aYou have selected the %type% position!")).send(e.player, Message.Placeholder("type", type))
            }.bindWith(composite)
    }
}