package ch.voidlee.repair.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class RepairMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (matches("SchematicPrinterMixin", mixinClassName)) {
            // Both of these implemented a fix before we got to it
            return !isModEarlyLoaded("tfg") && !isModEarlyLoaded("create_schematicannon_dupe_fix");
        }
        if (matches("StationBlockEntityMixin", mixinClassName)) {
            // Also separately fixed by tfg
            return !isModEarlyLoaded("tfg");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    private boolean isModEarlyLoaded(String modId){
        return LoadingModList.get().getModFileById(modId) != null;
    }

    private boolean matches(String mixinToMatch, String mixinClassName) {
        return mixinClassName.equals("ch.voidlee.repair.mixin." + mixinToMatch);
    }
}
