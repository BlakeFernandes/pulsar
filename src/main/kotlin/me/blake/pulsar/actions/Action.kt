package me.blake.pulsar.actions

import me.blake.pulsar.actions.impl.CommandAction
import me.blake.pulsar.actions.impl.MessageAction
import me.blake.pulsar.actions.impl.TitleAction
import me.blake.pulsar.messaging.Message.Placeholder
import org.apache.commons.lang.StringUtils
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

abstract class Action(
) {
    private var controller: ActionController? = null
    abstract fun execute(player: Player, vararg placeholders: Placeholder)
    abstract fun execute(player: Player)

    companion object Parser {
        fun from(action: String): Action? {
            return parse(action)
        }

        fun from(action: List<String>): List<Action> {
            val actionList: ArrayList<Action> = ArrayList()

            for (a in action) {
                val parsed = parse(a)
                if (parsed != null) {
                    actionList.add(parsed)
                }
            }

            return actionList
        }

        fun from(configurationSection: ConfigurationSection): List<Action> {
            val actionList: ArrayList<Action> = ArrayList()

            for (key in configurationSection.getKeys(false)) {
                if (!configurationSection.contains(key)) continue
                val parsed = parse(configurationSection.getString(key) ?: "")
                if (parsed != null) {
                    actionList.add(parsed)
                }
            }

            return actionList
        }

        private fun parse(action: String): Action? {
            val rawActionType: String = StringUtils.substringBetween(action, "[", "]")
            val actionController: String = if (rawActionType.contains(";")) rawActionType.split(";")[1] else ""
            val actionType: String = if (rawActionType.contains(";")) rawActionType.split(";")[0] else rawActionType
            val actionData = StringUtils.substringAfter(action, "] ")

            return when (actionType.uppercase()) {
                "" -> null
                "MESSAGE" -> MessageAction(actionData)
                "COMMAND" -> CommandAction(actionData)
                "TITLE" -> TitleAction.parse(actionData)
                else -> null
            }
        }
    }
}