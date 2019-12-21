package at.martinthedragon.ntm.main;

import at.martinthedragon.ntm.items.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class CreativeTabs {
    public static final ItemGroup PARTS_TAB = new ItemGroup("ntm_parts") {
        @OnlyIn(Dist.CLIENT)
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(Items.URANIUM_INGOT);
        }
    };
}
