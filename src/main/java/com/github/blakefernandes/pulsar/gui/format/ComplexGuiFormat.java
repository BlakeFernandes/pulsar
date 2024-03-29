package com.github.blakefernandes.pulsar.gui.format;

import com.github.blakefernandes.pulsar.items.ComplexGuiItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.github.blakefernandes.pulsar.gui.schema.SchemaFormat;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @AllArgsConstructor
public class ComplexGuiFormat {
    private String title;
    private SchemaFormat schemaFormat;
    private List<ComplexGuiItem> itemMap;

    public ComplexGuiFormat() {
        this("&cConfig Section not found!", new SchemaFormat("########"), new ArrayList<>());
    }

    public static ComplexGuiFormat from(@Nullable ConfigurationSection section) {
        if (section == null) return new ComplexGuiFormat();

        ComplexGuiFormat guiFormat = new ComplexGuiFormat(
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

    public void setItem(ComplexGuiItem item) {

    }
}
