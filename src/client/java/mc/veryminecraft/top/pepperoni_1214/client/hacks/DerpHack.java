package mc.veryminecraft.top.pepperoni_1214.client.hacks;

import mc.veryminecraft.top.pepperoni_1214.client.utils.Aim;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class DerpHack extends Hack{

    public int lastYaw;

    public DerpHack(WorldRenderContext ctx) {
        super(ctx);
    }


    public void update() {

        if (mc.world == null || mc.player == null) return;

        if (onHack) {
            hackMethod();
        }


    }

    public void hackMethod(){
        if (mc.world == null || mc.player == null) return;

        int currentHeadYaw;
        if (lastYaw == 0) {
            currentHeadYaw = (int) mc.player.getHeadYaw();
        } else {
            currentHeadYaw = lastYaw;
        }

        var newHeadYaw = currentHeadYaw + getRandomInt(3, 8);
        System.out.println("Current head yaw: " + currentHeadYaw + " New head yaw: " + newHeadYaw);
        Aim.adjustPlayerHeadYawView(mc.player, newHeadYaw);

        lastYaw = newHeadYaw;


    }


}
