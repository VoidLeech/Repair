package ch.voidlee.repair.data;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;

public class BuiltInResourcePackSource implements PackSource {
    private final boolean enabledByDefault;

    public BuiltInResourcePackSource(boolean enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
    }

    @Override
    public Component decorate(Component name) {
        return Component.translatable("pack.nameAndSource", name, Component.translatable("pack.source.builtin")).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public boolean shouldAddAutomatically() {
        return enabledByDefault;
    }
}