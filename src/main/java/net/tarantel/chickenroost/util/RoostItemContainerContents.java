package net.tarantel.chickenroost.util;

import com.google.common.collect.Iterables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public final class RoostItemContainerContents {
    private static final int NO_SLOT = -1;
    private static final int MAX_SIZE = 2147483;
    public static final RoostItemContainerContents EMPTY = new RoostItemContainerContents(NonNullList.create());
    public static final Codec<RoostItemContainerContents> CODEC = RoostItemContainerContents.Slot.CODEC
            .sizeLimitedListOf(2147483)
            .xmap(RoostItemContainerContents::fromSlots, RoostItemContainerContents::asSlots);
    public static final StreamCodec<RegistryFriendlyByteBuf, RoostItemContainerContents> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list(2147483))
            .map(RoostItemContainerContents::new, p_331691_ -> p_331691_.items);
    private final NonNullList<ItemStack> items;
    private final int hashCode;

    private RoostItemContainerContents(NonNullList<ItemStack> items) {
        if (items.size() > 2147483) {
            throw new IllegalArgumentException("Got " + items.size() + " items, but maximum is 256");
        } else {
            this.items = items;
            this.hashCode = ItemStack.hashStackList(items);
        }
    }

    private RoostItemContainerContents(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    private RoostItemContainerContents(List<ItemStack> items) {
        this(items.size());

        for (int i = 0; i < items.size(); i++) {
            this.items.set(i, items.get(i));
        }
    }

    private static RoostItemContainerContents fromSlots(List<RoostItemContainerContents.Slot> slots) {
        OptionalInt optionalint = slots.stream().mapToInt(RoostItemContainerContents.Slot::index).max();
        if (optionalint.isEmpty()) {
            return EMPTY;
        } else {
            RoostItemContainerContents itemcontainercontents = new RoostItemContainerContents(optionalint.getAsInt() + 1);

            for (RoostItemContainerContents.Slot itemcontainercontents$slot : slots) {
                itemcontainercontents.items.set(itemcontainercontents$slot.index(), itemcontainercontents$slot.item());
            }

            return itemcontainercontents;
        }
    }

    public static RoostItemContainerContents fromItems(List<ItemStack> items) {
        int i = findLastNonEmptySlot(items);
        if (i == -1) {
            return EMPTY;
        } else {
            RoostItemContainerContents itemcontainercontents = new RoostItemContainerContents(i + 1);

            for (int j = 0; j <= i; j++) {
                itemcontainercontents.items.set(j, items.get(j).copy());
            }

            return itemcontainercontents;
        }
    }

    private static int findLastNonEmptySlot(List<ItemStack> items) {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    private List<RoostItemContainerContents.Slot> asSlots() {
        List<RoostItemContainerContents.Slot> list = new ArrayList<>();

        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty()) {
                list.add(new RoostItemContainerContents.Slot(i, itemstack));
            }
        }

        return list;
    }

    public void copyInto(NonNullList<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            ItemStack itemstack = i < this.items.size() ? this.items.get(i) : ItemStack.EMPTY;
            list.set(i, itemstack.copy());
        }
    }

    public ItemStack copyOne() {
        return this.items.isEmpty() ? ItemStack.EMPTY : this.items.get(0).copy();
    }

    public Stream<ItemStack> stream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Stream<ItemStack> nonEmptyStream() {
        return this.items.stream().filter(p_331322_ -> !p_331322_.isEmpty()).map(ItemStack::copy);
    }

    public Iterable<ItemStack> nonEmptyItems() {
        return Iterables.filter(this.items, p_331420_ -> !p_331420_.isEmpty());
    }

    public Iterable<ItemStack> nonEmptyItemsCopy() {
        return Iterables.transform(this.nonEmptyItems(), ItemStack::copy);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (other instanceof RoostItemContainerContents itemcontainercontents && ItemStack.listMatches(this.items, itemcontainercontents.items)) {
                return true;
            }

            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    /**
     * Neo:
     * {@return the number of slots in this container}
     */
    public int getSlots() {
        return this.items.size();
    }

    /**
     * Neo: Gets a copy of the stack at a particular slot.
     *
     * @param slot The slot to check. Must be within [0, {@link #getSlots()}]
     * @return A copy of the stack in that slot
     * @throws UnsupportedOperationException if the provided slot index is out-of-bounds.
     */
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return this.items.get(slot).copy();
    }

    /**
     * Neo: Throws {@link UnsupportedOperationException} if the provided slot index is invalid.
     */
    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
        }
    }

    static record Slot(int index, ItemStack item) {
        public static final Codec<RoostItemContainerContents.Slot> CODEC = RecordCodecBuilder.create(
                p_331695_ -> p_331695_.group(
                                Codec.intRange(0, 2147483).fieldOf("slot").forGetter(RoostItemContainerContents.Slot::index),
                                ItemStack.CODEC.fieldOf("item").forGetter(RoostItemContainerContents.Slot::item)
                        )
                        .apply(p_331695_, RoostItemContainerContents.Slot::new)
        );
    }
}
