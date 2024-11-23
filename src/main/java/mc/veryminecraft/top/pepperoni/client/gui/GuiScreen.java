package mc.veryminecraft.top.pepperoni.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import mc.veryminecraft.top.pepperoni.client.hacks.ESP;



public class GuiScreen extends Screen {

    public static final KeyBinding toggleScreenKey = new KeyBinding("key.veryminecraft.opensc", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "category.veryminecraft");


    public GuiScreen(ESP esp) {
        super(Text.of("GuiScreen"));

        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Player ESP"), (btn) -> esp.togglePlayerESP()).dimensions(100, 100, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Mob ESP"), (btn) -> esp.toggleMobESP()).dimensions(100, 130, 200, 20).build());
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
