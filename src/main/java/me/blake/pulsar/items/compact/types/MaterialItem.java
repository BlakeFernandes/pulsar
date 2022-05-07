package me.blake.pulsar.items.compact.types;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import me.blake.pulsar.items.compact.CompactItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class MaterialItem extends CompactItem {
    private String materialType;

    @Override
    public ItemStack getItem() {
        return XMaterial.matchXMaterial(materialType).orElse(XMaterial.STONE).parseItem();
    }
}
