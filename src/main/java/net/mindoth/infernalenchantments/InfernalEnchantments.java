package net.mindoth.infernalenchantments;

import net.mindoth.infernalenchantments.registries.RegistryEnchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(InfernalEnchantments.MOD_ID)
public class InfernalEnchantments {
    public static final String MOD_ID = "infernalenchantments";

    public InfernalEnchantments() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        addRegistries(modEventBus);
    }

    private void addRegistries(final IEventBus modEventBus) {
        RegistryEnchantments.register(modEventBus);
    }
}
