package at.martinthedragon.ntm.items;

import at.martinthedragon.ntm.items.AdvancedItems.ItemCustomized;
import at.martinthedragon.ntm.lib.RefStrings;
import at.martinthedragon.ntm.main.CreativeTabs;
import at.martinthedragon.ntm.main.Main;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

// Use this class if you want to access items.
// Write item variables only!
// The order of items in the inventory depends on the order of the variables here.
@SuppressWarnings({"unused"})
@Mod.EventBusSubscriber(modid = RefStrings.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder("ntm")
public class Items {

    // NTM Resources and Parts
    // Ingots

    public static final ItemCustomized URANIUM_INGOT = new ItemCustomized("uranium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U233_INGOT = new ItemCustomized("u233_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U235_INGOT = new ItemCustomized("u235_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U238_INGOT = new ItemCustomized("u238_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized THORIUM_INGOT = new ItemCustomized("thorium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PLUTONIUM_INGOT = new ItemCustomized("plutonium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU238_INGOT = new ItemCustomized("pu238_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU239_INGOT = new ItemCustomized("pu239_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU240_INGOT = new ItemCustomized("pu240_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized NEPTUNIUM_INGOT = new ItemCustomized("neptunium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1).lore(1));
    public static final ItemCustomized TITANIUM_INGOT = new ItemCustomized("titanium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized INDUSTRIAL_COPPER_INGOT = new ItemCustomized("industrial_copper_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MINECRAFT_COPPER_INGOT = new ItemCustomized("minecraft_copper_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_ALLOY_INGOT = new ItemCustomized("advanced_alloy_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TUNGSTEN_INGOT = new ItemCustomized("tungsten_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ALUMINIUM_INGOT = new ItemCustomized("aluminium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STEEL_INGOT = new ItemCustomized("steel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_INGOT = new ItemCustomized("lead_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BERYLLIUM_INGOT = new ItemCustomized("beryllium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_SPEED_STEEL_INGOT = new ItemCustomized("high_speed_steel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POLYMER_INGOT = new ItemCustomized("polymer_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_INGOT = new ItemCustomized("schrabidium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized MAGNETIZED_TUNGSTEN_INGOT = new ItemCustomized("magnetized_tungsten_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COMBINE_STEEL_INGOT = new ItemCustomized("combine_steel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(1));
    public static final ItemCustomized SOLINIUM_INGOT = new ItemCustomized("solinium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized URANIUM_FUEL_INGOT = new ItemCustomized("uranium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized THORIUM_FUEL_INGOT = new ItemCustomized("thorium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PLUTONIUM_FUEL_INGOT = new ItemCustomized("plutonium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized MOX_FUEL_INGOT = new ItemCustomized("mox_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized SCHRABIDIUM_FUEL_INGOT = new ItemCustomized("schrabidium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized HIGH_ENRICHED_SCHRABIDIUM_FUEL_INGOT = new ItemCustomized("high_enriched_schrabidium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized LOW_ENRICHED_SCHRABIDIUM_FUEL_INGOT = new ItemCustomized("low_enriched_schrabidium_fuel_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized AUSTRALIUM_INGOT = new ItemCustomized("australium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized WEIDANIUM_INGOT = new ItemCustomized("weidanium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REIIUM_INGOT = new ItemCustomized("reiium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized UNOBTAINIUM_INGOT = new ItemCustomized("unobtainium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DAFFERGON_INGOT = new ItemCustomized("daffergon_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized VERTICIUM_INGOT = new ItemCustomized("verticium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LANTHANUM_INGOT = new ItemCustomized("lanthanum_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ACTINIUM_INGOT = new ItemCustomized("actinium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized DESH_INGOT = new ItemCustomized("desh_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STARMETAL_INGOT = new ItemCustomized("starmetal_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SATURNITE_INGOT = new ItemCustomized("saturnite_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EUPHEMIUM_INGOT = new ItemCustomized("euphemium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(1));
    public static final ItemCustomized DINEUTRONIUM_INGOT = new ItemCustomized("dineutronium_ingot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Cubes

    public static final ItemCustomized LITHIUM_CUBE = new ItemCustomized("lithium_cube", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SOLID_FUEL_CUBE = new ItemCustomized("solid_fuel_cube", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SOLID_ROCKET_FUEL_CUBE = new ItemCustomized("solid_rocket_fuel_cube", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Fuels

    public static final ItemCustomized COKE = new ItemCustomized("coke", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).setBurnTime(160));
    public static final ItemCustomized LIGNITE = new ItemCustomized("lignite", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).setBurnTime(60));
    public static final ItemCustomized LIGNITE_BRIQUETTE = new ItemCustomized("lignite_briquette", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).setBurnTime(80));

    // Powders

    public static final ItemCustomized SULFUR = new ItemCustomized("sulfur", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NITER = new ItemCustomized("niter", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FLUORITE = new ItemCustomized("fluorite", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COAL_POWDER = new ItemCustomized("coal_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized IRON_POWDER = new ItemCustomized("iron_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLD_POWDER = new ItemCustomized("gold_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LAPIS_LAZULI_POWDER = new ItemCustomized("lapis_lazuli_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized QUARTZ_POWDER = new ItemCustomized("quartz_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DIAMOND_POWDER = new ItemCustomized("diamond_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EMERALD_POWDER = new ItemCustomized("emerald_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized URANIUM_POWDER = new ItemCustomized("uranium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PLUTONIUM_POWDER = new ItemCustomized("plutonium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized NEPTUNIUM_POWDER = new ItemCustomized("neptunium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized TITANIUM_POWDER = new ItemCustomized("titanium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COPPER_POWDER = new ItemCustomized("copper_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RED_COPPER_POWDER = new ItemCustomized("red_copper_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_ALLOY_POWDER = new ItemCustomized("advanced_alloy_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TUNGSTEN_POWDER = new ItemCustomized("tungsten_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ALUMINIUM_POWDER = new ItemCustomized("aluminium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STEEL_POWDER = new ItemCustomized("steel_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_POWDER = new ItemCustomized("lead_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BERYLLIUM_POWDER = new ItemCustomized("beryllium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_SPEED_STEEL_POWDER = new ItemCustomized("high_speed_steel_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POLYMER_POWDER = new ItemCustomized("polymer_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_POWDER = new ItemCustomized("schrabidium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MAGNETIZED_TUNGSTEN_POWDER = new ItemCustomized("magnetized_tungsten_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COMBINE_STEEL_POWDER = new ItemCustomized("combine_steel_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LITHIUM_POWDER = new ItemCustomized("lithium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LANTHANUM_POWDER = new ItemCustomized("lanthanum_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ACTINIUM_POWDER = new ItemCustomized("actinium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized AUSTRALIUM_POWDER = new ItemCustomized("australium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized WEIDANIUM_POWDER = new ItemCustomized("weidanium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REIIUM_POWDER = new ItemCustomized("reiium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized UNOBTAINIUM_POWDER = new ItemCustomized("unobtainium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DAFFERGON_POWDER = new ItemCustomized("daffergon_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized VERTICIUM_POWDER = new ItemCustomized("verticium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EUPHEMIUM_POWDER = new ItemCustomized("euphemium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(2));
    public static final ItemCustomized DINEUTRONIUM_POWDER = new ItemCustomized("dineutronium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NEODYMIUM_POWDER = new ItemCustomized("neodymium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COBALT_POWDER = new ItemCustomized("cobalt_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NIOBIUM_POWDER = new ItemCustomized("niobium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CERIUM_POWDER = new ItemCustomized("cerium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESH_POWDER = new ItemCustomized("desh_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESH_BLEND = new ItemCustomized("desh_blend", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NITANIUM_BLEND = new ItemCustomized("nitanium_blend", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SPARK_BLEND = new ItemCustomized("spark_blend", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LIGNITE_POWDER = new ItemCustomized("lignite_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized METEORITE_POWDER = new ItemCustomized("meteorite_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESATURATED_REDSTONE = new ItemCustomized("desaturated_redstone", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DUST = new ItemCustomized("dust", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(1));
    public static final ItemCustomized YELLOWCAKE = new ItemCustomized("yellowcake", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized ENCHANTMENT_POWDER = new ItemCustomized("enchantment_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CLOUD_RESIDUE = new ItemCustomized("cloud_residue", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FLAME_POWDER = new ItemCustomized("flame_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(2));
    public static final ItemCustomized CRYO_POWDER = new ItemCustomized("cryo_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POISON_POWDER = new ItemCustomized("poison_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(2));
    public static final ItemCustomized THERMITE = new ItemCustomized("thermite", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ENERGY_POWDER = new ItemCustomized("energy_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Tiny Powders

    public static final ItemCustomized TINY_LITHIUM_POWDER = new ItemCustomized("tiny_lithium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_NEODYMIUM_POWDER = new ItemCustomized("tiny_neodymium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_COBALT_POWDER = new ItemCustomized("tiny_cobalt_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_NIOBIUM_POWDER = new ItemCustomized("tiny_niobium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_CERIUM_POWDER = new ItemCustomized("tiny_cerium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_LANTHANUM_POWDER = new ItemCustomized("tiny_lanthanum_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_ACTINIUM_POWDER = new ItemCustomized("tiny_actinium_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized TINY_METEORITE_POWDER = new ItemCustomized("tiny_meteorite_powder", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Fragments

    public static final ItemCustomized NEODYMIUM_FRAGMENT = new ItemCustomized("neodymium_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COBALT_FRAGMENT = new ItemCustomized("cobalt_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NIOBIUM_FRAGMENT = new ItemCustomized("niobium_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CERIUM_FRAGMENT = new ItemCustomized("cerium_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LANTHANUM_FRAGMENT = new ItemCustomized("lanthanum_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ACTINIUM_FRAGMENT = new ItemCustomized("actinium_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized METEORITE_FRAGMENT = new ItemCustomized("meteorite_fragment", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Propellants

    public static final ItemCustomized BALLISTITE_PROPELLANT = new ItemCustomized("ballistite_propellant", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CORDITE_PROPELLANT = new ItemCustomized("cordite_propellant", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Biomass

    public static final ItemCustomized BIOMASS = new ItemCustomized("biomass", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COMPRESSED_BIOMASS = new ItemCustomized("compressed_biomass", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Nuggets

    public static final ItemCustomized URANIUM_NUGGET = new ItemCustomized("uranium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U233_NUGGET = new ItemCustomized("u233_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U235_NUGGET = new ItemCustomized("u235_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized U238_NUGGET = new ItemCustomized("u238_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized THORIUM_NUGGET = new ItemCustomized("thorium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PLUTONIUM_NUGGET = new ItemCustomized("plutonium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU238_NUGGET = new ItemCustomized("pu238_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU239_NUGGET = new ItemCustomized("pu239_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PU240_NUGGET = new ItemCustomized("pu240_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized NEPTUNIUM_NUGGET = new ItemCustomized("neptunium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized LEAD_NUGGET = new ItemCustomized("lead_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BERYLLIUM_NUGGET = new ItemCustomized("beryllium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_NUGGET = new ItemCustomized("schrabidium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized SOLINIUM_NUGGET = new ItemCustomized("solinium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized URANIUM_FUEL_NUGGET = new ItemCustomized("uranium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized THORIUM_FUEL_NUGGET = new ItemCustomized("thorium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized PLUTONIUM_FUEL_NUGGET = new ItemCustomized("plutonium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized MOX_FUEL_NUGGET = new ItemCustomized("mox_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized SCHRABIDIUM_FUEL_NUGGET = new ItemCustomized("schrabidium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized HIGH_ENRICHED_SCHRABIDIUM_FUEL_NUGGET = new ItemCustomized("high_enriched_schrabidium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized LOW_ENRICHED_SCHRABIDIUM_FUEL_NUGGET = new ItemCustomized("low_enriched_schrabidium_fuel_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized AUSTRALIUM_NUGGET = new ItemCustomized("australium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized WEIDANIUM_NUGGET = new ItemCustomized("weidanium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REIIUM_NUGGET = new ItemCustomized("reiium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized UNOBTAINIUM_NUGGET = new ItemCustomized("unobtainium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DAFFERGON_NUGGET = new ItemCustomized("daffergon_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized VERTICIUM_NUGGET = new ItemCustomized("verticium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESH_NUGGET = new ItemCustomized("desh_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EUPHEMIUM_NUGGET = new ItemCustomized("euphemium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).lore(3));
    public static final ItemCustomized DINEUTRONIUM_NUGGET = new ItemCustomized("dineutronium_nugget", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Plates

    public static final ItemCustomized IRON_PLATE = new ItemCustomized("iron_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLD_PLATE = new ItemCustomized("gold_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TITANIUM_PLATE = new ItemCustomized("titanium_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ALUMINIUM_PLATE = new ItemCustomized("aluminium_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STEEL_PLATE = new ItemCustomized("steel_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_PLATE = new ItemCustomized("lead_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COPPER_PLATE = new ItemCustomized("copper_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_ALLOY_PLATE = new ItemCustomized("advanced_alloy_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NEUTRON_REFLECTOR = new ItemCustomized("neutron_reflector", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_PLATE = new ItemCustomized("schrabidium_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized COMBINE_STEEL_PLATE = new ItemCustomized("combine_steel_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MIXED_PLATE = new ItemCustomized("mixed_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SATURNITE_PLATE = new ItemCustomized("saturnite_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized PAA_ALLOY_PLATE = new ItemCustomized("paa_alloy_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized INSULATOR = new ItemCustomized("insulator", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized KEVLAR_CERAMIC_COMPOUND = new ItemCustomized("kevlar_ceramic_compound", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ANGRY_METAL = new ItemCustomized("angry_metal", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESH_COMPOUND_PLATE = new ItemCustomized("desh_compound_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EUPHEMIUM_COMPOUND_PLATE = new ItemCustomized("euphemium_compound_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DINEUTRONIUM_COMPOUND_PLATE = new ItemCustomized("dineutronium_compound_plate", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COPPER_PANEL = new ItemCustomized("copper_panel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Bolts

    public static final ItemCustomized HIGH_SPEED_STEEL_BOLT = new ItemCustomized("high_speed_steel_bolt", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TUNGSTEN_BOLT = new ItemCustomized("tungsten_bolt", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REINFORCED_TURBINE_SHAFT = new ItemCustomized("reinforced_turbine_shaft", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Cloths

    public static final ItemCustomized HAZMAT_CLOTH = new ItemCustomized("hazmat_cloth", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_HAZMAT_CLOTH = new ItemCustomized("advanced_hazmat_cloth", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_REINFORCED_HAZMAT_CLOTH = new ItemCustomized("lead_reinforced_hazmat_cloth", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FIRE_PROXIMITY_CLOTH = new ItemCustomized("fire_proximity_cloth", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ACTIVATED_CARBON_FILTER = new ItemCustomized("activated_carbon_filter", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Wires

    public static final ItemCustomized ALUMINIUM_WIRE = new ItemCustomized("aluminium_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COPPER_WIRE = new ItemCustomized("copper_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TUNGSTEN_WIRE = new ItemCustomized("tungsten_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RED_COPPER_WIRE = new ItemCustomized("red_copper_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SUPER_CONDUCTOR = new ItemCustomized("super_conductor", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLD_WIRE = new ItemCustomized("gold_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_WIRE = new ItemCustomized("schrabidium_wire", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB).radiation(1));
    public static final ItemCustomized HIGH_TEMPERATURE_SUPER_CONDUCTOR = new ItemCustomized("high_temperature_super_conductor", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Coils

    public static final ItemCustomized COPPER_COIL = new ItemCustomized("copper_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RING_COIL = new ItemCustomized("ring_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SUPER_CONDUCTING_COIL = new ItemCustomized("super_conducting_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SUPER_CONDUCTING_RING_COIL = new ItemCustomized("super_conducting_ring_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLD_COIL = new ItemCustomized("gold_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLDEN_RING_COIL = new ItemCustomized("golden_ring_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HEATING_COIL = new ItemCustomized("heating_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_TEMPERATURE_SUPER_CONDUCTING_COIL = new ItemCustomized("high_temperature_super_conducting_coil", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Some random stuff

    public static final ItemCustomized STEEL_TANK = new ItemCustomized("steel_tank", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MOTOR = new ItemCustomized("motor", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CENTRIFUGE_ELEMENT = new ItemCustomized("centrifuge_element", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CENTRIFUGE_TOWER = new ItemCustomized("centrifuge_tower", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEE_MAGNETS = new ItemCustomized("dee_magnets", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FLAT_MAGNET = new ItemCustomized("flat_magnet", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CYCLOTRON_TOWER = new ItemCustomized("cyclotron_tower", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BREEDING_REACTOR_CORE = new ItemCustomized("breeding_reactor_core", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RTG_UNIT = new ItemCustomized("rtg_unit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Distribution Units

    public static final ItemCustomized THERMAL_DISTRIBUTION_UNIT = new ItemCustomized("thermal_distribution_unit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized THERMAL_SUCKING_UNIT = new ItemCustomized("thermal_sucking_unit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HEAT_DISTRIBUTION_UNIT = new ItemCustomized("heat_distribution_unit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GRAVITY_MANIPULATOR = new ItemCustomized("gravity_manipulator", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Some more random stuff

    public static final ItemCustomized STEEL_PIPES = new ItemCustomized("steel_pipes", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TITANIUM_DRILL = new ItemCustomized("titanium_drill", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized PHOTOVOLTAIC_PANEL = new ItemCustomized("photovoltaic_panel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CHLORINE_PINWHEEL = new ItemCustomized("chlorine_pinwheel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TELEPAD = new ItemCustomized("telepad", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ENTANGLEMENT_KIT = new ItemCustomized("entanglement_kit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STABILIZER_COMPONENT = new ItemCustomized("stabilizer_component", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EMITTER_COMPONENT = new ItemCustomized("emitter_component", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ALUMINIUM_CAP = new ItemCustomized("aluminium_cap", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_STEEL_SHELL = new ItemCustomized("small_steel_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_ALUMINIUM_SHELL = new ItemCustomized("small_aluminium_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BIG_STEEL_SHELL = new ItemCustomized("big_steel_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BIG_ALUMINIUM_SHELL = new ItemCustomized("big_aluminium_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BIG_TITANIUM_SHELL = new ItemCustomized("big_titanium_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FLAT_STEEL_CASING = new ItemCustomized("flat_steel_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_STEEL_GRID_FINS = new ItemCustomized("small_steel_grid_fins", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BIG_STEEL_GRID_FINS = new ItemCustomized("big_steel_grid_fins", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_STEEL_FINS = new ItemCustomized("large_steel_fins", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_TITANIUM_FINS = new ItemCustomized("small_titanium_fins", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STEEL_SPHERE = new ItemCustomized("steel_sphere", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized STEEL_PEDESTAL = new ItemCustomized("steel_pedestal", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DYSFUNCTIONAL_NUCLEAR_REACTOR = new ItemCustomized("dysfunctional_nuclear_reactor", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_STEEL_ROTOR = new ItemCustomized("large_steel_rotor", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GENERATOR_BODY = new ItemCustomized("generator_body", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TITANIUM_BLADE = new ItemCustomized("titanium_blade", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TUNGSTEN_REINFORCED_BLADE = new ItemCustomized("tungsten_reinforced_blade", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TITANIUM_STEAM_TURBINE = new ItemCustomized("titanium_steam_turbine", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REINFORCED_TURBOFAN_BLADES = new ItemCustomized("reinforced_turbofan_blades", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GENERATOR_FRONT = new ItemCustomized("generator_front", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TOOTHPICKS = new ItemCustomized("toothpicks", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DUCT_TAPE = new ItemCustomized("duct_tape", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CLAY_CATALYST = new ItemCustomized("clay_catalyst", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_MISSILE_ASSEMBLY = new ItemCustomized("small_missile_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Warheads

    public static final ItemCustomized SMALL_WARHEAD= new ItemCustomized("small_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_WARHEAD = new ItemCustomized("medium_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_WARHEAD = new ItemCustomized("large_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_INCENDIARY_WARHEAD = new ItemCustomized("small_incendiary_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_INCENDIARY_WARHEAD = new ItemCustomized("medium_incendiary_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_INCENDIARY_WARHEAD = new ItemCustomized("large_incendiary_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_CLUSTER_WARHEAD = new ItemCustomized("small_cluster_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_CLUSTER_WARHEAD = new ItemCustomized("medium_cluster_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_CLUSTER_WARHEAD = new ItemCustomized("large_cluster_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_BUNKER_BUSTER_WARHEAD = new ItemCustomized("small_bunker_busting_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_BUNKER_BUSTER_WARHEAD = new ItemCustomized("medium_bunker_busting_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_BUNKER_BUSTER_WARHEAD = new ItemCustomized("large_bunker_busting_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NUCLEAR_WARHEAD = new ItemCustomized("nuclear_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized THERMONUCLEAR_WARHEAD = new ItemCustomized("thermonuclear_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ENDOTHERMIC_WARHEAD = new ItemCustomized("endothermic_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EXOTHERMIC_WARHEAD = new ItemCustomized("exothermic_warhead", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Fuel Tanks

    public static final ItemCustomized SMALL_FUEL_TANK = new ItemCustomized("small_fuel_tank", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_FUEL_TANK = new ItemCustomized("medium_fuel_tank", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_FUEL_TANK = new ItemCustomized("large_fuel_tank", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Thrusters

    public static final ItemCustomized SMALL_THRUSTER = new ItemCustomized("small_thruster", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MEDIUM_THRUSTER = new ItemCustomized("medium_thruster", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_THRUSTER = new ItemCustomized("large_thruster", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LV_N_NUCLEAR_ROCKET_ENGINE = new ItemCustomized("lv_n_nuclear_rocket_engine", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Satellite stuff

    public static final ItemCustomized SATELLITE_BASE = new ItemCustomized("satellite_base", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_GAIN_OPTICAL_CAMERA = new ItemCustomized("high_gain_optical_camera", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized M700_SURVEY_SCANNER = new ItemCustomized("m700_survey_scanner", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RADAR_DISH = new ItemCustomized("radar_dish", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEATH_RAY = new ItemCustomized("death_ray", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized XENIUM_RESONATOR = new ItemCustomized("xenium_resonator", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Connectors

    public static final ItemCustomized SIZE_10_CONNECTOR = new ItemCustomized("size_10_connector", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SIZE_15_CONNECTOR = new ItemCustomized("size_15_connector", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SIZE_20_CONNECTOR = new ItemCustomized("size_20_connector", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Chopper stuff

    public static final ItemCustomized HUNTER_CHOPPER_COCKPIT = new ItemCustomized("hunter_chopper_cockpit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EMPLACEMENT_GUN = new ItemCustomized("emplacement_gun", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HUNTER_CHOPPER_BODY = new ItemCustomized("hunter_chopper_body", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HUNTER_CHOPPER_TAIL = new ItemCustomized("hunter_chopper_tail", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HUNTER_CHOPPER_WING = new ItemCustomized("hunter_chopper_wing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HUNTER_CHOPPER_ROTOR_BLADES = new ItemCustomized("hunter_chopper_rotor_blades", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COMBINE_STEEL_SCRAP_METAL = new ItemCustomized("combine_steel_scrap_metal", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // And more random stuff

    public static final ItemCustomized HEAVY_HAMMER_HEAD = new ItemCustomized("heavy_hammer_head", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HEAVY_AXE_HEAD = new ItemCustomized("heavy_axe_head", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized REINFORCED_POLYMER_HANDLE = new ItemCustomized("reinforced_polymer_handle", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Circuits

    public static final ItemCustomized BASIC_CIRCUIT_ASSEMBLY = new ItemCustomized("basic_circuit_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BASIC_CIRCUIT = new ItemCustomized("basic_circuit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ENHANCED_CIRCUIT = new ItemCustomized("enhanced_circuit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_CIRCUIT = new ItemCustomized("advanced_circuit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized OVERCLOCKED_CIRCUIT = new ItemCustomized("overclocked_circuit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_PERFORMANCE_CIRCUIT = new ItemCustomized("high_performance_circuit", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Military grade circuit boards

    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_1 = new ItemCustomized("military_grade_circuit_board_tier_1", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_2 = new ItemCustomized("military_grade_circuit_board_tier_2", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_3 = new ItemCustomized("military_grade_circuit_board_tier_3", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_4 = new ItemCustomized("military_grade_circuit_board_tier_4", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_5 = new ItemCustomized("military_grade_circuit_board_tier_5", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MILITARY_GRADE_CIRCUIT_BOARD_TIER_6 = new ItemCustomized("military_grade_circuit_board_tier_6", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Weapon mechanisms

    public static final ItemCustomized REVOLVER_MECHANISM = new ItemCustomized("revolver_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_REVOLVER_MECHANISM = new ItemCustomized("advanced_revolver_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized RIFLE_MECHANISM = new ItemCustomized("rifle_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_RIFLE_MECHANISM = new ItemCustomized("advanced_rifle_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LAUNCHER_MECHANISM = new ItemCustomized("launcher_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADVANCED_LAUNCHER_MECHANISM = new ItemCustomized("advanced_launcher_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized HIGH_TECH_WEAPON_MECHANISM = new ItemCustomized("high_tech_weapon_mechanism", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Bullet Primers

    public static final ItemCustomized POINT_357_MAGNUM_PRIMER = new ItemCustomized("point_357_magnum_primer", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_44_MAGNUM_PRIMER = new ItemCustomized("point_44_magnum_primer", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_CALIBER_PRIMER = new ItemCustomized("small_caliber_primer", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_CALIBER_PRIMER = new ItemCustomized("large_caliber_primer", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BUCKSHOT_PRIMER = new ItemCustomized("buckshot_primer", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Bullet casings

    public static final ItemCustomized POINT_357_MAGNUM_CASING = new ItemCustomized("point_357_magnum_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_44_MAGNUM_CASING = new ItemCustomized("point_44_magnum_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SMALL_CALIBER_CASING = new ItemCustomized("small_caliber_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LARGE_CALIBER_CASING = new ItemCustomized("large_caliber_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BUCKSHOT_CASING = new ItemCustomized("buckshot_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Bullet Assemblies

    public static final ItemCustomized IRON_BULLET_ASSEMBLY = new ItemCustomized("iron_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_BULLET_ASSEMBLY = new ItemCustomized("lead_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GLASS_BULLET_ASSEMBLY = new ItemCustomized("glass_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized GOLD_BULLET_ASSEMBLY = new ItemCustomized("gold_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized SCHRABIDIUM_BULLET_ASSEMBLY = new ItemCustomized("schrabidium_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NIGHTMARE_BULLET_ASSEMBLY = new ItemCustomized("nightmare_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DESH_BULLET_ASSEMBLY = new ItemCustomized("desh_bullet_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_44_MAGNUM_ASSEMBLY = new ItemCustomized("point_44_magnum_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NINE_MM_ASSEMBLY = new ItemCustomized("9_mm_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_22_LR_ASSEMBLY = new ItemCustomized("point_22_lr_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_5_MM_ASSEMBLY = new ItemCustomized("point_5_mm_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_50_AE_ASSEMBLY = new ItemCustomized("point_50_ae_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POINT_50_BMG_ASSEMBLY = new ItemCustomized("point_50_bmg_assembly", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // More bullet casings

    public static final ItemCustomized SILVER_BULLET_CASING = new ItemCustomized("silver_bullet_casing", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TWELVE_POINT_8_CM_STARMETAL_HIGH_ENERGY_SHELL = new ItemCustomized("12_point_8_cm_starmetal_high_energy_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TWELVE_POINT_8_CM_NUCLEAR_SHELL = new ItemCustomized("12_point_8_cm_nuclear_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TWELVE_POINT_8_CM_DU_SHELL = new ItemCustomized("12_point_8_cm_du_shell", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // More random

    public static final ItemCustomized CABLE_DRUM = new ItemCustomized("cable_drum", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized PAINTING_OF_A_CARTOON_PONY = new ItemCustomized("painting_of_a_cartoon_pony", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CONSPIRACY_THEORY = new ItemCustomized("conspiracy_theory", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POLITICAL_TOPIC = new ItemCustomized("political_topic", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized OWN_OPINION = new ItemCustomized("own_opinion", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized EXPLOSIVE_PELLETS = new ItemCustomized("explosive_pellets", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized LEAD_PELLETS = new ItemCustomized("lead_pellets", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized FLECHETTES = new ItemCustomized("flechettes", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized POISON_GAS_CARTRIDGE = new ItemCustomized("poison_gas_cartridge", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MAGNETRON = new ItemCustomized("magnetron", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DENSE_COAL_CLUSTER = new ItemCustomized("dense_coal_cluster", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MACHINE_UPGRADE_PIECE_TEMPLATE = new ItemCustomized("machine_upgrade_piece_template", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Catalyst matrix

    public static final ItemCustomized BLANK_CATALYST_MATRIX = new ItemCustomized("blank_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized COOL_CATALYST_MATRIX = new ItemCustomized("cool_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized BALANCED_CATALYST_MATRIX = new ItemCustomized("balanced_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ROUGH_CATALYST_MATRIX = new ItemCustomized("rough_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized MULTIPLICATIVE_CATALYST_MATRIX = new ItemCustomized("multiplicative_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized ADDITIVE_CATALYST_MATRIX = new ItemCustomized("additive_catalyst_matrix", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // ???
    public static final ItemCustomized BURNED_OUT_QUAD_SCHRABIDIUM_FUEL_ROD = new ItemCustomized("burned_out_quad_schrabidium_fuel_rod", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Trash

    public static final ItemCustomized SCRAP = new ItemCustomized("scrap", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_URANIUM_FUEL_HOT = new ItemCustomized("depleted_uranium_fuel_hot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_THORIUM_FUEL_HOT = new ItemCustomized("depleted_thorium_fuel_hot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_PLUTONIUM_FUEL_HOT = new ItemCustomized("depleted_plutonium_fuel_hot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_MOX_FUEL_HOT = new ItemCustomized("depleted_mox_fuel_hot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_SCHRABIDIUM_FUEL_HOT = new ItemCustomized("depleted_schrabidium_fuel_hot", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_URANIUM_FUEL = new ItemCustomized("depleted_uranium_fuel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_THORIUM_FUEL = new ItemCustomized("depleted_thorium_fuel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_PLUTONIUM_FUEL = new ItemCustomized("depleted_plutonium_fuel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_MOX_FUEL = new ItemCustomized("depleted_mox_fuel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized DEPLETED_SCHRABIDIUM_FUEL = new ItemCustomized("depleted_schrabidium_fuel", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TRINITITE = new ItemCustomized("trinitite", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized NUCLEAR_WASTE = new ItemCustomized("nuclear_waste", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized TINY_NUCLEAR_WASTE = new ItemCustomized("tiny_nuclear_waste", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    // Crystals

    public static final ItemCustomized CRYSTAL_HORN = new ItemCustomized("crystal_horn", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));
    public static final ItemCustomized CHARRED_CRYSTAL = new ItemCustomized("charred_crystal", new ItemCustomized.Properties().group(CreativeTabs.PARTS_TAB));

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Main.LOGGER.debug("Registering items...");
        ItemCustomized[] items = new ItemCustomized[ItemCustomized.itemBuffer.size()];
        ItemCustomized.itemBuffer.toArray(items);
        event.getRegistry().registerAll(items);
        Main.LOGGER.debug("Items registered");
    }
}
