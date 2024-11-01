package mc.veryminecraft.top.pepperoni.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
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
        super(Text.of("GuiScreen"));
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // 使用屏幕的实际宽度和高度来计算按钮的位置
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Click Me"), (btn) -> {
                    onButtonClick(); // 触发自定义的点击函数
                }).dimensions((screenWidth - 100) / 2, (screenHeight - 20) / 2, 100, 20) // 居中按钮
                .build();

        // 添加按钮到屏幕
        this.addDrawableChild(buttonWidget);
    }

    private void onButtonClick() {
        System.out.println("Button clicked!");
        // 执行其他逻辑
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
