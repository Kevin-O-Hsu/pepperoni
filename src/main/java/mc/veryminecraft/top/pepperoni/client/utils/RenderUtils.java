package mc.veryminecraft.top.pepperoni.client.utils;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3f;


public class RenderUtils {

    private final Tessellator tessellator;
    public BufferBuilder buffer;


    public RenderUtils(WorldRenderContext ctx){
        this.tessellator = Tessellator.getInstance();

    }



    public void drawBox(Vec3d pos, float width, float height, Vec3d cameraPos, Vector3f boxColor, float alpha) {
        this.buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        startRender();


        // 计算盒子的8个顶点位置
        float x0 = (float) (pos.x - width / 2);
        float x1 = (float) (pos.x + width / 2);
        float y0 = (float) pos.y;
        float y1 = (float) (pos.y + height);
        float z0 = (float) (pos.z - width / 2);
        float z1 = (float) (pos.z + width / 2);

        // 计算顶点位置
        Vector3f v0 = new Vector3f(x0, y0, z0);  // 左下前
        Vector3f v1 = new Vector3f(x1, y0, z0);  // 右下前
        Vector3f v2 = new Vector3f(x1, y1, z0);  // 右上前
        Vector3f v3 = new Vector3f(x0, y1, z0);  // 左上前
        Vector3f v4 = new Vector3f(x0, y0, z1);  // 左下后
        Vector3f v5 = new Vector3f(x1, y0, z1);  // 右下后
        Vector3f v6 = new Vector3f(x1, y1, z1);  // 右上后
        Vector3f v7 = new Vector3f(x0, y1, z1);  // 左上后

        // 使用简化后的 drawLine 函数绘制边框
        drawLine(cameraPos, v0, v1, boxColor, alpha); // 前面底边
        drawLine(cameraPos, v1, v2, boxColor, alpha); // 前面右边
        drawLine(cameraPos, v2, v3, boxColor, alpha); // 前面顶边
        drawLine(cameraPos, v3, v0, boxColor, alpha); // 前面左边

        drawLine(cameraPos, v4, v5, boxColor, alpha); // 后面底边
        drawLine(cameraPos, v5, v6, boxColor, alpha); // 后面右边
        drawLine(cameraPos, v6, v7, boxColor, alpha); // 后面顶边
        drawLine(cameraPos, v7, v4, boxColor, alpha); // 后面左边

        drawLine(cameraPos, v0, v4, boxColor, alpha); // 连接前后左边
        drawLine(cameraPos, v1, v5, boxColor, alpha); // 连接前后右边
        drawLine(cameraPos, v2, v6, boxColor, alpha); // 连接前后右边
        drawLine(cameraPos, v3, v7, boxColor, alpha); // 连接前后左边


        endRender(this.buffer);
    }




    public void drawLine(Vec3d cameraPos, Vector3f vertex1, Vector3f vertex2, Vector3f lineColor, float alpha) {

        // 将顶点相对于相机位置进行平移，使其正确显示在相机视角下
        float x1 = vertex1.x - (float) cameraPos.x;
        float y1 = vertex1.y - (float) cameraPos.y;
        float z1 = vertex1.z - (float) cameraPos.z;

        float x2 = vertex2.x - (float) cameraPos.x;
        float y2 = vertex2.y - (float) cameraPos.y;
        float z2 = vertex2.z - (float) cameraPos.z;

        // 向缓冲区中添加顶点和颜色
        this.buffer.vertex(x1, y1, z1).color(lineColor.x, lineColor.y, lineColor.z, alpha);
        this.buffer.vertex(x2, y2, z2).color(lineColor.x, lineColor.y, lineColor.z, alpha);



    }

    public void startRender(){
        RenderSystem.disableDepthTest();  // 禁用深度测试，避免透明物体被遮挡
        RenderSystem.enableBlend();       // 启用透明度混合
        RenderSystem.defaultBlendFunc();  // 设置默认混合函数
    }

    public void endRender(BufferBuilder buffer){
        BufferRenderer.drawWithGlobalProgram(buffer.end());  // 绘制顶点数据
        RenderSystem.disableBlend();      // 禁用透明度混合，防止影响后续渲染
        RenderSystem.enableDepthTest();   // 重新启用深度测试
    }




}
