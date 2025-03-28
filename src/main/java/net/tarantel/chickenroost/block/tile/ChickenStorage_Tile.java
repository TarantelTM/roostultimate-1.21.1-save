package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.SoulExtractor_Handler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.RoostUltimateItem;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.chickenroost.util.RoostItemContainerContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ChickenStorage_Tile extends BlockEntity {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2147483) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                default -> (stack.getItem() instanceof RoostUltimateItem);
            };
        }
    };
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);

        Integer integer = 0;
        ItemStack itemStack = new ItemStack(ModBlocks.CHICKENSTORAGE.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                integer += itemHandler.getStackInSlot(i).getCount();
            }
        }
        itemStack.set(ModDataComponents.CONTAINER.value(), RoostItemContainerContents.fromItems(inventory.getItems()));
        itemStack.set(ModDataComponents.STORAGEAMOUNT.value(), integer);
        block.setItem(0, itemStack.copy());


        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }
    public ChickenStorage_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHICKENSTORAGE.get(), pos, state);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
    }
    //private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler);
    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandler;
    }
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
    }


    public static void tick(Level level, BlockPos pos, BlockState state, ChickenStorage_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        setChanged(level, pos, state);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return new CompoundTag();
    }
}