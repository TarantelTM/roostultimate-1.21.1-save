package net.tarantel.chickenroost.util;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModDataComponents {

    /**
     * A {@link DeferredRegister} for registering custom {@link DataComponentType} instances
     * used in the Chicken Roost Mod. This register is linked to the {@code DATA_COMPONENT_TYPE}
     * registry in the Minecraft game and is associated with the ChickenRoostMod's mod ID.
     *
     * It serves as a central registration hub for various data components such as
     * chicken levels, experience, and container contents in the mod. All custom
     * {@link DataComponentType} registrations should utilize this register.
     */
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChickenRoostMod.MODID);

    /**
     * Registers the components with the provided event bus.
     *
     * @param bus the event bus to register the components to
     */
    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
    }

    /**
     * Represents a data component type for tracking chicken levels within the mod.
     * CHICKENLEVEL is a deferred holder that registers and holds a persistent,
     * network-synchronized integer data component type. The allowed range for this
     * component is between 0 and 10000, inclusive.
     *
     * This component is used to store the level of a chicken in gameplay, where the
     * level can define attributes such as performance, capacities, or other gameplay
     * mechanics tied to chicken leveling within the mod system.
     *
     * The data is synchronized between server and client using variable-length
     * integer (VAR_INT) encoding to optimize network performance.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENLEVEL = COMPONENTS.register("chickenlevel",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 10000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    /**
     * Represents the "chickenxp" data component type used for storing experience points associated
     * with chickens. This component is persistent and synchronized over the network.
     *
     * - The value is an integer within the range [0, 100,000,000].
     * - Persistent storage ensures the data is saved and loaded properly.
     * - Network synchronization enables consistent data sharing across connected clients.
     *
     * This component is registered in the {@code ModDataComponents.COMPONENTS} registry.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENXP = COMPONENTS.register("chickenxp",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORAGEAMOUNT = COMPONENTS.register("storageamount",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );
    /**
     * A constant representing the data component type for a "container".
     * This is registered as a deferred holder using the COMPONENTS registry.
     * It is built using a persistent codec, a network-synchronized stream codec,
     * and includes cache encoding for optimized operations.
     * The underlying data type is {@code RoostItemContainerContents}.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RoostItemContainerContents>> CONTAINER = COMPONENTS.register(
            "container",() -> DataComponentType.<RoostItemContainerContents>builder().persistent(RoostItemContainerContents.CODEC).networkSynchronized(RoostItemContainerContents.STREAM_CODEC).cacheEncoding().build()
    );
}
