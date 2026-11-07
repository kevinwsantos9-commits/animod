package com.nivek.animated;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class NivekAnimated implements ModInitializer {
    public static final String MOD_ID = "nivek_animated";

    public static Item LEGENDARY_SWORD;

    @Override
    public void onInitialize() {
        LEGENDARY_SWORD = Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "legendary_sword"),
                new LegendarySwordItem(new Item.Settings()
                        .rarity(Rarity.EPIC)
                        .fireproof()
                        .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.2f))));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(LEGENDARY_SWORD));
    }
}
