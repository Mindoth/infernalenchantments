package net.mindoth.infernalenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class RipAndRepair extends Enchantment {
    public RipAndRepair(Rarity pRarity, EnchantmentType pCategory, EquipmentSlotType... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canGenerateInLoot() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return true;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return !(ench instanceof MendingEnchantment) && super.canApplyTogether(ench);
    }
}
