package com.nivek.animated.client;

import com.nivek.animated.LegendarySwordItem;
import com.nivek.animated.NivekAnimated;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class NivekAnimatedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Partículas amarelas leves enquanto a espada está na mão (a cada 3 ticks, baixo custo)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null || client.player.age % 3 != 0) return;
            if (!client.player.getMainHandStack().isOf(NivekAnimated.LEGENDARY_SWORD)) return;
            Vec3d p = client.player.getEyePos().add(client.player.getRotationVec(1.0f).multiply(0.9));
            Random r = client.world.random;
            client.world.addParticle(LegendarySwordItem.GOLD_DUST,
                    p.x + (r.nextDouble() - 0.5) * 0.5, p.y - 0.4 + (r.nextDouble() - 0.5) * 0.6,
                    p.z + (r.nextDouble() - 0.5) * 0.5, 0.0, 0.02, 0.0);
        });
    }
}
