package me.blake.pulsar.items;

import lombok.Getter;
import me.blake.pulsar.items.compact.CompactItem;
import me.lucko.helper.menu.Item;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

@Getter
public class ComplexGuiItem extends ComplexItem {

    private final Character symbol;
    private final String type;

    public ComplexGuiItem(String name, ItemStack itemStack, List<String> lore,
                          int damage, boolean glow, Character symbol, String type) {
        super();
        setName(name);
        setItemStack(itemStack);
        setLore(lore);
        setMeta(new Meta(damage, glow));
        this.symbol = symbol;
        this.type = type;
    }

    public Item getGuiItem() {
        return getItemStackBuilder().buildItem().build();
    }

    public static ComplexGuiItem from(ConfigurationSection section) {
        String type = section.getString("type");
        String name = section.getString("name");
        String material = section.getString("material");
        String symbol = section.getString("symbol");
        List<String> lore = section.getStringList("lore");

        ConfigurationSection actions = section.getConfigurationSection("actions");

        boolean glow = section.getBoolean("settings.glow");
        int damage = section.getInt("settings.damage");

        Objects.requireNonNull(symbol);
        Objects.requireNonNull(material);

        ComplexGuiItem guiItem = new ComplexGuiItem(
                name,
                CompactItem.from(material).getItem(),
                lore,
                damage,
                glow,
                symbol.charAt(0),
                type
        );

        return guiItem;
    }
}
