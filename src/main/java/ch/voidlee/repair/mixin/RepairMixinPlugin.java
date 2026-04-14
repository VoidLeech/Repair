package ch.voidlee.repair.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
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
        if (matches("bug_fixes.dupes.SchematicPrinterMixin", mixinClassName)) {
            // These implemented a fix before we got to it; tfg later removed them after adding us to their pack
            return (!isModEarlyLoaded("tfg") || LoadingModList.get().getModFileById("tfg")
                    .getMods().get(0).getVersion().compareTo(new DefaultArtifactVersion("0.9.4")) >= 0) &&
                   !isModEarlyLoaded("create_schematicannon_dupe_fix") && // Ported from tfg
                   !isModEarlyLoaded("create_6_0_8_backported_fixes"); // Rebrand of dupe fix
        }
        if (matches("client.bug_fixes.XaeroTrainMapMixin", mixinClassName)) {
            // Again
            return (!isModEarlyLoaded("tfg") || LoadingModList.get().getModFileById("tfg")
                    // Not a mistake in the version, they just missed we also fixed this when removing their mixins
                    .getMods().get(0).getVersion().compareTo(new DefaultArtifactVersion("0.9.5")) >= 0) &&
                   !isModEarlyLoaded("create_6_0_8_backported_fixes"); // Ported from tfg
        }
        if (matches("crash_fixes.StationBlockEntityMixin", mixinClassName)) {
            // Also separately fixed by tfg
            return !isModEarlyLoaded("tfg") || LoadingModList.get().getModFileById("tfg")
                    .getMods().get(0).getVersion().compareTo(new DefaultArtifactVersion("0.9.4")) >= 0;
        }

        // Fabric Mixin Unavailability
        if (matches("client.bug_fixes.backtank_glint.LayeredArmorItemMixin", mixinClassName)) {
            return fabricMixinAvailable();
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

    private boolean fabricMixinAvailable() {
        return isModEarlyLoaded("connectormod") || isModEarlyLoaded("mixinbooster");
    }

    private boolean matches(String mixinToMatch, String mixinClassName) {
        return mixinClassName.equals("ch.voidlee.repair.mixin." + mixinToMatch);
    }
}
