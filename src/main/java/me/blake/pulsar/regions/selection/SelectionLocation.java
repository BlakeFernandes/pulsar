package me.blake.pulsar.regions.selection;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public class SelectionLocation {
    private Location location1;
    private Location location2;

    public void setLocation(SelectionData.Type locationType, Location location) {
        if (locationType == SelectionData.Type.FIRST_POSITION) {
            this.location1 = location;
        } else {
            this.location2 = location;
        }
    }

    public Location getLocation(SelectionData.Type locationType) {
        if (locationType == SelectionData.Type.FIRST_POSITION) {
            return location1;
        } else {
            return location2;
        }
    }

    public boolean hasLocation() {
        return location1 != null && location2 != null;
    }
}
