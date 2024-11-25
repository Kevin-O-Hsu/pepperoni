package mc.veryminecraft.top.pepperoni.client.hacks;

import mc.veryminecraft.top.pepperoni.client.utils.MobLocator;
import mc.veryminecraft.top.pepperoni.client.utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;


public class Hack {

    public final MobLocator theMobLocator = new MobLocator();
    public final MinecraftClient mc = MinecraftClient.getInstance();
    public final WorldRenderContext context;
    public final RenderUtils renderUtils;
    public boolean onHack = true;


    public Hack(WorldRenderContext ctx) {
        this.context = ctx;
        this.renderUtils = new RenderUtils();
    }

    public void update() {

        if (mc.world == null || mc.player == null) return;

        if (onHack) {
            hackMethod();
        }


    }

    public void toggleHack(){
        onHack = !onHack;
    }


    public void hackMethod(){

    }


    public float calculateAlpha(double distance, double maxDistance, float minAlpha, float maxAlpha) {
        return Math.max(minAlpha, Math.min(maxAlpha, (float) (maxAlpha - (distance / maxDistance) * (maxAlpha - minAlpha))));
    }

    public Vec3d interpolateEntityPosition(Entity entity, float partialTicks, boolean isEyePos) {
        // 获取实体的前一帧位置
        Vec3d prevPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ);

        // 获取实体的当前帧位置
        Vec3d currentPos = entity.getPos();

        // 插值计算

        Vec3d originPos = prevPos.lerp(currentPos, partialTicks);
        if (isEyePos) {
            // 如果是眼睛位置，则需要加上眼睛的高度
            return originPos.add(0, entity.getEyeHeight(entity.getPose()), 0);
        } else{
            return originPos;
        }
    }

    public Vec3d interpolatePlayerPosition(PlayerEntity player, float partialTicks){
        return player.getCameraPosVec(partialTicks);
    }



}
