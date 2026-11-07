package com.nivek.animated.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Suaviza (interpolação exponencial independente de FPS) a pose de braços, pernas e corpo
 * para remover o movimento "robótico" e criar transições suaves entre animações.
 */
public final class PoseSmoother {
    private static final float SPEED_NORMAL = 16f;   // 1/s  (menor = mais suave)
    private static final float SPEED_ACTION = 32f;   // ataque/uso de item: mais responsivo
    private static final Map<UUID, State> STATES = new HashMap<>();

    private static final class State {
        final float[] v = new float[15];
        long lastNanos;
    }

    private PoseSmoother() {}

    public static void apply(LivingEntity entity, PlayerEntityModel<?> m) {
        if (!(entity instanceof PlayerEntity)) return;
        ModelPart[] parts = {m.rightArm, m.leftArm, m.rightLeg, m.leftLeg, m.body};

        if (STATES.size() > 128) STATES.clear();
        long now = System.nanoTime();
        State s = STATES.get(entity.getUuid());
        float dt = s == null ? 1f : (now - s.lastNanos) / 1.0e9f;
        boolean snap = s == null || dt > 0.25f;
        if (s == null) { s = new State(); STATES.put(entity.getUuid(), s); }
        s.lastNanos = now;

        float speed = (entity.handSwinging || entity.isUsingItem()) ? SPEED_ACTION : SPEED_NORMAL;
        float alpha = snap ? 1f : 1f - (float) Math.exp(-speed * dt);

        for (int i = 0; i < parts.length; i++) {
            ModelPart p = parts[i];
            int o = i * 3;
            if (snap) { s.v[o] = p.pitch; s.v[o + 1] = p.yaw; s.v[o + 2] = p.roll; }
            else {
                s.v[o] += (p.pitch - s.v[o]) * alpha;
                s.v[o + 1] += (p.yaw - s.v[o + 1]) * alpha;
                s.v[o + 2] += (p.roll - s.v[o + 2]) * alpha;
            }
            p.pitch = s.v[o]; p.yaw = s.v[o + 1]; p.roll = s.v[o + 2];
        }

        // Camadas externas (mangas/calças/jaqueta) acompanham as partes suavizadas
        m.leftSleeve.copyTransform(m.leftArm);
        m.rightSleeve.copyTransform(m.rightArm);
        m.leftPants.copyTransform(m.leftLeg);
        m.rightPants.copyTransform(m.rightLeg);
        m.jacket.copyTransform(m.body);
    }
}
