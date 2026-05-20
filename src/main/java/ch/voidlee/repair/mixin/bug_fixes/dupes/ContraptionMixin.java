package ch.voidlee.repair.mixin.bug_fixes.dupes;

import com.simibubi.create.content.contraptions.Contraption;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

// https://github.com/Creators-of-Create/Create/pull/9399
@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @Shadow(remap = false)
    protected Map<BlockPos, StructureBlockInfo> blocks;

    @Inject(method = "<init>()V", at = @At("TAIL"), remap = false)
    private void create_repair$blocksStoredInLinkedHashMap(CallbackInfo ci) {
        this.blocks = new LinkedHashMap<>(this.blocks);
    }

    @Inject(method = "removeBlocksFromWorld", at = @At("HEAD"), remap = false)
    private void create_repair$iterateBlocksInReverse(Level world, BlockPos offset, CallbackInfo ci) {
        List<Map.Entry<BlockPos, StructureBlockInfo>> entries = new ArrayList<>(this.blocks.entrySet());
        Collections.reverse(entries);
        this.blocks.clear();
        entries.forEach(e -> this.blocks.put(e.getKey(), e.getValue()));
    }
}