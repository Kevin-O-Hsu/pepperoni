package mc.veryminecraft.top.pepperoni_1214.client.hacks;

import mc.veryminecraft.top.pepperoni_1214.client.utils.Aim;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;

public class AimNearbyMonster extends Hack{

    private MobEntity nearestHostile = null;


    public AimNearbyMonster(WorldRenderContext ctx) {
        super(ctx);
        onHack = false;
    }

    @Override
    public void update() {
        if (mc.world == null || mc.player == null) return;


        double distance = 96;
        for (MobEntity hostile : theMobLocator.getNearbyMonsters(mc.world, mc.player, 96F)) {
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
        if (nearestHostile instanceof CreeperEntity creeperHostileEntity){
            if (creeperHostileEntity.getClientFuseTime(this.context.camera().getLastTickDelta()) <= 0){
                nearestHostile = null;
                Aim.adjustPlayerPitchView(mc.player, 0);
                return;
            }
        }

        if (mc.player.getPos().distanceTo(nearestHostile.getPos()) > 5){
            nearestHostile = null;
            return;
        }

        Aim.lookAt(mc.player, nearestHostile, context, false);

    }


}
