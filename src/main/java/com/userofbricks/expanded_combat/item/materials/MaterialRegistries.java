package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

@SuppressWarnings("unused")
public class MaterialRegistries {

    static final DeferredRegister<Material> DEFERRED_MATERIALS = DeferredRegister.create(Keys.MATERIALS, Keys.MATERIALS.location().getNamespace());
    public static final Supplier<IForgeRegistry<Material>> MATERIALS = DEFERRED_MATERIALS.makeRegistry(() -> makeRegistry(Keys.MATERIALS, "vanilla"));

    public static List<WeaponMaterial> weaponMaterialConfigs = new ArrayList<>();

    public static List<ShieldToMaterials> shieldToMaterials = new ArrayList<>();

    public static List<Material> materials = new ArrayList<>();
    public static List<Material> gauntletMaterials = new ArrayList<>();
    public static List<Material> shieldMaterials = new ArrayList<>();
    public static List<Material> bowMaterials = new ArrayList<>();
    public static List<Material> crossbowMaterials = new ArrayList<>();
    public static List<Material> arrowMaterials = new ArrayList<>();
    public static List<Material> quiverMaterials = new ArrayList<>();
    public static List<Material> weaponMaterials = new ArrayList<>();

    public static WeaponMaterial BATTLE_STAFF =  new WeaponMaterial.Builder("Battle Staff", CONFIG.battlestaff,     () -> new RecipeIngredientMapBuilder().put('s', ECItems.LEATHER_STICK.get()),  new String[]{"  i", " s ", "i  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial BROAD_SWORD =   new WeaponMaterial.Builder("Broad Sword", CONFIG.broadsword,       RecipeIngredientMapBuilder::new,                                                        new String[]{" i ", "ipi"}).dyeable().hasLargeModel().build();
    public static WeaponMaterial CLAYMORE =      new WeaponMaterial.Builder("Claymore", CONFIG.claymore,            RecipeIngredientMapBuilder::new,                                                        new String[]{"i", "i", "p"}).dyeable().hasLargeModel().build();
    public static WeaponMaterial CUTLASS =       new WeaponMaterial.Builder("Cutlass", CONFIG.cutlass,              () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"i", "i", "s"}).customModelTransforms().build();
    public static WeaponMaterial DAGGER =        new WeaponMaterial.Builder("Dagger", CONFIG.dagger,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.IRON_STICK.get()),     new String[]{"i", "s"}).customModelTransforms().build();
    public static WeaponMaterial DANCERS_SWORD = new WeaponMaterial.Builder("Dancer's Sword", CONFIG.dancers_sword, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"  p", " s ", "p  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial FLAIL =         new WeaponMaterial.Builder("Flail", CONFIG.flail,                  () -> new RecipeIngredientMapBuilder().put('c', Items.CHAIN).put('s', Items.STICK), new String[]{"b", "c", "s"}).blockWeapon().build();
    public static WeaponMaterial GLAIVE =        new WeaponMaterial.Builder("Glaive", CONFIG.glaive,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"  p", " s ", "s  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial GREAT_HAMMER =  new WeaponMaterial.Builder("Great Hammer", CONFIG.great_hammer,    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"  b", " s ", "s  "}).blockWeapon().build();
    public static WeaponMaterial KATANA =        new WeaponMaterial.Builder("Katana", CONFIG.katana,                RecipeIngredientMapBuilder::new,                                                        new String[]{"i", "p"}).hasLargeModel().build();
    public static WeaponMaterial MACE =          new WeaponMaterial.Builder("Mace", CONFIG.mace,                    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{" b", "s "}).blockWeapon().build();
    public static WeaponMaterial SCYTHE =        new WeaponMaterial.Builder("Scythe", CONFIG.scythe,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"ip ", "  s", "  s"}).potionDippable().hasLargeModel().build();
    public static WeaponMaterial SICKLE =        new WeaponMaterial.Builder("Sickle", CONFIG.sickle,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"ii", "s "}).customModelTransforms().build();
    public static WeaponMaterial SPEAR =         new WeaponMaterial.Builder("Spear", CONFIG.spear,                  () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"p", "s", "s"}).hasLargeModel().build();

    public static final RegistryObject<Material> VANILLA =       DEFERRED_MATERIALS.register("vnilla", () -> new Material.Builder(REGISTRATE, "Vanilla",     null, CONFIG.vanilla).shield().build());
    public static final RegistryObject<Material> LEATHER =       DEFERRED_MATERIALS.register("leather", () -> new Material.Builder(REGISTRATE, "Leather",     null, CONFIG.leather).gauntlet().dyeable().quiver().build());
    public static final RegistryObject<Material> WOOD =          DEFERRED_MATERIALS.register("wood", () -> new Material.Builder(REGISTRATE, "Wood",        null, CONFIG.wood).weapons().build());
    public static final RegistryObject<Material> STONE =         DEFERRED_MATERIALS.register("stone", () -> new Material.Builder(REGISTRATE, "Stone",       null, CONFIG.stone).weapons().build());
    public static final RegistryObject<Material> IRON =          DEFERRED_MATERIALS.register("iron", () -> new Material.Builder(REGISTRATE, "Iron",        null, CONFIG.iron).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build());
    public static final RegistryObject<Material> GOLD =          DEFERRED_MATERIALS.register("gold", () -> new Material.Builder(REGISTRATE, "Gold",        null, CONFIG.gold).bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build());
    public static final RegistryObject<Material> DIAMOND =       DEFERRED_MATERIALS.register("diamond", () -> new Material.Builder(REGISTRATE, "Diamond",     IRON,            CONFIG.diamond).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build());
    public static final RegistryObject<Material> NETHERITE =     DEFERRED_MATERIALS.register("netherite", () -> new Material.Builder(REGISTRATE, "Netherite",   null/*DIAMOND*/,         CONFIG.netherite).arrow().bow().crossbow().gauntlet().quiver().shield().weapons().build());
    public static final RegistryObject<Material> STEEL =         DEFERRED_MATERIALS.register("steel", () -> new Material.Builder(REGISTRATE, "Steel",       null, CONFIG.steel).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> BRONZE =        DEFERRED_MATERIALS.register("bronze", () -> new Material.Builder(REGISTRATE, "Bronze",      null, CONFIG.bronze).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> SILVER =        DEFERRED_MATERIALS.register("silver", () -> new Material.Builder(REGISTRATE, "Silver",      null, CONFIG.silver).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> LEAD =          DEFERRED_MATERIALS.register("lead", () -> new Material.Builder(REGISTRATE, "Lead",        null, CONFIG.lead).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> IRONWOOD =      DEFERRED_MATERIALS.register("ironwood", () -> new Material.Builder(REGISTRATE, "Ironwood",    null, CONFIG.ironwood).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> FIERY =         DEFERRED_MATERIALS.register("fiery", () -> new Material.Builder(REGISTRATE, "Fiery",       null, CONFIG.fiery).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> STEELEAF =      DEFERRED_MATERIALS.register("steeleaf", () -> new Material.Builder(REGISTRATE, "Steeleaf",    null, CONFIG.steeleaf).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> KNIGHTMETAL =   DEFERRED_MATERIALS.register("knight_metal", () -> new Material.Builder(REGISTRATE, "Knight Metal",null, CONFIG.knightmetal).gauntlet().shield().weapons().build());
    public static final RegistryObject<Material> NAGASCALE =     DEFERRED_MATERIALS.register("naga_scale", () -> new Material.Builder(REGISTRATE, "Naga Scale",  null, CONFIG.nagaScale).gauntlet().shield().build());
    public static final RegistryObject<Material> YETI =          DEFERRED_MATERIALS.register("yeti", () -> new Material.Builder(REGISTRATE, "Yeti",        null, CONFIG.yeti).gauntlet().build());
    public static final RegistryObject<Material> ARCTIC =        DEFERRED_MATERIALS.register("arctic", () -> new Material.Builder(REGISTRATE, "Arctic",      null, CONFIG.arctic).gauntlet().build());

    public static void loadClass(IEventBus bus) {
        Keys.init();
        DEFERRED_MATERIALS.register(bus);
    }

    public static final class Keys {
        public static final ResourceKey<Registry<Material>> MATERIALS = key(MODID + ":materials");

        private static <T> ResourceKey<Registry<T>> key(String name)
        {
            return ResourceKey.createRegistryKey(new ResourceLocation(name));
        }
        private static void init() {}
    }

    private static final int MAX_VARINT = Integer.MAX_VALUE - 1; //We were told it is their intention to have everything in a reg be unlimited, so assume that until we find cases where it isnt.

    private static <T> RegistryBuilder<T> makeRegistry(ResourceKey<? extends Registry<T>> key, String _default)
    {
        return new RegistryBuilder<T>().setName(key.location()).setMaxID(MAX_VARINT).setDefaultKey(new ResourceLocation(_default));
    }
}
