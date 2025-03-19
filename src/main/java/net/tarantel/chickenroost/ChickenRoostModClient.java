package net.tarantel.chickenroost;


import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.render.*;
import net.tarantel.chickenroost.handler.Guide_Handler;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.util.KeyBinding;

@Mod(value = "chicken_roost", dist = Dist.CLIENT)
public class ChickenRoostModClient {
    public ChickenRoostModClient(IEventBus modBus, ModContainer container) {
        // Perform logic in that should only be executed on the physical client
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @EventBusSubscriber(modid = "chicken_roost", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            RenderType rendertype3 = RenderType.translucent();
            event.registerBlockEntityRenderer(ModBlockEntities.BREEDER.get(),
                    BreederChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ROOST.get(),
                    RoostChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_EXTRACTOR.get(),
                    ExtractorChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_BREEDER.get(),
                    AnimatedSoulBreederRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(),
                    AnimatedTrainerRenderer::new);

        }

        @SubscribeEvent
        public static void onClientSetup(RegisterMenuScreensEvent event) {
            event.register(ModHandlers.SOUL_BREEDER_MENU.get(), Soul_Breeder_Screen::new);
            event.register(ModHandlers.BREEDER_MENU.get(), Breeder_Screen::new);
            event.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), Soul_Extractor_Screen::new);
            event.register(ModHandlers.ROOST_MENU_V1.get(), Roost_Screen::new);
            event.register(ModHandlers.TRAINER.get(), Trainer_Screen::new);
            //event.register(ModHandlers.GUIDE.get(), Guide_Screen::new);
        }


    }
    @EventBusSubscriber(modid = ChickenRoostMod.MODID, value = Dist.CLIENT)
    public static class ClientNeoEvents{
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.GUIDE_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new Guide_Screen());
            }
        }
    }
    @EventBusSubscriber(modid = ChickenRoostMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.GUIDE_KEY);
        }
    }
}