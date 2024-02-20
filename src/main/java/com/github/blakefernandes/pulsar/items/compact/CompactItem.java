package com.github.blakefernandes.pulsar.items.compact;

import com.github.blakefernandes.pulsar.items.compact.types.CompactItemStack;
import com.github.blakefernandes.pulsar.items.compact.types.MaterialItem;
import com.github.blakefernandes.pulsar.items.compact.types.SkullItem;
import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public abstract class CompactItem {
    public static CompactItem from(String string) {
        String[] itemType = string.split(";");

        if (itemType.length != 2) {
            if (itemType.length == 1 && XMaterial.matchXMaterial(itemType[0]).isPresent()) {
                return new MaterialItem(itemType[0]);
            } else {
                return new CompactItemStack(XMaterial.STONE.parseItem());
            }
        }

        switch (itemType[0]) {
           case "SKULL" -> {
                return new SkullItem(itemType[1]);
           }
           case "MATERIAL" -> {
               return new MaterialItem(itemType[1]);
           }
           default -> {
               return new CompactItemStack(XMaterial.STONE.parseItem());
           }
        }
    }

    public abstract ItemStack getItem();
}
