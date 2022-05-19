package me.blake.pulsar.actions.impl

import me.blake.pulsar.actions.Action
import me.blake.pulsar.messaging.Message
import me.blake.pulsar.messaging.Message.Placeholder
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CommandAction(
    private val command: String
) : Action() {

    override fun execute(player: Player, vararg placeholders: Placeholder) {
        Bukkit.getServer().dispatchCommand(
            Bukkit.getConsoleSender(),
            Message(command).transform(*placeholders).asList()[0]
        )
    }

    override fun execute(player: Player) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Message(command).firstString())
    }
}
