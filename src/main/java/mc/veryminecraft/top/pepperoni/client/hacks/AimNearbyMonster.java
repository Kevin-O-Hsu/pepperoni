package mc.veryminecraft.top.pepperoni.client.hacks;

import mc.veryminecraft.top.pepperoni.client.utils.Aim;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;

public class AimNearbyMonster extends Hack{

    HostileEntity nearestHostile = null;


    public AimNearbyMonster(WorldRenderContext ctx) {
        super(ctx);
    }

    @Override
    public void update() {
        if (mc.world == null || mc.player == null) return;


        double distance = 96;
        for (HostileEntity hostile : theMobLocator.getNearbyMonsters(mc.world, mc.player, 96F)) {
            double nowHostileDistance = mc.player.getPos().distanceTo(hostile.getPos());
            if ((nowHostileDistance < distance) && (nowHostileDistance < 5)){
                nearestHostile = hostile;
                distance = mc.player.getPos().distanceTo(hostile.getPos());
            }
        }
        if (onHack) {
            hackMethod();
        }
    }

    @Override
    public void hackMethod() {
        if (mc.world == null || mc.player == null) return;
        if (nearestHostile == null) return;
        if (nearestHostile.isDead()){
            nearestHostile = null;
            Aim.adjustPlayerPitchView(mc.player, 0);
            return;
        }

        Vec3d interpolatedPos = interpolateEntityPosition(nearestHostile, context.camera().getLastTickDelta());
        Vec3d interpolatedPlayerPos = interpolateEntityPosition(mc.player, context.camera().getLastTickDelta());
        Aim.adjustPlayerYawView(mc.player, Aim.calculateRelativeYaw(interpolatedPlayerPos, interpolatedPos));
        Aim.adjustPlayerPitchView(mc.player, Aim.calculateRelativePitch(interpolatedPlayerPos, interpolatedPos));

    }


}
