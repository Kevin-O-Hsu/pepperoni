package mc.veryminecraft.top.pepperoni.client.hacks;

import mc.veryminecraft.top.pepperoni.client.utils.MobLocator;
import mc.veryminecraft.top.pepperoni.client.utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import java.util.List;
import java.util.HashMap;



public class ESP {

    private final MobLocator theMobLocator = new MobLocator();
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final WorldRenderContext context;
    private final RenderUtils renderUtils;
    private final HashMap<String, Boolean> hackStatus;

    public ESP(WorldRenderContext ctx, HashMap<String, Boolean> hackStatus) {
        this.context = ctx;
        this.renderUtils = new RenderUtils();
        this.hackStatus = hackStatus;
    }

    public void update() {
        if (hackStatus.getOrDefault("mobESP", false)) {
            renderMobESP();
        }
        if (hackStatus.getOrDefault("playerESP", false)) {
            renderPlayerESP();
        }
    }

    private void renderMobESP() {
        if (mc.world == null || mc.player == null) return;
        List<HostileEntity> allHostiles = theMobLocator.getNearbyMonsters(mc.world, mc.player, 96F);
        if (!allHostiles.isEmpty()) {
            Vector3f hostileBoxColor = new Vector3f(1F, 0F, 0F);
            for (HostileEntity _hostile : allHostiles) {
                Vec3d hostilePos = _hostile.getPos();
                Vec3d cameraPos = context.camera().getPos();


                double distance = mc.player.getPos().distanceTo(hostilePos);
                float alpha = Math.max(0.1F, Math.min(1.0F, (float) (1 - (distance / 96D))));


                renderUtils.drawBox(hostilePos, _hostile.getWidth(), _hostile.getHeight(), cameraPos, hostileBoxColor, alpha, true);
            }
        }
    }

    private void renderPlayerESP() {
        if (mc.world == null || mc.player == null) return;
        List<AbstractClientPlayerEntity> exceptMePlayers = theMobLocator.getPlayers(mc.world, mc.player);
        if (!exceptMePlayers.isEmpty()) {
            Vector3f playerBoxColor = new Vector3f(0F, 1F, 0F);
            for (PlayerEntity _player : exceptMePlayers) {
                Vec3d playerPos = _player.getPos();
                Vec3d cameraPos = context.camera().getPos();

                double distance = mc.player.getPos().distanceTo(playerPos);
                float alpha = Math.max(0.1F, Math.min(1.0F, (float) (1 - (distance / 96D))));

                renderUtils.drawBox(playerPos, _player.getWidth(), _player.getHeight(), cameraPos, playerBoxColor, alpha, true);
            }
        }
    }
}
