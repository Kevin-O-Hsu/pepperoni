package mc.veryminecraft.top.pepperoni_1214.client;

import mc.veryminecraft.top.pepperoni_1214.client.hacks.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HackList {

    public MobESPHack mobESP;
    public PlayerESPHack playerESP;
    public AimNearbyMonster aimNearbyMonster;
    public AimNearbyPlayer aimNearbyPlayer;
    public DerpHack derpHack;


    public HackList(WorldRenderContext ctx){
        playerESP = new PlayerESPHack(ctx);
        mobESP = new MobESPHack(ctx);
        aimNearbyMonster = new AimNearbyMonster(ctx);
        aimNearbyPlayer = new AimNearbyPlayer(ctx);
        derpHack = new DerpHack(ctx);
    }

    public List<Object> getAllFieldValues(){
        List<Object> fieldValues = new ArrayList<>();

        // 获取类的所有字段
        Field[] fields = this.getClass().getDeclaredFields();

        // 遍历所有字段
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使私有字段可访问
                fieldValues.add(field.get(this)); // 获取字段的值并添加到List中
            } catch (IllegalAccessException e) {
                // 捕获异常并处理，避免抛出
                System.err.println("无法访问字段: " + field.getName() + "，错误信息: " + e.getMessage());
                fieldValues.add(null);  // 或者返回null来表示无法访问该字段
            }
        }

        return fieldValues;
    }

}
