package net.tarantel.chickenroost.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class storageBlockItem extends BlockItem {
    private final Block block;

    public storageBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
        this.block = block;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.nullToEmpty("Very Big Storage"));
        list.add(Component.nullToEmpty("For Chickens and Seeds"));
        list.add(Component.nullToEmpty("Works only with AE2, RS, Simple Storage Network"));
        list.add(Component.nullToEmpty("or other Mods who access Block Inventories via Interfaces"));
        list.add(Component.nullToEmpty("Its harder than Obsidian and got a 10x explosion Resist"));
        list.add(Component.nullToEmpty("No Safety!!!"));
        list.add(Component.nullToEmpty("If u destroy it, all Items inside get deleted!!!"));
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(2147483) {
        protected void onContentsChanged(int slot) {


        }
    };
}
