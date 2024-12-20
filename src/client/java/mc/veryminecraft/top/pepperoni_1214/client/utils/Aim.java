package mc.veryminecraft.top.pepperoni_1214.client.utils;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Aim {

    public static void adjustPlayerYawView(ClientPlayerEntity player, float yaw) {
        player.setYaw(yaw);
    }

    public static void adjustPlayerPitchView(ClientPlayerEntity player, float pitch) {
        player.setPitch(pitch);
    }

    public static void adjustPlayerHeadYawView(ClientPlayerEntity player, float headYaw){
        player.headYaw = headYaw;
    }


    public static float calculateRelativeYaw(Vec3d playerPos, Vec3d targetPos) {


        double deltaX = targetPos.x - playerPos.x;
        double deltaZ = targetPos.z - playerPos.z;

        // Calculate yaw in radians
        double yawRadians = Math.atan2(deltaZ, deltaX);

        // Convert yaw to degrees and adjust to Minecraft's coordinate system
        float yawDegrees = (float) Math.toDegrees(yawRadians) - 90.0f;

        // Normalize yaw to be between -180 and 180
        while (yawDegrees < -180.0f) yawDegrees += 360.0f;
        while (yawDegrees > 180.0f) yawDegrees -= 360.0f;

        return yawDegrees;
    }

    public static float calculateRelativePitch(Vec3d playerPos, Vec3d targetPos) {



        double deltaX = targetPos.x - playerPos.x;
        double deltaY = targetPos.y - playerPos.y;
        double deltaZ = targetPos.z - playerPos.z;

        // Calculate the horizontal distance
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        // Calculate pitch in radians
        double pitchRadians = Math.atan2(-deltaY, horizontalDistance); // 注意这里的 -deltaY

        // Convert pitch to degrees
        float pitchDegrees = (float) Math.toDegrees(pitchRadians);

        // Normalize pitch to be between -90 and 90
        while (pitchDegrees < -90.0f) pitchDegrees += 180.0f;
        while (pitchDegrees > 90.0f) pitchDegrees -= 180.0f;

        return pitchDegrees;
    }



    public static void lookAt(ClientPlayerEntity player, Entity entity, WorldRenderContext context, boolean vertical){
        float tickDelta = context.camera().getLastTickDelta();
        
        Vec3d interpolatedPos = RenderUtils.interpolateEntityPosition(entity, tickDelta, true);
        Vec3d interpolatedPlayerCameraPos = RenderUtils.interpolatePlayerPosition(player, tickDelta);

        Vec3d interpolatedPlayerPos = RenderUtils.interpolateEntityPosition(player, tickDelta, false);
        
        double lastTickPlayerEyeY = player.lastRenderY + player.getEyeHeight(entity.getPose());
        double currentTickPlayerEyeY = player.getEyeY();
        double interpolatedPlayerEyeY = lastTickPlayerEyeY + ((currentTickPlayerEyeY - lastTickPlayerEyeY) * tickDelta);
        Vec3d interpolatedPlayerEyePos = new Vec3d(interpolatedPlayerPos.x, interpolatedPlayerEyeY, interpolatedPlayerPos.z);
        
        if (vertical){
            Aim.adjustPlayerYawView(player, Aim.calculateRelativeYaw(interpolatedPlayerCameraPos, interpolatedPos));
            Aim.adjustPlayerPitchView(player, Aim.calculateRelativePitch(interpolatedPlayerCameraPos, interpolatedPos));

        } else {
            Vec3d entityPos = RenderUtils.interpolateEntityPosition(entity, tickDelta, false);
            Vec3d entityHeadPos = new Vec3d(entityPos.x, entityPos.y + entity.getHeight(), entityPos.z);

            if ((entityPos.getY() <= currentTickPlayerEyeY) && (entityHeadPos.getY() >= currentTickPlayerEyeY)){
                    Aim.adjustPlayerPitchView(player, 0);
            } else if (entityPos.getY() > interpolatedPlayerEyeY){
                Aim.adjustPlayerPitchView(player, (float) (-1 * Math.toDegrees(Math.asin(
                        (entityPos.getY() - interpolatedPlayerEyeY) / interpolatedPlayerEyePos.distanceTo(entityPos))))
                );
            } else if (entityHeadPos.getY() < interpolatedPlayerEyeY) {
                Aim.adjustPlayerPitchView(player, (float) (90 - (Math.toDegrees(Math.acos(
                        (interpolatedPlayerEyeY - entityHeadPos.getY()) / (interpolatedPlayerEyePos.distanceTo(entityHeadPos)))
                ))));
            }
            Aim.adjustPlayerYawView(player, Aim.calculateRelativeYaw(interpolatedPlayerPos, interpolatedPos));

        }
    }







}
