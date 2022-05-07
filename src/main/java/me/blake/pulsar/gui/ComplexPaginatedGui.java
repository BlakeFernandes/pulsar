package me.blake.pulsar.gui;

import lombok.Getter;
import me.blake.pulsar.gui.format.ComplexPaginatedGuiFormat;
import me.blake.pulsar.items.ComplexGuiItem;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Getter
public abstract class ComplexPaginatedGui extends PaginatedGui {
    private ComplexPaginatedGuiFormat guiFormat;

    public ComplexPaginatedGui(Player player, ComplexPaginatedGuiFormat guiFormat) {
        super(paginatedGui -> new ArrayList<>(), player, getBuilder(guiFormat));
        this.guiFormat = guiFormat;
    }

    private static PaginatedGuiBuilder getBuilder(ComplexPaginatedGuiFormat guiFormat) {
        PaginatedGuiBuilder builder = PaginatedGuiBuilder.create()
                .title(guiFormat.getTitle())
                .lines(guiFormat.getSchemaFormat().getLines())
                .scheme(new MenuScheme())
                .itemSlots(new MenuScheme().masks(guiFormat.getItemSlots()).getMaskedIndexesImmutable());

        if (guiFormat.getNextPage() != null) {
            builder.nextPageItem(pageInfo -> guiFormat.getNextPage().getGuiItem().getItemStack());
            builder.nextPageSlot(guiFormat.getNextItemSlot());
        }

        if (guiFormat.getPreviousPage() != null) {
            builder.previousPageItem(pageInfo -> guiFormat.getPreviousPage().getGuiItem().getItemStack());
            builder.previousPageSlot(guiFormat.getPreviousItemSlot());
        }

        return builder;
    }

    @Override
    public void redraw() {
        super.redraw();
        for (ComplexGuiItem guiItem : this.guiFormat.getItemMap()) {
//            if (!guiItem.getType().equalsIgnoreCase("ITEM")) continue;
            List<Integer> positions = guiFormat.getSchemaFormat().getPositions(guiItem.getSymbol());
            for (Integer pos : positions) {
                setItem(pos, guiItem.getGuiItem());
            }
        }
    }
}