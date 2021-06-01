package at.martinthedragon.ntm.datagen

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper

class BlockArmatureProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : ArmatureProvider<ArmatureBuilder>(dataGenerator, BLOCK_FOLDER, ::ArmatureBuilder, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block Armatures"

    override fun registerArmatures() {
        val steamPressArmature = builder("machines/steam_press_top")
        steamPressArmature.joints().joint("press_head", 2 to 1f, 3 to 1f)
        steamPressArmature.clips().clip("move_down").jointClips("press_head")
            .addJointClip(ArmatureBuilder.JointClip.VariableType.OffsetY, ArmatureBuilder.JointClip.InterpolationKind.LINEAR, 0f, 1f)
        steamPressArmature.clips().clip("move_up").jointClips("press_head")
            .addJointClip(ArmatureBuilder.JointClip.VariableType.OffsetY, ArmatureBuilder.JointClip.InterpolationKind.LINEAR, 1f, 0f)
    }
}
