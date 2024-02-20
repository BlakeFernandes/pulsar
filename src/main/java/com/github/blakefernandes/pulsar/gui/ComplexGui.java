package com.github.blakefernandes.pulsar.gui;

import com.github.blakefernandes.pulsar.gui.format.ComplexGuiFormat;
import com.github.blakefernandes.pulsar.items.ComplexGuiItem;
import me.lucko.helper.menu.Gui;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ComplexGui extends Gui {

    private ComplexGuiFormat guiFormat;

    public ComplexGui(Player player, ComplexGuiFormat guiFormat) {
        super(player, guiFormat.getSchemaFormat().getLines(), guiFormat.getTitle());
        this.guiFormat = guiFormat;
    }

    @Override
    public void redraw() {
        for (ComplexGuiItem guiItem : this.guiFormat.getItemMap()) {
//            if (!guiItem.getType().equalsIgnoreCase("ITEM")) continue;
            List<Integer> positions = guiFormat.getSchemaFormat().getPositions(guiItem.getSymbol());
            positions.forEach(pos -> setItem(pos, guiItem.getGuiItem()));
        }
    }

    public ComplexGuiFormat getGuiFormat() {
        return guiFormat;
    }
}
