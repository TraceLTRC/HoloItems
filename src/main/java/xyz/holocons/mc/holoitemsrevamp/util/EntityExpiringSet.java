package xyz.holocons.mc.holoitemsrevamp.util;

import java.util.UUID;

import org.bukkit.entity.Entity;

public class EntityExpiringSet extends ExpiringSet<UUID, Entity> {
    public EntityExpiringSet(ExpirationPolicy<Entity> expirationPolicy) {
        super(Entity::getUniqueId, expirationPolicy);
    }
}
