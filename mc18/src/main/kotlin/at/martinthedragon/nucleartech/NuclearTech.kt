package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.coremodules.minecraftModule
import at.martinthedragon.nucleartech.coremodules.koinApp
import at.martinthedragon.nucleartech.coremodules.minecraftClientModule
import at.martinthedragon.nucleartech.coremodules.registriesModule
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import com.mojang.logging.LogUtils
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.VersionChecker
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.forgespi.language.IModInfo
import org.koin.dsl.koinApplication
import org.slf4j.Logger
import kotlin.jvm.optionals.getOrNull

@Mod(NuclearTech.MODID)
class NuclearTech : ModClass {
    init {
        koinApp = koinApplication {
            modules(
                minecraftModule,
                registriesModule
            )
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT) { Runnable {
                modules(
                    minecraftClientModule
                )
            }}
        }
        entryPoint()
        NuclearConfig.registerConfigs(ModLoadingContext.get().activeContainer)
        MinecraftForge.EVENT_BUS.register(this)
        NuclearPacketHandler.initialize()
    }

    companion object {
        const val MODID = "nucleartech"
        val LOGGER: Logger = LogUtils.getLogger()

        val polaroidID: Int get() = NTechItems.polaroid.get().id + 1
        val polaroidBroken: Boolean get() = polaroidID == 11

        val modInfo: IModInfo? by lazy { ModList.get().getModContainerById(MODID).getOrNull()?.modInfo }
        val currentVersion: String? by lazy { modInfo?.version?.toString() }
        val isSnapshot: Boolean by lazy { currentVersion?.contains("snapshot") == true }
        val versionCheckResult: VersionChecker.CheckResult? by lazy { modInfo?.let{ VersionChecker.getResult(it) }}
    }

    @Suppress("unused", "SpellCheckingInspection")
    object SpecialUsers {
        // 1.18.2
        const val MartinTheDragon = "3e763f54-12fc-40ef-9416-72edc689a952"
        const val JulekJulas = "8996d0cc-5bcd-4332-bcc0-fc3391f34c07"
        const val TheOriginalGolem = "058b52a6-05b7-4d11-8cfa-2db665d9a521"
        const val KauaiMoho = "2bdb22e4-96e9-49c5-be8a-4f8d52c6188c" // 6litchh

        // 1.7.10
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
        const val mustang_rudolf = "06ab7c03-55ce-43f8-9d3c-2850e3c652de"
        const val JMF781 = "5bf069bc-5b46-4179-aafe-35c0a07dee8b"
        const val FifeMiner = "37e5eb63-b9a2-4735-9007-1c77d703daa3"
        const val lag_add = "259785a0-20e9-4c63-9286-ac2f93ff528f"
        const val Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3"
        const val Tankish = "609268ad-5b34-49c2-abba-a9d83229af03"
        const val SolsticeUnlimitd = "f5574fd2-ec28-4927-9d11-3c0c731771f4"
        const val FrizzleFrazzle = "fc4cc2ee-12e8-4097-b26a-1c6cb1b96531"
        const val Barnaby99_x = "711aaf78-a862-4b7e-921a-216349716e9a"
        const val Ma118 = "1121cb7a-8773-491f-8e2b-221290c93d81"
        const val Adam29Adam29 = "bbae7bfa-0eba-40ac-a0dd-f3b715e73e61"

        // 1.12.2
        const val Malpon = "0b399a4a-8545-45a1-be3d-ece70d7d48e9" // Alcater
    }
}
