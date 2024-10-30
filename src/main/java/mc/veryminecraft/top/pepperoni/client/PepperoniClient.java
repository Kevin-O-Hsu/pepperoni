package mc.veryminecraft.top.pepperoni.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.joml.Vector3f;
import mc.veryminecraft.top.pepperoni.client.utils.RenderUtils;
import mc.veryminecraft.top.pepperoni.client.utils.MobLocator;

public class PepperoniClient implements ClientModInitializer {

    private final MobLocator theMobLocator = new MobLocator();
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private WorldRenderContext context;
    private RenderUtils renderUtils;


    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::run);

    }


    public void run(WorldRenderContext context) {

        this.renderUtils = new RenderUtils(context);
        this.context = context;


        if (mc.world == null || mc.player == null) return;


        mobESP();
        playerESP();

    }

    public void playerESP() {
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


    public void mobESP() {
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
