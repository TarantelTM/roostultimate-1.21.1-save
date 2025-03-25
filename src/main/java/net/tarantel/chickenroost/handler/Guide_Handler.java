package net.tarantel.chickenroost.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Guide_Handler extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level level;
    public final Player entity;
    public int x, y, z;
    public IItemHandler internal;
    public final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    public final Container bookContainer;

    public Guide_Handler(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModHandlers.GUIDE.get(), id);
        this.bookContainer = inv;
        this.entity = inv.player;
        this.level = inv.player.level();
        this.internal = new ItemStackHandler(12);
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}
