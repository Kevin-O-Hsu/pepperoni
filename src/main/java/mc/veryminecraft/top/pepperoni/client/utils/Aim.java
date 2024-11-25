package mc.veryminecraft.top.pepperoni.client.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Aim {

    public static void adjustPlayerYawView(ClientPlayerEntity player, float yaw) {
        player.setYaw(yaw);
    }

    public static void adjustPlayerPitchView(ClientPlayerEntity player, float pitch) {
        player.setPitch(pitch);
    }

    public static void movePlayerYawView(ClientPlayerEntity player, float yaw){
        player.setYaw(player.getYaw() + yaw);
    }

    public static void movePlayerPitchView(ClientPlayerEntity player, float pitch){
        player.setPitch(player.getPitch() + pitch);
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


}
