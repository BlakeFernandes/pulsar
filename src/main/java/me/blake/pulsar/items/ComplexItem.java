package me.blake.pulsar.items;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import me.blake.pulsar.items.builder.ComplexItemBuilder;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

@Setter @Getter
public class ComplexItem implements Cloneable {
    private String name;
    private List<String> lore;
    private ItemStack itemStack;
    private Meta meta;

    public ComplexItem() {
        this.name = "&cUnknown Name";
        this.lore = List.of("&cUnknown Lore");
        this.itemStack = new ItemStack(XMaterial.STONE.parseMaterial());
        this.meta = new Meta(0, false);
    }

    public ItemStackBuilder getItemStackBuilder() {
        return ItemStackBuilder.of(new ItemStack(itemStack))
                .apply(b -> meta.apply(b))
                .name(name)
                .lore(lore);
    }

    public ComplexItemBuilder getComplexItemBuilder() {
        return ComplexItemBuilder.of(this);
    }

    public ItemStack buildItemStack() {
        return new ItemStack(this.getItemStackBuilder().build());
    }

    public Item buildItem() {
        return this.getItemStackBuilder().buildItem().build();
    }

    public Material getMaterial() {
        return itemStack.getType();
    }

    @NotNull
    @Override
    public ComplexItem clone() {
        try {
            ComplexItem complexItem = (ComplexItem) super.clone();

            if (this.meta != null) {
                complexItem.meta = this.meta.clone();
            }

            return complexItem;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public static class Meta implements Cloneable{
        private int data;
        private boolean glow;
        private NBTCompound nbtCompound;

        public Meta(int data, boolean glow) {
            this.data = data;
            this.glow = glow;
        }

        public ItemStackBuilder apply(ItemStackBuilder builder) {
            if (glow) {
                builder.enchant(Enchantment.KNOCKBACK);
                builder.hideAttributes();
            }

            builder.data(data);

            if (nbtCompound != null) {
                NBTItem nbtItem = new NBTItem(builder.build());
                nbtItem.mergeCompound(nbtCompound);
                builder.transform(item -> item.setItemMeta(nbtItem.getItem().getItemMeta()));
            }

            return builder;
        }

        public void applyNBT(Consumer<NBTCompound> nbtCompound) {
            nbtCompound.accept(getNBTCompound());
        }

        public NBTCompound getNBTCompound() {
            if (this.nbtCompound == null) this.nbtCompound = new NBTContainer();
            return this.nbtCompound;
        }

        public void setNbtCompound(NBTCompound nbtCompound) {
            this.nbtCompound = nbtCompound;
        }

        @NotNull
        @Override
        public Meta clone() {
            try {
                return (Meta) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }

    }

//    private String name;
//    private ItemStack material;
//    private List<String> lore;
//    private int damage;
//    private boolean glow;
//    private NBTCompound NBTCompound;
//
//    public ComplexItem() {
//        this.name = "&cInvalid ConfigurationSection";
//        this.material = XMaterial.STONE.parseItem();
//        this.lore = List.of("&7Could not find the config for this item");
//        this.damage = 0;
//        this.glow = false;
//    }
//
//    public ItemStack getItemStack() {
//        return getItem().getItemStack();
//    }
//
//    public Item getItem() {
//        ItemStackBuilder builder = ItemStackBuilder.of(new ItemStack(material))
//                .name(name)
//                .lore(lore);
//
//        if (damage != 0) {
//            builder.data(damage);
//        }
//
//        if (glow) {
//            builder.enchant(Enchantment.KNOCKBACK);
//            builder.hideAttributes();
//        }
//
//        NBTItem nbtItem = new NBTItem(builder.build());
//        if (NBTCompound != null) {
//            nbtItem.mergeCompound(NBTCompound);
//        }
//
//        return ItemStackBuilder.of(nbtItem.getItem()).buildItem().build();
//    }
//
//    public void setNBTCompound(NBTCompound NBTCompound) {
//        this.NBTCompound = NBTCompound;
//    }
//
//    public static ComplexItem from(@Nullable ConfigurationSection section) {
//        if (section == null) return new ComplexItem();
//
//        String name = section.getString("name");
//        String material = section.getString("material");
//        List<String> lore = section.getStringList("lore");
//        boolean glow = section.getBoolean("settings.glow");
//        int damage = section.getInt("settings.damage");
//
//        Objects.requireNonNull(material);
//
//        return new ComplexItem(
//                name,
//                CompactItem.from(material).getItem(),
//                lore,
//                damage,
//                glow,
//                null
//        );
//    }
}
