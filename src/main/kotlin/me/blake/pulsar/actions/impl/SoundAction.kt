package me.blake.pulsar.actions.impl

import com.cryptomorin.xseries.XSound
import me.blake.pulsar.actions.Action
import me.blake.pulsar.messaging.Message
import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundAction(
    private val sound: Sound,
    private val volume: Float,
    private val pitch: Float
): Action() {

    override fun execute(player: Player, vararg placeholders: Message.Placeholder) {
        execute(player)
    }

    override fun execute(player: Player) {
        player.playSound(player.location, sound, volume, pitch)
    }

    companion object Parser {
        fun parse(args: String): Action? {
            val split = args.split(";")
            if (split.size < 3) return null
            val sound = XSound.parse(split[1])?.sound?.parseSound() ?: XSound.BLOCK_STONE_BUTTON_CLICK_ON.parseSound() ?: return null
            val volume = split[2].toFloatOrNull() ?: return null
            val pitch = if (split.size > 3) split[3].toFloatOrNull() ?: return null else 1f
            return SoundAction(sound, volume, pitch)
        }
    }
}