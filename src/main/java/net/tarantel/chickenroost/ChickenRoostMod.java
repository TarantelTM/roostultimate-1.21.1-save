package net.tarantel.chickenroost;

import com.mojang.logging.LogUtils;
import de.cech12.bucketlib.api.BucketLibApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.util.*;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.util.Random;



@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod {
    //region
    public static final String MODID = "chicken_roost";
    private static final Logger LOGGER = LogUtils.getLogger();
    //endregion
    public ChickenRoostMod(IEventBus bus, ModContainer modContainer) throws FileNotFoundException {
        bus.addListener(this::commonSetup);
        GsonChickenReader.readItemsFromFile();
        ModCreativeModeTabs.register(bus);
        ModDataComponents.register(bus);
        ModItems.register(bus);
        ModEntities.REGISTRY.register(bus);
        ModEntitySpawnMonster.SERIALIZER.register(bus);
        ModEntitySpawnMob.SERIALIZER.register(bus);

        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.register(bus);
        bus.addListener(this::registerCapabilities);

        ModHandlers.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);
        ModRecipes.RECIPE_TYPES.register(bus);
        NeoForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);

    }


    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        ModBlockEntities.registerCapabilities(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {


    }
    @Deprecated
    public static final Random RANDOM = new Random();
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    public void addCreative(BuildCreativeModeTabContentsEvent event)  {
        if(event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()){
            for (int i = 0; i < ModItems.ITEMS.getEntries().size(); i++) {
                event.accept(ModItems.ITEMS.getEntries().stream().toList().get(i).get().asItem());
            }
            event.accept(ModBlocks.BREEDER.get());
            event.accept(ModItems.TRAINER.get());
            event.accept(ModBlocks.SOUL_EXTRACTOR.get());
            event.accept(ModBlocks.ROOST.get());
            event.accept(ModItems.CHICKENSTORAGE.get());
            event.accept(ModItems.SOUL_BREEDER.get());
            event.accept(ModBlocks.SLIMEBLOCK.get());
        }
    }
    public static ResourceLocation ownresource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, path);
    }
    public static ResourceLocation commonsource(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    @EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {

        @SubscribeEvent
        public static void sendImc(RegisterCapabilitiesEvent evt) {
            //BucketLibApi.registerBucket(evt, ModItems.TESTBUCKET.getId());
            BucketLibApi.registerBucket(evt, ModItems.WATER_EGG.getId());
            BucketLibApi.registerBucket(evt, ModItems.LAVA_EGG.getId());
        }
        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            /*event.register(ModEntities.A_CHICKEN_SPRUCEWOOD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_DARK_OAK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENSPIDEREYE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_SAND.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENSWEETBERRIES.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENBEETROOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_QUARTZ.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_STRING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_ENDSTONE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_BIRCHWOOD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_SOUL_SAND.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_SOUL_SOIL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_SUGAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_FLINT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_STONE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENSNOW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENGLOWBERRIES.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_CHORUS_FRUIT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENROTTEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_OAKWOOD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENCARROT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENMELON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_NETHERRACK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_BASALT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKENAPPLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(ModEntities.A_CHICKEN_JUNGLE_WOOD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
*/

        }
    }
}
