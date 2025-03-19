
package net.tarantel.chickenroost.util;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public record ModEntitySpawnMonster(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawn) implements BiomeModifier {

	/**
	 * A static DeferredRegister instance used to register biome modifier serializers
	 * in the mod identified by the MODID of ChickenRoostMod. This register facilitates
	 * the addition of custom biome modifiers to the game's biome modification system.
	 */
	public static DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredRegister.create(
			NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ChickenRoostMod.MODID);

	/**
	 * A supplier that registers and provides a serialization codec for the {@code ModEntitySpawn} class.
	 * The codec is utilized to encode and decode instances of {@code ModEntitySpawn} during data handling
	 * operations, such as saving and loading biome modifier configurations.
	 *
	 * This codec is registered within the {@link ModEntitySpawnMonster#SERIALIZER} field under the key "mobspawns".
	 * It uses {@code RecordCodecBuilder} to define the serialization structure, which includes:
	 * - The list of biomes affected by the entity spawn, serialized using {@link Biome#LIST_CODEC}.
	 * - The spawn data for the associated mob, serialized using {@link MobSpawnSettings.SpawnerData#CODEC}.
	 *
	 * This structure enables the construction of a {@code ModEntitySpawn} record with its associated biome
	 * set and spawn data.
	 */
	static Supplier<MapCodec<ModEntitySpawnMonster>> ROOST_SPAWN_CODEC_MOB = SERIALIZER.register("monsterspawns",
			() -> RecordCodecBuilder.mapCodec(
					builder -> builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawnMonster::biomes),
							MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(
									ModEntitySpawnMonster::spawn)).apply(builder, ModEntitySpawnMonster::new)));

	/**
	 * Modifies the biome settings, specifically adding mob spawn data if conditions are met.
	 *
	 * @param biome  The biome to be modified, represented as a Holder of Biome.
	 * @param phase  The modification phase, determines when the modification is applied.
	 * @param builder The builder object used for modifying the biome's settings, allowing customization of spawns.
	 */
	@Override
	public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
		}
	}

	/**
	 * Returns the codec associated with the {@code ModEntitySpawn} record,
	 * which is used for serializing and deserializing biome modifiers.
	 *
	 * @return A non-null {@code MapCodec} instance for biome modifiers represented by this class.
	 */
	@Override
	public @NotNull MapCodec<? extends BiomeModifier> codec() {
		return ROOST_SPAWN_CODEC_MOB.get();
	}

	


}
