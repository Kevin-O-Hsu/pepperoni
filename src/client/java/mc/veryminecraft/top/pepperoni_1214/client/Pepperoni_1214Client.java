package mc.veryminecraft.top.pepperoni_1214.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import mc.veryminecraft.top.pepperoni_1214.client.gui.GuiScreen;
import mc.veryminecraft.top.pepperoni_1214.client.hacks.*;



public class Pepperoni_1214Client implements ClientModInitializer{

    private final MinecraftClient mc = MinecraftClient.getInstance();
    public HackList hackList;
    private boolean initialized = false;



    @Override
    public void onInitializeClient() {
        // 注册渲染事件
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::run);

        // 注册键位事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (GuiScreen.toggleScreenKey.wasPressed()) {
                mc.setScreen(new GuiScreen(hackList)); // 打开 GuiScreen
            }
        });
    }

    public void run(WorldRenderContext context){
        if (!initialized) {
            System.out.println("init esp");
            hackList = new HackList(context);
            initialized = true;
        }

        for (Object hack : hackList.getAllFieldValues()){
            if (hack instanceof Hack){
                ((Hack) hack).update();
            }
        }
    }

}
