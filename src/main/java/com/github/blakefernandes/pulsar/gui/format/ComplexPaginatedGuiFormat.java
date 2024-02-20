package com.github.blakefernandes.pulsar.gui.format;

import com.github.blakefernandes.pulsar.gui.schema.SchemaFormat;
import com.github.blakefernandes.pulsar.items.ComplexGuiItem;
import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ComplexPaginatedGuiFormat extends ComplexGuiFormat {

    private @Nullable ComplexGuiItem previousPage;
    private @Nullable ComplexGuiItem defaultPreviousPage;
    private @Nullable ComplexGuiItem nextPage;
    private @Nullable ComplexGuiItem defaultNextPage;

    public ComplexPaginatedGuiFormat() {
        super("&cConfig Section not found!", new SchemaFormat("########"), new ArrayList<>());
    }

    public ComplexPaginatedGuiFormat(String title, SchemaFormat schemaFormat, List<ComplexGuiItem> itemList) {
        super(title, schemaFormat, itemList);

        this.nextPage = getComplexItem(itemList, "NEXT_PAGE", new ComplexGuiItem("", XMaterial.AIR.parseItem(), new ArrayList<>(), 0, false, ' ', ""));
        this.previousPage = getComplexItem(itemList, "PREVIOUS_PAGE", new ComplexGuiItem("", XMaterial.AIR.parseItem(), new ArrayList<>(), 0, false, ' ', ""));
    }

    public ComplexGuiItem getComplexItem(List<ComplexGuiItem> itemList, String type, ComplexGuiItem fallbackItem) {
        List<ComplexGuiItem> items = itemList.stream()
                .filter(item -> item.getType().equalsIgnoreCase(type))
                .toList();

        if (items.size() < 1) return fallbackItem;
        return items.get(0);
    }

    public String[] getItemSlots() {
        return getSchemaFormat().getItemSlots();
    }

    public int getPreviousItemSlot() {
        if (this.previousPage == null) return 0;
        List<Integer> itemSlots = this.getSchemaFormat().getPositions(this.previousPage.getSymbol());
        return itemSlots.isEmpty() ? 0 : itemSlots.get(0);
    }

    public int getNextItemSlot() {
        if (this.nextPage == null) return 0;
        List<Integer> itemSlots = this.getSchemaFormat().getPositions(this.nextPage.getSymbol());
        return itemSlots.isEmpty() ? 0 : itemSlots.get(0);
    }

    public static ComplexPaginatedGuiFormat from(@Nullable ConfigurationSection section) {
        if (section == null) return new ComplexPaginatedGuiFormat();

        ComplexPaginatedGuiFormat guiFormat = new ComplexPaginatedGuiFormat(
                section.getString("title"),
                new SchemaFormat(section.getStringList("schema")),
                section.getConfigurationSection("items")
                        .getKeys(false)
                        .stream()
                        .map(key -> ComplexGuiItem.from(section.getConfigurationSection("items." + key)))
                        .collect(Collectors.toList())
        );

        return guiFormat;
    }
}
