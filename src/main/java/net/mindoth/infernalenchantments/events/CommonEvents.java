package net.mindoth.infernalenchantments.events;

import net.mindoth.infernalenchantments.InfernalEnchantments;
import net.mindoth.infernalenchantments.enchantment.MagicMunitions;
import net.mindoth.infernalenchantments.registries.RegistryEnchantments;
import net.mindoth.infernalenchantments.enchantment.ThunderForged;
import net.mindoth.infernalenchantments.enchantment.TntMunitions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = InfernalEnchantments.MOD_ID)
public class CommonEvents {

    //MagicMunitions
    @SubscribeEvent
    public static void onMagicMunitions(final LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity pAttacker = source.getTrueSource();
        Entity pArrow = source.getImmediateSource();
        if (pArrow instanceof AbstractArrowEntity && !(pArrow instanceof TridentEntity)) {
            if (pAttacker != null && !pAttacker.world.isRemote) {
                if (!(pAttacker instanceof PlayerEntity)) {
                    Iterable<ItemStack> heldEquipment = pAttacker.getHeldEquipment();
                    for (ItemStack equipped : heldEquipment) {
                        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(equipped);
                        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                            if (enchantment.getKey() instanceof MagicMunitions) {
                                int j = EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.MAGIC_MUNITIONS.get(), equipped);
                                event.setAmount(event.getAmount() * 1.25F * (j + 1) /*+ (float)j * 0.5F + 0.5F*/);
                                source.setDamageBypassesArmor().setMagicDamage();
                            }
                        }
                    }
                }
                else {
                    if (EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.MAGIC_MUNITIONS.get(), ((PlayerEntity) pAttacker).getHeldItem(((PlayerEntity) pAttacker).getActiveHand())) > 0) {
                        event.setAmount(event.getAmount() + EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.MAGIC_MUNITIONS.get(), ((PlayerEntity) pAttacker).getHeldItem(((PlayerEntity) pAttacker).getActiveHand())));
                        source.setDamageBypassesArmor().setMagicDamage();
                    }
                }
            }
        }
    }

    //ThunderForged
    @SubscribeEvent
    public static void onThunderForged(final ProjectileImpactEvent.Arrow event) {
        if (event.getEntity() instanceof AbstractArrowEntity && ((AbstractArrowEntity) event.getEntity()).getShooter() != null && !(event.getEntity() instanceof TridentEntity)) {
            if (event.getRayTraceResult() instanceof EntityRayTraceResult && ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() != null && !(event.getEntity() instanceof TridentEntity) ) {
                Entity pAttacker = ((AbstractArrowEntity) event.getEntity()).getShooter();
                Entity pTarget = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
                if (!pAttacker.world.isRemote) {
                    if ( !(pAttacker instanceof PlayerEntity) ) {
                        Iterable<ItemStack> heldEquipment = pAttacker.getHeldEquipment();
                        for (ItemStack equipped : heldEquipment) {
                            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(equipped);
                            for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                                if (enchantment.getKey() instanceof ThunderForged) {
                                    EntityType.LIGHTNING_BOLT.spawn((ServerWorld) pTarget.world, null, null, pTarget.getPosition(), SpawnReason.TRIGGERED, true, true);
                                }
                            }
                        }
                    }
                    else {
                        if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.THUNDER_FORGED.get(), ((PlayerEntity) pAttacker).getHeldItem(((PlayerEntity) pAttacker).getActiveHand())) > 0 ) {
                            EntityType.LIGHTNING_BOLT.spawn((ServerWorld) pTarget.world, null, null, pTarget.getPosition(), SpawnReason.TRIGGERED, true, true);
                        }
                    }
                }
            }
        }
    }

    //TNTMunitions
    @SubscribeEvent
    public static void onTNTMunitions(final ProjectileImpactEvent.Arrow event) {
        if ( event.getEntity() instanceof AbstractArrowEntity && ((AbstractArrowEntity)event.getEntity()).getShooter() != null ) {
            if ( event.getRayTraceResult() instanceof EntityRayTraceResult && ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() != null && !(event.getEntity() instanceof TridentEntity) ) {
                Entity pAttacker = ((AbstractArrowEntity) event.getEntity()).getShooter();
                Entity pTarget = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
                    if ( !pAttacker.world.isRemote ) {
                        if (!(pAttacker instanceof PlayerEntity)) {
                            Iterable<ItemStack> heldEquipment = pAttacker.getHeldEquipment();
                            for (ItemStack equipped : heldEquipment) {
                                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(equipped);
                                for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                                    if (enchantment.getKey() instanceof TntMunitions) {
                                        pTarget.world.createExplosion(pTarget, pTarget.getPosition().getX(), pTarget.getPosition().getY(), pTarget.getPosition().getZ(), 5, false, Explosion.Mode.NONE);
                                    }
                                }
                            }
                        }
                        else {
                            if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.TNT_MUNITIONS.get(), ((PlayerEntity) pAttacker).getHeldItem(((PlayerEntity) pAttacker).getActiveHand())) > 0 ) {
                                pTarget.world.createExplosion(pTarget, pTarget.getPosition().getX(), pTarget.getPosition().getY(), pTarget.getPosition().getZ(), 5, false, Explosion.Mode.NONE);
                            }
                        }
                    }
                }
            }
        }

    //Ripandrepair
    @SubscribeEvent
    public static void onRipAndRepair(final LivingDamageEvent event) {
        if ( event.getSource().getTrueSource() instanceof LivingEntity ) {
            LivingEntity pAttacker = (LivingEntity)event.getSource().getTrueSource();
            if ( !pAttacker.world.isRemote ) {
                if ( event.getAmount() >= 10 ) {
                    //Mainhand
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND)));
                            }
                        }
                    }
                    //Offhand
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND)));
                            }
                        }
                    }
                    //Helmet
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.HEAD)));
                            }
                        }
                    }
                    //Chestplate
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.CHEST)));
                            }
                        }
                    }
                    //Leggings
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.LEGS)));
                            }
                        }
                    }
                    //Boots
                    if ( EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET)) > 0 ) {
                        double rand = Math.random();
                        if ( pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET).getDamage() > 0 ) {
                            if ( rand > (1.0f - ((float)EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET)) * 0.15f) ) ) {
                                pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET).setDamage(pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET).getDamage() - EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.RIP_AND_REPAIR.get(), pAttacker.getItemStackFromSlot(EquipmentSlotType.FEET)));
                            }
                        }
                    }
                }
            }
        }
    }
}
