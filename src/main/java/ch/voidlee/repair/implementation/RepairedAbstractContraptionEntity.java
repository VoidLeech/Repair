package ch.voidlee.repair.implementation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
public interface RepairedAbstractContraptionEntity {
    default boolean create_repair$canInteractWithBlock(Player player, BlockPos localPos, double distance) {
        return create_repair$canInteractWithBlock(player, Vec3.atCenterOf(localPos), distance);
    }

    default boolean create_repair$canInteractWithBlock(Player player, Vec3 localPos, double distance) {
        BlockPos pos = BlockPos.containing(toGlobalVector(localPos, 0));
        return player.canReach(pos, distance);
    }

    Vec3 toGlobalVector(Vec3 localVec, float partialTicks);
}
