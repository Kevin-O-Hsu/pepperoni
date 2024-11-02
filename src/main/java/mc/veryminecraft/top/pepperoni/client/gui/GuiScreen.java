package mc.veryminecraft.top.pepperoni.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.HashMap;



public class GuiScreen extends Screen {

    private final ResultCallback callback;
    public static final KeyBinding toggleScreenKey = new KeyBinding("key.veryminecraft.opensc", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "category.veryminecraft");
    private final HashMap<String, Boolean> hackStatus;

    public interface ResultCallback {
        void onResult(HashMap<String, Boolean> result);
    }

    public GuiScreen(ResultCallback callback, HashMap<String, Boolean> hackStatus) {
        super(Text.of("GuiScreen"));
        this.callback = callback;
        this.hackStatus = hackStatus;

        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Player ESP"), (btn) -> toggleESP("playerESP")).dimensions(100, 100, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Toggle Mob ESP"), (btn) -> toggleESP("mobESP")).dimensions(100, 130, 200, 20).build());
    }

    private void toggleESP(String hack) {
        hackStatus.put(hack, !hackStatus.get(hack)); // 切换状态
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
            callback.onResult(hackStatus); // 传递当前状态
            MinecraftClient.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
