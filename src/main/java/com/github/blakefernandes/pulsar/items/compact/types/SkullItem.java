package com.github.blakefernandes.pulsar.items.compact.types;

import com.github.blakefernandes.pulsar.utils.CustomHeads;
import lombok.AllArgsConstructor;
import com.github.blakefernandes.pulsar.items.compact.CompactItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SkullItem extends CompactItem {
    private String base64Texture;

    @Override
    public ItemStack getItem() {
        return CustomHeads.getCustomTextureHead(base64Texture);
    }
}
