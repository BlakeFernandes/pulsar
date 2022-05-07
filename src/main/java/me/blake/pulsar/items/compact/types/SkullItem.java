package me.blake.pulsar.items.compact.types;

import lombok.AllArgsConstructor;
import me.blake.pulsar.items.compact.CompactItem;
import me.blake.pulsar.utils.CustomHeads;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SkullItem extends CompactItem {
    private String base64Texture;

    @Override
    public ItemStack getItem() {
        return CustomHeads.getCustomTextureHead(base64Texture);
    }
}
