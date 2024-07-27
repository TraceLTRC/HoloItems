package xyz.holocons.mc.holoitemsrevamp.enchantment;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class SSKSword extends CustomEnchantment implements EnchantmentAbility {

    private static final int ticksPerLevel = 80;

    public SSKSword(HoloItemsRevamp plugin) {
        super(plugin, "ssk_sword");
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return itemStack.getType() == Material.DIAMOND_SWORD;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text()
                .color(NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("SSK Sword"))
                .build();
    }

    @Override
    public int getCostMultiplier() {
        return 12;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if (isMissingHealth(event.getDamager())) {
            return;
        }

        final var rawDamage = event.getDamage();
        if (rawDamage <= 0d || !(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        event.setDamage(0);

        final var targetIsUndead = target.getCategory() == EntityCategory.UNDEAD;
        final var effectType = targetIsUndead ? PotionEffectType.HARM : PotionEffectType.HEAL;
        target.addPotionEffect(new PotionEffect(effectType, 1, 1));

        final var fireAspectLevel = itemStack.getEnchantmentLevel(FIRE_ASPECT);
        if (fireAspectLevel > 0) {
            target.addPotionEffects(List.of(
                    new PotionEffect(PotionEffectType.FIRE_RESISTANCE, ticksPerLevel * fireAspectLevel, 1),
                    new PotionEffect(PotionEffectType.SPEED, ticksPerLevel * fireAspectLevel, 1)));
        }

        final var location = target.getLocation().add(0, target.getHeight() / 2, 0);
        final var world = location.getWorld();
        world.spawnParticle(Particle.HEART, location, 3, 0.5, 0.5, 0.5);
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, 1f);
    }

    private static boolean isMissingHealth(final Entity entity) {
        return entity instanceof LivingEntity livingEntity
                && livingEntity.getHealth() < livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }
}
