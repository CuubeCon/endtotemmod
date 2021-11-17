package io.github.cuubecon.endtotem.item;

import io.github.cuubecon.endtotem.EndTotemMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EndTotemMod.MOD_ID);

    public static final RegistryObject<Item> ENDTOTEM = ITEMS.register("endtotem",
            () -> new Item(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
