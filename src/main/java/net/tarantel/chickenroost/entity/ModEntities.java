
package net.tarantel.chickenroost.entity;

import de.cech12.bucketlib.api.BucketLibComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.chickens.*;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE,
			ChickenRoostMod.MODID);


	/*public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> entity,
																						   float width, float height, int primaryEggColor, int secondaryEggColor) {
		DeferredHolder<EntityType<?>, EntityType<T>> entityType = ENTITIES.register(name,
				() -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

		return entityType;
	}
	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> entity,
																						   float width, float height, int primaryEggColor, int secondaryEggColor) {
		DeferredHolder<EntityType<?>, EntityType<T>> entityType = ENTITIES.register(name,
				() -> EntityType.Builder.of(entity, MobCategory.MONSTER).sized(width, height).build(name));

		return entityType;
	}*/


	public static final DeferredHolder<EntityType<?>, EntityType<AChickenLavaEntity>> A_CHICKEN_LAVA = registerMonster("c_lava", AChickenLavaEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);

	public static final DeferredHolder<EntityType<?>, EntityType<AChickenWaterEntity>> A_CHICKEN_WATER = registerMob("c_water", AChickenWaterEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMonster(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMobFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMonsterFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	public static void readthis() {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				String mobormonster = etherItem.getMobOrMonster();
				Boolean IS_FIRE = etherItem.getIsFire();
				extrachickens(id, mobormonster, IS_FIRE);
			}
		}
	}

	private static DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> extrachickens(String idd, String mobormonster, Boolean IS_FIRE) {
		if(mobormonster.equals("Mob")){
			if(IS_FIRE){
				return registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}
			else {
				return registerMobFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}

		}
		else {
			if(IS_FIRE){
				return registerMonster(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}
			else {
				return registerMonsterFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}

		}
		//return registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
	}



	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BaseChickenEntity.init();
		});
	}
	public static void initChickenConfig() {

		ItemStack lavaegg = new ItemStack(ModItems.LAVA_EGG.get());
		ItemStack wateregg = new ItemStack(ModItems.WATER_EGG.get());
		CompoundTag fluidTagLava = new CompoundTag();
		CompoundTag fluidTagWater = new CompoundTag();
		fluidTagLava.putString("FluidName", "minecraft:lava");
		fluidTagLava.putInt("Amount", 1000);
		fluidTagWater.putString("FluidName", "minecraft:water");
		fluidTagWater.putInt("Amount", 1000);

// Create a new CompoundTag for the root NBT data
		CompoundTag nbtDataLava = new CompoundTag();
		CompoundTag nbtDataWater = new CompoundTag();
		nbtDataLava.put("Fluid", fluidTagLava);
		nbtDataWater.put("Fluid", fluidTagWater);

// Set the NBT data to the ItemStack
		//lavaegg.setTag(nbtDataLava);
		//wateregg.setTag(nbtDataWater);
		wateregg.getOrDefault(BucketLibComponents.BUCKET_CONTENT, CustomData.of(nbtDataWater));
		lavaegg.getOrDefault(BucketLibComponents.BUCKET_CONTENT, CustomData.of(nbtDataLava));

		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String dropitem = etherItem.getDropitem();
				int eggtime = etherItem.getEggtime();

				boolean IS_FIRE = etherItem.getIsFire();
				boolean IS_PROJECTILE = etherItem.getIsProjectile();
				boolean IS_EXPLOSION = etherItem.getIsExplosion();
				boolean IS_FALL = etherItem.getIsFall();
				boolean IS_DROWNING = etherItem.getIsDrowning();
				boolean IS_FREEZING = etherItem.getIsFreezing();
				boolean IS_LIGHTNING = etherItem.getIsLightning();
				boolean IS_WITHER = etherItem.getIsWither();
				ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
				EntityType<?> entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				ChickenConfig.setDropStack(entityType, new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(dropitem))));
				ChickenConfig.setEggTime(entityType, eggtime);

				ChickenConfig.setIsFire(entityType, IS_FIRE);
				ChickenConfig.setIsProjectile(entityType, IS_PROJECTILE);
				ChickenConfig.setIsExplosion(entityType, IS_EXPLOSION);
				ChickenConfig.setIsFall(entityType, IS_FALL);
				ChickenConfig.setIsDrowning(entityType, IS_DROWNING);
				ChickenConfig.setIsFreezing(entityType, IS_FREEZING);
				ChickenConfig.setIsLightning(entityType, IS_LIGHTNING);
				ChickenConfig.setIsWither(entityType, IS_WITHER);
			}
		}
		ChickenConfig.setDropStack(A_CHICKEN_LAVA.get(), lavaegg);
		ChickenConfig.setEggTime(A_CHICKEN_LAVA.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WATER.get(), wateregg);
		ChickenConfig.setEggTime(A_CHICKEN_WATER.get(), 600);
	}
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
				EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				event.put(entityType, BaseChickenEntity.createAttributes().build());
			}
		}
		event.put(A_CHICKEN_LAVA.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WATER.get(), BaseChickenEntity.createAttributes().build());
	}

	public static void register(IEventBus eventBus) {
		readthis();
		REGISTRY.register(eventBus);
	}
}
