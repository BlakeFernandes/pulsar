package me.blake.pulsar.items.action.types;

import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SoundAction extends Action {
    private Sound sound;
    private int volume;
    private int pitch;

    @Override
    public void execute(Player player) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
