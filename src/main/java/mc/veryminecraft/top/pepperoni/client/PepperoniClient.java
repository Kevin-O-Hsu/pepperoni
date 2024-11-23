package mc.veryminecraft.top.pepperoni.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import mc.veryminecraft.top.pepperoni.client.gui.GuiScreen;
import mc.veryminecraft.top.pepperoni.client.hacks.ESP;



public class PepperoniClient implements ClientModInitializer{

    private final MinecraftClient mc = MinecraftClient.getInstance();
    public ESP esp;
    private boolean initialized = false;


    @Override
    public void onInitializeClient() {
        // 注册渲染事件
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::run);

        // 注册键位事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (GuiScreen.toggleScreenKey.wasPressed()) {
                mc.setScreen(new GuiScreen(this.esp)); // 打开 GuiScreen
            }
        });
    }

    public void run(WorldRenderContext context) {
        if (!initialized) {
            System.out.println("init esp");
            this.esp = new ESP(context);
            initialized = true;
        }

        this.esp.update();
    }

}
