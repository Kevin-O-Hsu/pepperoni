package mc.veryminecraft.top.pepperoni.client.hacks;

import mc.veryminecraft.top.pepperoni.client.utils.Aim;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;

public class AimNearbyPlayer extends Hack{
    
    private PlayerEntity nearestPlayer = null;


    public AimNearbyPlayer(WorldRenderContext ctx) {
        super(ctx);
        onHack = false;
    }
    
    @Override
    public void update(){
        if (mc.world == null || mc.player == null) return;

        double distance = 96;
        for (PlayerEntity player : theMobLocator.getPlayers(mc.world, mc.player)) {
            double nowHostileDistance = mc.player.getPos().distanceTo(player.getPos());
            if ((nowHostileDistance < distance) && (nowHostileDistance < 5)){
                nearestPlayer = player;
                distance = mc.player.getPos().distanceTo(player.getPos());
            }
        }

        if (onHack) {
            hackMethod();
        }
        
        
    }
    
    public void hackMethod(){
        if (mc.world == null || mc.player == null) return;
        if (nearestPlayer == null) return;
        if (nearestPlayer.isDead()){
            nearestPlayer = null;
            Aim.adjustPlayerPitchView(mc.player, 0);
            return;
        }
        if (mc.player.getPos().distanceTo(nearestPlayer.getPos()) > 5){
            nearestPlayer = null;
            return;
        }

        Aim.lookAt(mc.player, nearestPlayer, context);
        
    }

    
}
