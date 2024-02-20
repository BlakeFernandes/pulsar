package com.github.blakefernandes.pulsar.items.compact.types;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import com.github.blakefernandes.pulsar.items.compact.CompactItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class MaterialItem extends CompactItem {
    private String materialType;

    @Override
    public ItemStack getItem() {
        return XMaterial.matchXMaterial(materialType).orElse(XMaterial.STONE).parseItem();
    }
}
