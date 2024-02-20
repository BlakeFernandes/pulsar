package com.github.blakefernandes.pulsar.actions.impl

import com.github.blakefernandes.pulsar.actions.Action
import com.github.blakefernandes.pulsar.messaging.Message
import com.github.blakefernandes.pulsar.messaging.Message.Placeholder
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