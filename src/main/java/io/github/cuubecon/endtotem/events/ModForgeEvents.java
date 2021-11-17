package io.github.cuubecon.endtotem.events;

import io.github.cuubecon.endtotem.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEvents
{
    @SubscribeEvent
    public static void dimensionChange(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        BlockPos playerLocation = event.getPlayer().getPosition();
        event.getPlayer().getPersistentData().putIntArray("validPos", new int[]{playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()});

    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event)
    {

        if(event.getSource().canHarmInCreative() && event.getEntity() instanceof PlayerEntity)
        {
            ItemStack itemstack = null;
            PlayerEntity player = (PlayerEntity) event.getEntity();

            for(Hand hand : Hand.values()) {
                ItemStack itemstack1 = player.getHeldItem(hand);
                if (itemstack1.getItem() == ModItems.ENDTOTEM.get()) {
                    itemstack = itemstack1.copy();
                    itemstack1.shrink(1);
                    break;
                }
            }
            if(itemstack != null)
            {
                if(player.getPersistentData().contains("validPos"))
                {
                    int[] ints = player.getPersistentData().getIntArray("validPos");
                    player.fallDistance = 0F;
                    player.setPositionAndUpdate(ints[0], ints[1], ints[2]);
                    event.setCanceled(true);

                    if (event.getEntity() instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)event.getEntity();
                        serverplayerentity.takeStat(Stats.ITEM_USED.get(ModItems.ENDTOTEM.get()));
                        // CriteriaTriggers.USED_TOTEM.trigger(serverplayerentity, itemstack);
                    }

                    player.setHealth(2.0F);
                    player.getActivePotionEffects().clear();
                    player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                    player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 150, 1));
                    player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                    player.world.setEntityState(player, (byte)35);
                }
            }

        }
        else if(event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if(player.getHealth() - event.getAmount() <= 0 && player.world.getDimensionKey().getLocation().equals(DimensionType.THE_END_ID))
            {
                ItemStack itemstack = null;

                for(Hand hand : Hand.values()) {
                    ItemStack itemstack1 = player.getHeldItem(hand);
                    if (itemstack1.getItem() == ModItems.ENDTOTEM.get()) {
                        itemstack = itemstack1.copy();
                        itemstack1.shrink(1);
                        break;
                    }
                }
                if(itemstack != null)
                {
                        player.fallDistance = 0F;
                        event.setCanceled(true);

                        if (event.getEntity() instanceof ServerPlayerEntity) {
                            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)event.getEntity();
                            serverplayerentity.takeStat(Stats.ITEM_USED.get(ModItems.ENDTOTEM.get()));
                            // CriteriaTriggers.USED_TOTEM.trigger(serverplayerentity, itemstack);
                        }

                        player.setHealth(2.0F);
                        player.getActivePotionEffects().clear();
                        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 200, 1));
                    player.addPotionEffect(new EffectInstance(Effects.SATURATION, 50, 1));
                    player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                        player.world.setEntityState(player, (byte)35);

                }
            }
        }
    }
}
