package at.martinthedragon.ntm.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("ntm")
public class Main {
    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
