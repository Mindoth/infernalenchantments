package net.mindoth.infernalenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

public class TntMunitions extends Enchantment {
    public TntMunitions(Rarity pRarity, EnchantmentType pCategory, EquipmentSlotType... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
    public boolean canApply(ItemStack pStack) {
        return pStack.getItem() instanceof BowItem || pStack.getItem() instanceof CrossbowItem;
    }
}
