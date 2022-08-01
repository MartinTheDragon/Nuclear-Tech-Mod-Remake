package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger

@Mod(NuclearTech.MODID)
class NuclearTech {
    init {
        NuclearConfig.registerConfigs(ModLoadingContext.get().activeContainer)
        MinecraftForge.EVENT_BUS.register(this)
        NuclearPacketHandler.initialize()
    }

    companion object {
        const val MODID = "nucleartech"
        val LOGGER: Logger = LogUtils.getLogger()

        val polaroidID: Int get() = NTechItems.polaroid.get().id + 1
        val polaroidBroken: Boolean get() = polaroidID == 11
    }

    @Suppress("unused", "SpellCheckingInspection")
    object SpecialUsers {
        const val HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8"
        const val LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077"
        const val Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0"
        const val a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0"
        const val LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f"
        const val CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1"
        const val dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e"
        const val Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd"
        const val Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda"
        const val Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f"
        const val Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb"
        const val Steelcourage = "ac49720b-4a9a-4459-a26f-bee92160287a"
        const val GOD___TM = "57146e3f-16b5-4e9f-b0b8-139bec2ca2cb"
        const val ZippySqrl = "03c20435-a229-489a-a1a1-671b803f7017"
        const val Schrabby = "3a4a1944-5154-4e67-b80a-b6561e8630b7"
        const val SweatySwiggs = "5544aa30-b305-4362-b2c1-67349bb499d5"
        const val Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b"
        const val Doctor17 = "e4ab1199-1c22-4f82-a516-c3238bc2d0d1"
        const val Doctor17PH = "4d0477d7-58da-41a9-a945-e93df8601c5a"
        const val ShimmeringBlaze = "061bc566-ec74-4307-9614-ac3a70d2ef38"
        const val FifeMiner = "37e5eb63-b9a2-4735-9007-1c77d703daa3"
        const val lag_add = "259785a0-20e9-4c63-9286-ac2f93ff528f"
        const val Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3"
        const val Tankish = "609268ad-5b34-49c2-abba-a9d83229af03"
        const val SolsticeUnlimitd = "f5574fd2-ec28-4927-9d11-3c0c731771f4"
        const val FrizzleFrazzle = "fc4cc2ee-12e8-4097-b26a-1c6cb1b96531"
    }
}
