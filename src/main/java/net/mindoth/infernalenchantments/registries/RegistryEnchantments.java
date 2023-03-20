package net.mindoth.infernalenchantments.registries;

import net.mindoth.infernalenchantments.InfernalEnchantments;
import net.mindoth.infernalenchantments.enchantment.MagicMunitions;
import net.mindoth.infernalenchantments.enchantment.RipAndRepair;
import net.mindoth.infernalenchantments.enchantment.ThunderForged;
import net.mindoth.infernalenchantments.enchantment.TntMunitions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, InfernalEnchantments.MOD_ID);

    public static RegistryObject<Enchantment> MAGIC_MUNITIONS =
            ENCHANTMENTS.register("magic_munitions",
                    () -> new MagicMunitions(Enchantment.Rarity.UNCOMMON,
                            EnchantmentType.BOW, EquipmentSlotType.MAINHAND));

    public static RegistryObject<Enchantment> THUNDER_FORGED =
            ENCHANTMENTS.register("thunder_forged",
                    () -> new ThunderForged(Enchantment.Rarity.VERY_RARE,
                            EnchantmentType.BOW, EquipmentSlotType.MAINHAND));

    public static RegistryObject<Enchantment> TNT_MUNITIONS =
            ENCHANTMENTS.register("tnt_munitions",
                    () -> new TntMunitions(Enchantment.Rarity.VERY_RARE,
                            EnchantmentType.BOW, EquipmentSlotType.MAINHAND));

    public static RegistryObject<Enchantment> RIP_AND_REPAIR =
            ENCHANTMENTS.register("rip_and_repair",
                    () -> new RipAndRepair(Enchantment.Rarity.VERY_RARE,
                            EnchantmentType.VANISHABLE, EquipmentSlotType.MAINHAND));

    public static void register(IEventBus modEventBus) {
        ENCHANTMENTS.register(modEventBus);
    }
}
