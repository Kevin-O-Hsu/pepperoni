package mc.veryminecraft.top.pepperoni.client.hacks;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerESPHack extends Hack{


    private List<AbstractClientPlayerEntity> cachedPlayers = new ArrayList<>();
    private final Map<AbstractClientPlayerEntity, Float> playerAlphaMap = new HashMap<>();


    public PlayerESPHack(WorldRenderContext ctx) {
        super(ctx);
    }

    @Override
    public void update() {
        if (mc.world == null || mc.player == null) return;
        cachedPlayers = theMobLocator.getPlayers(mc.world, mc.player);
        for (AbstractClientPlayerEntity player : cachedPlayers) {
            double distance = mc.player.getPos().distanceTo(player.getPos());
            float alpha = calculateAlpha(distance, 96, 0F, 0.75F);
            playerAlphaMap.put(player, alpha);
        }
        if (onHack) {
            hackMethod();
        }

    }

    @Override
    public void hackMethod() {
        if (mc.world == null || mc.player == null) return;

        Vec3d cameraPos = context.camera().getPos();
        for (AbstractClientPlayerEntity player : cachedPlayers) {
            float alpha = playerAlphaMap.getOrDefault(player, 1.0F);
            Vec3d interpolatedPos = interpolateEntityPosition(player, context.camera().getLastTickDelta(), false);
            renderUtils.drawBox(interpolatedPos, player.getWidth(), player.getHeight(),
                    cameraPos, new Vector3f(1F, 0F, 0F), alpha, true);
        }
    }

}
