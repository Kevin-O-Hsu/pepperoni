package mc.veryminecraft.top.pepperoni.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import mc.veryminecraft.top.pepperoni.client.gui.GuiScreen;
import mc.veryminecraft.top.pepperoni.client.hacks.ESP;
import java.util.HashMap;



public class PepperoniClient implements ClientModInitializer, GuiScreen.ResultCallback {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final HashMap<String, Boolean> hackStatus = new HashMap<>();

    @Override
    public void onInitializeClient() {
        // 初始化 hack 状态
        hackStatus.put("playerESP", true);
        hackStatus.put("mobESP", true);

        // 注册渲染事件
        WorldRenderEvents.AFTER_ENTITIES.register(this::run);

        // 注册键位事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (GuiScreen.toggleScreenKey.wasPressed()) {
                mc.setScreen(new GuiScreen(this, hackStatus)); // 打开 GuiScreen
            }
        });
    }

    public void run(WorldRenderContext context) {
        // 更新 ESP 状态
        ESP esp = new ESP(context, hackStatus);
        esp.update();
    }

    @Override
    public void onResult(HashMap<String, Boolean> hackStatus) {
        // 更新 hack 状态
        this.hackStatus.putAll(hackStatus);
    }
}
