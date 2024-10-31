package mc.veryminecraft.top.pepperoni.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;


public class GuiScreen extends Screen {

    public static final KeyBinding toggleScreenKey = new KeyBinding(
            "key.veryminecraft.opensc",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD,
            "category.veryminecraft"
    );

    public GuiScreen() {
        super(Text.of("Transparent Screen"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 使用新的 DrawContext API 设置半透明黑色背景
        context.fill(0, 0, this.width, this.height, 0x88000000);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false; // 不暂停游戏
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // 检测按下 . 键时关闭 Screen
        if (keyCode == GLFW.GLFW_KEY_PERIOD) {
            MinecraftClient.getInstance().setScreen(null); // 关闭当前 Screen
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

}
