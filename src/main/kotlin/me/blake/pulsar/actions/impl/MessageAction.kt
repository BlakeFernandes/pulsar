package me.blake.pulsar.actions.impl

import me.blake.pulsar.actions.Action
import me.blake.pulsar.messaging.Message
import me.blake.pulsar.messaging.Message.Placeholder
import org.bukkit.entity.Player

class MessageAction(
    private val message: String,
): Action() {
    override fun execute(player: Player, vararg placeholders: Placeholder) {
        Message(message).send(player, *placeholders)
    }

    override fun execute(player: Player) {
        Message(message).send(player)
    }
}