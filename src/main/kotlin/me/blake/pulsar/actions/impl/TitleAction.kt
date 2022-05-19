package me.blake.pulsar.actions.impl

import me.blake.pulsar.actions.Action
import me.blake.pulsar.messaging.Message
import me.blake.pulsar.messaging.Message.Placeholder
import me.lucko.helper.text3.Text
import org.bukkit.entity.Player

class TitleAction(
    private val title: String = "",
    private val subtitle: String = "",
    private val fadeIn: Int = 0,
    private val stay: Int = 10,
    private val fadeOut: Int = 0
): Action() {
    override fun execute(player: Player, vararg placeholders: Placeholder) {
        player.sendTitle(
            Text.colorize(Message(title).transform(*placeholders).firstString()),
            Text.colorize(Message(subtitle).transform(*placeholders).firstString()),
            fadeIn,
            stay,
            fadeOut
        )
    }

    override fun execute(player: Player) {
        player.sendTitle(
            Text.colorize(title),
            subtitle,
            fadeIn,
            stay,
            fadeOut
        )
    }

    companion object Parser {
        fun parse(action: String): TitleAction {
            val split = action.split(";")
            return TitleAction(
                split[0],
                split.getOrElse(1) { "" },
                split.getOrElse(2) { 0 } as Int,
                split.getOrElse(3) { 10 } as Int,
                split.getOrElse(4) { 0 } as Int
            )
        }
    }
}