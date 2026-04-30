package ch.voidlee.repair.mixin;

import com.simibubi.create.Create;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.util.version.SemanticVersionImpl;
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
        if (matches("bug_fixes.SchematicannonBlockEntityMixin", mixinClassName)) {
            Version createFabricVersion = FabricLoader.getInstance().getModContainer(Create.ID).get().getMetadata().getVersion();
            SemanticVersion create6081version = null;
            try {
                create6081version = new SemanticVersionImpl("6.0.8.1+build.1744-mc1.20.1", false);
            }
            catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
            // We assume that should there be another release for 1.20.1 Create Fabric, that our PR is merged.
            return createFabricVersion.compareTo(create6081version) <= 0;
        }

        // No one to conflict with :)
        // No one else seems to fix bugs on Fabric :(
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
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    private boolean matches(String mixinToMatch, String mixinClassName) {
        return mixinClassName.equals("ch.voidlee.repair.mixin." + mixinToMatch);
    }
}
