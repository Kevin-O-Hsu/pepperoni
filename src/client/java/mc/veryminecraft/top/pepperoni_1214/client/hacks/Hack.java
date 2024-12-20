package mc.veryminecraft.top.pepperoni_1214.client.hacks;

import mc.veryminecraft.top.pepperoni_1214.client.utils.MobLocator;
import mc.veryminecraft.top.pepperoni_1214.client.utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;

import java.util.Random;


public class Hack {

    public final MobLocator theMobLocator = new MobLocator();
    public final MinecraftClient mc = MinecraftClient.getInstance();
    public final WorldRenderContext context;
    public final RenderUtils renderUtils;
    public boolean onHack = false;
    public final Random random = new Random();


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

    public int getRandomInt(int min, int max){

        return random.nextInt((max - min) + 1) + min;
    }




}
