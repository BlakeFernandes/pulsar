package me.blake.pulsar.items.compact.types;

import lombok.AllArgsConstructor;
import me.blake.pulsar.items.compact.CompactItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class CompactItemStack extends CompactItem {

    private ItemStack itemStack;

    @Override
    public ItemStack getItem() {
        return itemStack;
    }
}
