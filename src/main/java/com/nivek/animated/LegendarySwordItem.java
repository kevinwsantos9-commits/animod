package com.nivek.animated;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import org.joml.Vector3f;

public class LegendarySwordItem extends SwordItem {
    public static final DustParticleEffect GOLD_DUST = new DustParticleEffect(new Vector3f(1.0f, 0.85f, 0.1f), 1.2f);

    public LegendarySwordItem(Settings settings) {
        super(ToolMaterials.NETHERITE, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getWorld() instanceof ServerWorld sw) {
            sw.spawnParticles(GOLD_DUST, target.getX(), target.getBodyY(0.5), target.getZ(), 18, 0.3, 0.4, 0.3, 0.05);
            sw.spawnParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getBodyY(0.5), target.getZ(), 6, 0.3, 0.4, 0.3, 0.1);
        }
        return super.postHit(stack, target, attacker);
    }
}
