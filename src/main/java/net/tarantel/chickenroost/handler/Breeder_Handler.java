package net.tarantel.chickenroost.handler;


import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.minecraft.tags.ItemTags;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Breeder_Handler extends AbstractContainerMenu {

    public final Breeder_Tile blockEntity;
    public final Level level;
    private final ContainerData data;
    public int x, y, z;

    public Breeder_Handler(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(10));
    }

    public Breeder_Handler(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModHandlers.BREEDER_MENU.get(), id);
        checkContainerSize(inv, 11);
        blockEntity = (Breeder_Tile) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);



        ItemCapabilityMenuHelper.getCapabilityItemHandler(this.level, this.blockEntity).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 1, 21, 45){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return (stack.is(ItemTags.create(ChickenRoostMod.commonsource("seeds/tiered"))));
                }
            });

            this.addSlot(new SlotItemHandler(itemHandler, 0, 7, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return (stack.getItem() instanceof ChickenItemBase);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 2, 34, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return (stack.getItem() instanceof ChickenItemBase);
                }
            });

            this.addSlot(new SlotItemHandler(itemHandler, 3, 97, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 4, 115, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 5, 133, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 6, 151, 26){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 7, 97, 44){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 8, 115, 44){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 9, 133, 44){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 10, 151, 44){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });


        });
        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 40; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 11;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.BREEDER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}