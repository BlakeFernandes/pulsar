package me.blake.pulsar.utils

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.*


class FileManager(
    val plugin: JavaPlugin
) {
    private val configs = HashMap<String, Config>()

    fun getConfig(name: String): Config {
        return configs.getOrDefault(name, Config(name));
    }

    fun saveConfig(name: String): Config {
        return getConfig(name).save()
    }

    fun reloadConfig(name: String): Config {
        return getConfig(name).reload()
    }

    inner class Config(private val _name: String): YamlConfiguration() {
        private var file: File = File(plugin.dataFolder, _name)

        init {
            loadConfiguration(file)
        }

        fun save(): Config {
            try {
                copyDefaults(true)
                save(file)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return this
        }

        fun saveDefaultConfig(): Config {
            plugin.saveResource(_name, false)
            return this
        }

        fun reload(): Config {
            val configStream = InputStreamReader(plugin.getResource(this._name), "UTF8")
            val defaultConfig = loadConfiguration(configStream)
            setDefaults(defaultConfig)
            return this
        }

        fun copyDefaults(force: Boolean): Config {
            options().copyDefaults(force)
            return this
        }
    }
}