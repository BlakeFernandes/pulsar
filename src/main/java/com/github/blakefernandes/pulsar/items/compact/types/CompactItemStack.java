package com.github.blakefernandes.pulsar.items.compact.types;

import lombok.AllArgsConstructor;
import com.github.blakefernandes.pulsar.items.compact.CompactItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class CompactItemStack extends CompactItem {

    private ItemStack itemStack;

    @Override
    public ItemStack getItem() {
        return itemStack;
    }
}
