package mc.veryminecraft.top.pepperoni.client.hacks;

import mc.veryminecraft.top.pepperoni.client.utils.MobLocator;
import mc.veryminecraft.top.pepperoni.client.utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class ESP {

    private final MobLocator theMobLocator = new MobLocator();
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final WorldRenderContext context;
    private final RenderUtils renderUtils;
    private final HashMap<String, Boolean> hackStatus;

    private List<HostileEntity> cachedHostiles = new ArrayList<>();
    private List<AbstractClientPlayerEntity> cachedPlayers = new ArrayList<>();
    private long lastUpdateTime = 0;

    private final Map<HostileEntity, Float> hostileAlphaMap = new HashMap<>();
    private final Map<AbstractClientPlayerEntity, Float> playerAlphaMap = new HashMap<>();


    public ESP(WorldRenderContext ctx, HashMap<String, Boolean> hackStatus) {
        this.context = ctx;
        this.renderUtils = new RenderUtils();
        this.hackStatus = hackStatus;
    }

    public void update() {
        if (mc.world == null || mc.player == null) return;

        if (System.currentTimeMillis() - lastUpdateTime > 50) { // 每 50ms 更新一次
            cachedHostiles = theMobLocator.getNearbyMonsters(mc.world, mc.player, 96F);
            cachedPlayers = theMobLocator.getPlayers(mc.world, mc.player);

            lastUpdateTime = System.currentTimeMillis();

            for (HostileEntity hostile : cachedHostiles) {
                double distance = mc.player.getPos().distanceTo(hostile.getPos());
                float alpha = Math.max(0, Math.min(0.2F, (float) ((distance / 96D))));
                hostileAlphaMap.put(hostile, alpha);
            }

            for (AbstractClientPlayerEntity player : cachedPlayers) {
                double distance = mc.player.getPos().distanceTo(player.getPos());
                float alpha = Math.max(0, Math.min(0.2F, (float) ((distance / 96D))));
                playerAlphaMap.put(player, alpha);
            }

            if (hackStatus.getOrDefault("mobESP", true)) {
                renderMobESP();
            }
            if (hackStatus.getOrDefault("playerESP", true)) {
                renderPlayerESP();
            }


        }

    }



    private void renderMobESP() {
        if (mc.world == null || mc.player == null) return;

        Vec3d cameraPos = context.camera().getPos();
        for (HostileEntity hostile : cachedHostiles) {
            float alpha = hostileAlphaMap.getOrDefault(hostile, 1.0F);
            renderUtils.drawBox(hostile.getPos(), hostile.getWidth(), hostile.getHeight(),
                    cameraPos, new Vector3f(1F, 0F, 0F), alpha, true);
        }


    }

    private void renderPlayerESP() {
        if (mc.world == null || mc.player == null) return;

        Vec3d cameraPos = context.camera().getPos();
        for (AbstractClientPlayerEntity player : cachedPlayers) {
            float alpha = playerAlphaMap.getOrDefault(player, 1.0F);
            renderUtils.drawBox(player.getPos(), player.getWidth(), player.getHeight(),
                    cameraPos, new Vector3f(1F, 0F, 0F), alpha, true);
        }


    }
}
