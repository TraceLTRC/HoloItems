package xyz.holocons.mc.holoitemsrevamp.ability;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import xyz.holocons.mc.holoitemsrevamp.enchant.Ability;

public abstract interface PlayerInteract extends Ability {
    @Override
    default public <E extends Event> void run(E event) {
        run((PlayerInteractEvent) event);
    }

    public abstract void run(PlayerInteractEvent event);
}