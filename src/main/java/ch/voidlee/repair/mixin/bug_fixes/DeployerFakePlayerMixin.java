package ch.voidlee.repair.mixin.bug_fixes;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeployerFakePlayer.class)
public class DeployerFakePlayerMixin extends FakePlayer {
    public DeployerFakePlayerMixin(ServerLevel level, GameProfile name) {
        super(level, name);
    }

    @Override
    public boolean startRiding(@NotNull Entity pVehicle, boolean pForce) {
        return false;
    }
}
