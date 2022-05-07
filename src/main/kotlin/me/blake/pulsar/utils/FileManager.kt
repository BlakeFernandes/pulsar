package me.blake.pulsar.utils

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.*


class FileManager(
    val plugin: JavaPlugin
) {
    private val configs = HashMap<String, Config>()

    fun getConfig(name: String): Config? {
        if (!configs.containsKey(name)) configs[name] = Config(name)
        return configs[name]
    }

    fun saveConfig(name: String): Config? {
        return getConfig(name)?.save()
    }

    fun reloadConfig(name: String): Config? {
        return getConfig(name)?.reload()
    }

    inner class Config(private val name: String) {
        private var file: File = File(plugin.dataFolder, name)
        private var config: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        fun save(): Config {
            try {
                if (file.exists()) copyDefaults(true)
                if (config.getConfigurationSection("")?.getKeys(true)?.size != 0) config.save(file)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return this
        }

        fun saveDefaultConfig(): Config {
            plugin.saveResource(name, false)
            return this
        }

        fun reload(): Config {
            this.config = YamlConfiguration.loadConfiguration(file)
            return this
        }

        fun copyDefaults(force: Boolean): Config {
            config.options().copyDefaults(force)
            return this
        }

        operator fun set(key: String, value: Any?): Config {
            config.set(key, value)
            return this
        }
    }
}