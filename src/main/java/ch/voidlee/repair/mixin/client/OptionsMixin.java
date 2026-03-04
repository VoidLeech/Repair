/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Copied from net/fabricmc/fabric/mixin/resource/loader/client/GameOptionsMixin.java (1.20.1)
 * File is changed beyond repackaging/mixin-prefixing/mapping-related changes; adapted for Forge.
 */

package ch.voidlee.repair.mixin.client;

import ch.voidlee.repair.Repair;
import ch.voidlee.repair.data.BuiltInResourcePackSource;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.fml.loading.FMLLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mixin(Options.class)
public abstract class OptionsMixin {

    @Shadow
    public List<String> resourcePacks;

    @Inject(method = "loadSelectedResourcePacks", at = @At("HEAD"))
    private void create_repair$turnOnDefaultResourcePacks(PackRepository resourcePackRepository, CallbackInfo ci){
        File dataDir = FMLLoader.getGamePath().resolve("data").toFile();

        if (!dataDir.exists() && !dataDir.mkdirs()) {
            Repair.LOGGER.warn("Could not create data directory" + dataDir.getAbsolutePath());
        }

        File trackerFile = new File(dataDir, "create_repairDefaultResourcePacks.dat");
        Set<String> trackedPacks = new HashSet<>();

        if (trackerFile.exists()) {
            try {
                CompoundTag data = NbtIo.readCompressed(trackerFile);
                ListTag values = data.getList("values", Tag.TAG_STRING);

                for (int i = 0; i < values.size(); i++) {
                    trackedPacks.add(values.getString(i));
                }
            } catch (IOException e) {
                Repair.LOGGER.warn("Could not read " + trackerFile.getAbsolutePath(), e);
            }
        }

        Set<String> resourcePacks = new LinkedHashSet<>(this.resourcePacks);

        for (Pack pack : resourcePackRepository.getAvailablePacks()){
            if (pack.getPackSource() instanceof BuiltInResourcePackSource packSource) {
                if (trackedPacks.add(pack.getId()) && packSource.shouldAddAutomatically()) {
                    resourcePacks.add(pack.getId());
                }
            }
        }

        try {
            ListTag values = new ListTag();

            for (String id : trackedPacks) {
                values.add(StringTag.valueOf(id));
            }

            CompoundTag nbt = new CompoundTag();
            nbt.put("values", values);
            NbtIo.writeCompressed(nbt, trackerFile);
        } catch (IOException e) {
            Repair.LOGGER.warn("Could not write to " + trackerFile.getAbsolutePath(), e);
        }

        this.resourcePacks = new ArrayList<>(resourcePacks);
    }
}