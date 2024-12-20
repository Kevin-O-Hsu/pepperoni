package mc.veryminecraft.top.pepperoni_1214.client.utils;


import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.include.com.google.common.base.Predicate;


public class MobLocator {

    public List<MobEntity> getNearbyMonsters(ClientWorld world, PlayerEntity player, double radius) {
        Vec3d playerPos = player.getPos();

        // 定义搜索范围的立方体
        Box searchBox = new Box(
                playerPos.x - radius, playerPos.y - radius, playerPos.z - radius,
                playerPos.x + radius, playerPos.y + radius, playerPos.z + radius
        );

        // 过滤条件：仅保留活着的实体
        Predicate<MobEntity> predicate = entity -> entity != null && entity.isAlive();

        // 获取搜索范围内的所有怪物实体
        List<HostileEntity> monsters = world.getEntitiesByClass(HostileEntity.class, searchBox, predicate);

        List<SlimeEntity> slimes = world.getEntitiesByClass(SlimeEntity.class, searchBox, predicate);
        List<PillagerEntity> pillagers = world.getEntitiesByClass(PillagerEntity.class, searchBox, predicate);
        List<MagmaCubeEntity> magmaCubes = world.getEntitiesByClass(MagmaCubeEntity.class, searchBox, predicate);
        List<PolarBearEntity> polarBears = world.getEntitiesByClass(PolarBearEntity.class, searchBox, predicate);
        List<PiglinEntity> piglins = world.getEntitiesByClass(PiglinEntity.class, searchBox, predicate);

        List<MobEntity> allMonsters = new ArrayList<>();
        allMonsters.addAll(monsters);
        allMonsters.addAll(slimes);
        allMonsters.addAll(pillagers);
        allMonsters.addAll(magmaCubes);
        allMonsters.addAll(polarBears);
        allMonsters.addAll(piglins);

        return allMonsters;
    }

    public List<AbstractClientPlayerEntity> getPlayers(ClientWorld world, PlayerEntity player){
        List<AbstractClientPlayerEntity> allPlayers = world.getPlayers();
        return allPlayers.stream()
                .filter(players -> players != player)
                .toList();


    }




}
