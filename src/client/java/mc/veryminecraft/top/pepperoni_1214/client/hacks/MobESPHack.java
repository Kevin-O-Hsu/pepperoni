package mc.veryminecraft.top.pepperoni_1214.client.hacks;

import mc.veryminecraft.top.pepperoni_1214.client.utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobESPHack extends Hack{


    private List<MobEntity> cachedHostiles = new ArrayList<>();
    private final Map<MobEntity, Float> hostileAlphaMap = new HashMap<>();


    public MobESPHack(WorldRenderContext ctx) {
        super(ctx);
    }

    @Override
    public void update() {
        if (mc.world == null || mc.player == null) return;
        cachedHostiles = theMobLocator.getNearbyMonsters(mc.world, mc.player, 96F);
        for (MobEntity hostile : cachedHostiles) {
            double distance = mc.player.getPos().distanceTo(hostile.getPos());
            float alpha = calculateAlpha(distance, 96, 0F, 0.75F);
            hostileAlphaMap.put(hostile, alpha);
        }
        if (onHack) {
            hackMethod();
        }

    }

    @Override
    public void hackMethod() {
        if (mc.world == null || mc.player == null) return;

        Vec3d cameraPos = context.camera().getPos();
        for (MobEntity hostile : cachedHostiles) {
            float alpha = hostileAlphaMap.getOrDefault(hostile, 1.0F);
            Vec3d interpolatedPos = RenderUtils.interpolateEntityPosition(hostile, context.camera().getLastTickDelta(), false);
            renderUtils.drawBox(interpolatedPos, hostile.getWidth(), hostile.getHeight(),
                    cameraPos, new Vector3f(1F, 0F, 0F), alpha, true);
        }
    }

}
