package mc.veryminecraft.top.pepperoni_1214.client.gui;

import mc.veryminecraft.top.pepperoni_1214.client.HackList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;



public class GuiScreen extends Screen {

    public static final KeyBinding toggleScreenKey = new KeyBinding("key.veryminecraft.opensc", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "category.veryminecraft");


    public GuiScreen(HackList hackList) {
        super(Text.of("GuiScreen"));
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Mob ESP"), (btn) -> hackList.mobESP.toggleHack()).dimensions(100, 100, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Player ESP"), (btn) -> hackList.playerESP.toggleHack()).dimensions(100, 130, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Aim nearby Monster"), (btn) -> hackList.aimNearbyMonster.toggleHack()).dimensions(100, 160, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Aim nearby Player"), (btn) -> hackList.aimNearbyPlayer.toggleHack()).dimensions(100, 190, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Derp"), (btn) -> hackList.derpHack.toggleHack()).dimensions(100, 220, 200, 20).build());
    }




    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x88000000);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_PERIOD) {
            MinecraftClient.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
