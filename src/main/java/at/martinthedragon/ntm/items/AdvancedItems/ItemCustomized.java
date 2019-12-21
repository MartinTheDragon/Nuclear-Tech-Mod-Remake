package at.martinthedragon.ntm.items.AdvancedItems;

import at.martinthedragon.ntm.lib.RefStrings;
import at.martinthedragon.ntm.main.Main;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCustomized extends Item {

    public static List<ItemCustomized> itemBuffer = new ArrayList<>();

    private World world = null;

    private List<ITextComponent> lore = new ArrayList<>();
    private int amountOfLines = 0;
    private List<ITextComponent> array = new ArrayList<>();

    private String translationKey;

    private Properties properties;

    public ItemCustomized(String registryName, ItemCustomized.Properties properties) {
        super(properties);
        this.properties = properties;
        translationKey = registryName;
        setRegistryName(RefStrings.MODID, registryName);
        itemBuffer.add(this);
    }

    public boolean isRadioactive() {
        return properties.radiation != 0;
    }
    public int getRadiation() {
        return properties.radiation;
    }

    @Override
    @Nonnull
    public ITextComponent getName() {
        return new TranslationTextComponent(getTranslationKey());
    }
    @Override
    @Nonnull
    public String getTranslationKey() {
        return "item.ntm." + translationKey;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        world = worldIn;

        if(properties.getLoreListCount() >= 1) {
            addLocalizedLore(properties.getLoreListCount());
            updateLocalizedLore(true);
        }
        if (lore.isEmpty())
            return;

        updateLocalizedLore(false);
        tooltip.addAll(lore);
    }

    @SuppressWarnings("UnusedReturnValue")
    public ItemCustomized addLore(ITextComponent... lore) {
        this.lore.addAll(Arrays.asList(lore));
        return this;
    }
    public ItemCustomized addLore(List<ITextComponent> lore) {
        this.lore.addAll(lore);
        return this;
    }
    @SuppressWarnings("UnusedReturnValue")
    public ItemCustomized removeLore(int index) {
        try {
            lore.remove(index);
        } catch(IndexOutOfBoundsException e) {
            Main.LOGGER.error("Unable to remove lore from item because there is no entry in" +
                    " the list at the given index:\n" + Arrays.toString(e.getStackTrace()));
        }
        return this;
    }
    @SuppressWarnings("UnusedReturnValue")
    public ItemCustomized clearLore() {
        lore.clear();
        return this;
    }
    @SuppressWarnings("UnusedReturnValue")
    public ItemCustomized addLocalizedLore(int amountOfLines) {
        this.amountOfLines = amountOfLines;
        lore.clear();
        return this;
    }
    private void updateLocalizedLore(boolean firstRun) {
        if(world != null)
            if(world.isRemote) {
                array.clear();
                for(int i = 0; i < amountOfLines; i++) {
                    array.add(i, new StringTextComponent(I18n.format("item.ntm." + translationKey + ".lore" + i)).setStyle(properties.loreStyle));
                }
                if(!lore.equals(array) && !firstRun) {
                    lore.clear();
                    lore.addAll(array);
                } else if(firstRun)
                    lore.addAll(array);
            }
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return properties.getBurnTime() * 20;
    }

    public static class Properties extends Item.Properties {

        private int radiation = 0;
        private int burnTime = 0;

        private int loreLines = 0;
        private Style loreStyle = new Style().setColor(TextFormatting.GRAY);

        public Properties() {
            super();
        }

        @Override
        @Nonnull
        public Properties food(@Nonnull Food food) {
            return (Properties) super.food(food);
        }

        @Override
        @Nonnull
        public Properties maxStackSize(int size) {
            return (Properties) super.maxStackSize(size);
        }

        @Override
        @Nonnull
        public Properties defaultMaxDamage(int defaultDamage) {
            return (Properties) super.defaultMaxDamage(defaultDamage);
        }

        @Override
        @Nonnull
        public Properties maxDamage(int damage) {
            return (Properties) super.maxDamage(damage);
        }

        @Override
        @Nonnull
        public Properties containerItem(@Nonnull Item container) {
            return (Properties) super.containerItem(container);
        }

        @Override
        @Nonnull
        public Properties group(@Nonnull ItemGroup group) {
            return (Properties) super.group(group);
        }

        @Override
        @Nonnull
        public Properties rarity(@Nonnull Rarity rarity) {
            return (Properties) super.rarity(rarity);
        }

        @Override
        @Nonnull
        public Properties setNoRepair() {
            return (Properties) super.setNoRepair();
        }

        @Override
        @Nonnull
        public Properties addToolType(@Nonnull ToolType toolType, int level) {
            return (Properties) super.addToolType(toolType, level);
        }

        // Custom properties

        public Properties radiation(int radiation) {
            this.radiation = radiation;
            return this;
        }

        public Properties setBurnTime(int burnTime) {
            this.burnTime = burnTime;
            return this;
        }
        private int getBurnTime() {
            return burnTime;
        }

        public Properties lore(int lines) {
            loreLines = lines;
            return this;
        }
        private int getLoreListCount() {
            return loreLines;
        }
        private Properties loreStyle(Style style) {
            this.loreStyle = style;
            return this;
        }
    }
}
