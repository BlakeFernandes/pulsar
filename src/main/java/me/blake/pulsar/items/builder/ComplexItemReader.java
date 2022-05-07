package me.blake.pulsar.items.builder;

import com.cryptomorin.xseries.XMaterial;
import me.blake.pulsar.items.ComplexItem;
import me.blake.pulsar.items.compact.CompactItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class ComplexItemReader {

    public static final ComplexItemReader DEFAULT = new ComplexItemReader();

    public final ComplexItemBuilder read(ConfigurationSection config) {
        return ComplexItemBuilder.of(Material.STONE)
                .apply(itemBuilder -> {
                    parseMaterial(config).ifPresent(itemBuilder::itemStack);
                    parseMeta(config).ifPresent(itemBuilder::meta);
                    parseName(config).ifPresent(itemBuilder::name);
                    parseLore(config).ifPresent(itemBuilder::lore);
                });
    }

    protected Optional<ComplexItem.Meta> parseMeta(ConfigurationSection config) {
        if (config.contains("settings")) {
            boolean glow = config.getBoolean("settings.glow", false);
            int damage = config.getInt("settings.damage", 0);

            return Optional.of(new ComplexItem.Meta(damage, glow));
        }
        return Optional.empty();
    }

    protected Optional<ItemStack> parseMaterial(ConfigurationSection config) {
        if (config.contains("material")) {
            String materialString = config.getString("material");
            ItemStack materialItemStack;
            if (materialString.contains(";")) {
                materialItemStack = CompactItem.from(materialString).getItem();
            } else {
                materialItemStack = new ItemStack(XMaterial.matchXMaterial(materialString).orElse(XMaterial.STONE).parseMaterial());
            }
            return Optional.of(materialItemStack);
        }
        return Optional.empty();
    }

    protected Optional<String> parseName(ConfigurationSection config) {
        if (config.contains("name")) {
            return Optional.of(config.getString("name"));
        }
        return Optional.empty();
    }

    protected Optional<List<String>> parseLore(ConfigurationSection config) {
        if (config.contains("lore")) {
            return Optional.of(config.getStringList("lore"));
        }
        return Optional.empty();
    }
}
