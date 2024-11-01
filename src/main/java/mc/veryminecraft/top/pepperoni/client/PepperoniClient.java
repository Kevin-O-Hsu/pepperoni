package mc.veryminecraft.top.pepperoni.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import mc.veryminecraft.top.pepperoni.client.gui.GuiScreen;
import mc.veryminecraft.top.pepperoni.client.hacks.ESP;



public class PepperoniClient implements ClientModInitializer {

    private final MinecraftClient mc = MinecraftClient.getInstance();


    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::run);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (GuiScreen.toggleScreenKey.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new GuiScreen()); // 打开 Screen
            }
        });
    }


    public void run(WorldRenderContext context) {
        ESP esp = new ESP(context);


        if (mc.world == null || mc.player == null) return;


        esp.mobESP();
        esp.playerESP();

    }



}
