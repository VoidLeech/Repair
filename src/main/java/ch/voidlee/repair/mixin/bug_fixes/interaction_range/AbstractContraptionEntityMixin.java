package ch.voidlee.repair.mixin.bug_fixes.interaction_range;

import ch.voidlee.repair.implementation.RepairedAbstractContraptionEntity;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import org.spongepowered.asm.mixin.Mixin;

// https://github.com/Creators-of-Create/Create/commit/f99fe5778b127d45c1fc2dc7d1f3599ba9f5e71a
@Mixin(AbstractContraptionEntity.class)
public abstract class AbstractContraptionEntityMixin implements RepairedAbstractContraptionEntity {
    // Defaulted
}