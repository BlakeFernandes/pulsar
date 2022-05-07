package me.blake.pulsar.items.builder;

import me.blake.pulsar.items.ComplexItem;
import me.lucko.helper.menu.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ComplexItemBuilder {

    private final ComplexItem item;

    public ComplexItemBuilder(Material material) {
        this.item = new ComplexItem();
        this.item.setItemStack(new ItemStack(material));
    }

    public ComplexItemBuilder(ItemStack itemStack) {
        this.item = new ComplexItem();
        this.item.setItemStack(itemStack);
    }

    public ComplexItemBuilder(ComplexItem complexItem) {
        this.item = complexItem.clone();
    }

    public static ComplexItemBuilder of(Material material) {
        return new ComplexItemBuilder(material);
    }

    public static ComplexItemBuilder of(ItemStack itemStack) {
        return new ComplexItemBuilder(itemStack);
    }

    public static ComplexItemBuilder of(ComplexItem complexItem) {
        return new ComplexItemBuilder(complexItem);
    }

    public ItemStack buildItemStack() {
        return this.item.getItemStackBuilder().build();
    }

    public Item buildItem() {
        return this.item.getItemStackBuilder().buildItem().build();
    }

    public ComplexItem build() {
        return this.item;
    }

    public ComplexItemBuilder applyMeta(Consumer<ComplexItem.Meta> consumer) {
        consumer.accept(this.item.getMeta());
        return this;
    }

    public ComplexItemBuilder meta(ComplexItem.Meta meta) {
        this.item.setMeta(meta);
        return this;
    }

    public ComplexItemBuilder itemStack(ItemStack itemStack) {
        this.item.setItemStack(itemStack);
        return this;
    }

    public ComplexItemBuilder material(Material material) {
        this.item.setItemStack(new ItemStack(material));
        return this;
    }

    public ComplexItemBuilder applyName(UnaryOperator<String> name) {
        name(name.apply(this.item.getName()));
        return this;
    }

    public ComplexItemBuilder name(String name) {
        this.item.setName(name);
        return this;
    }

    public ComplexItemBuilder applyLore(UnaryOperator<List<String>> lore) {
        lore(lore.apply(this.item.getLore()));
        return this;
    }

    public ComplexItemBuilder lore(List<String> strings) {
        this.item.setLore(strings);
        return this;
    }

    public ComplexItemBuilder apply(Consumer<ComplexItemBuilder> consumer) {
        consumer.accept(this);
        return this;
    }
}
