package at.martinthedragon.ntm.datagen

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.util.ResourceLocation
import java.util.stream.Collectors

open class ArmatureBuilder(outputLocation: ResourceLocation) : ArmatureFile(outputLocation) {
    protected val joints = JointsBuilder()
    protected val clips = ClipsBuilder()

    override fun exists(): Boolean = true

    fun joints(): JointsBuilder = joints

    fun clips(): ClipsBuilder = clips

    fun toJson(): JsonObject {
        val root = JsonObject()

        val jointsResult = joints.build()
        val clipsResult = clips.build()

        if (jointsResult.isEmpty() || clipsResult.isEmpty())
            return root

        val joints = JsonObject()
        for (joint in jointsResult) {
            val jointWeights = JsonObject()
            for ((index, weight) in joint.indexWeights)
                jointWeights.add(index.toString(), JsonArray().apply { add(weight) })
            joints.add(joint.jointName, jointWeights)
        }

        root.add("joints", joints)

        val clips = JsonObject()
        for ((clipName, clip) in clipsResult) {
            val jointClips = JsonObject()
            for ((jointName, jointClipsList) in clip.jointClips) {
                val jointClipsArray = JsonArray()
                for (jointClip in jointClipsList) {
                    val jointClipObject = JsonObject()
                    jointClipObject.addProperty("variable", jointClip.variable.valueName)
                    jointClipObject.addProperty("type", "uniform")
                    jointClipObject.addProperty("interpolation", jointClip.interpolation.valueName)
                    val samples = JsonArray()
                    for (sample in jointClip.samples)
                        samples.add(sample)
                    jointClipObject.add("samples", samples)
                    jointClipsArray.add(jointClipObject)
                }
                jointClips.add(jointName, jointClipsArray)
            }
            val clipEntry = JsonObject()
            clipEntry.addProperty("loop", clip.loop)
            clipEntry.add("joint_clips", jointClips)

            val events = JsonObject()
            for ((eventTime, eventText) in clip.events) {
                events.addProperty(eventTime.toString(), eventText)
            }

            clipEntry.add("events", events)
            clips.add(clipName, clipEntry)
        }

        root.add("clips", clips)

        return root
    }

    class JointsBuilder {
        private val joints = mutableListOf<Joint>()

        fun joint(jointName: String, vararg indexWeightPairs: Pair<Int, Float>): JointsBuilder {
            joints += Joint(jointName, indexWeightPairs.toMap())
            return this
        }

        fun joint(joint: Joint): JointsBuilder {
            joints += joint
            return this
        }

        fun build(): List<Joint> = joints.toList()

        operator fun contains(jointName: String): Boolean = joints.any { it.jointName == jointName }
    }

    data class Joint(val jointName: String, val indexWeights: Map<Int, Float>) {
        init {
            require(indexWeights.values.all { it in 0f..1f }) { "The joint weight must be between 0 and 1" }
        }
    }

    data class JointClip(val variable: VariableType, val interpolation: InterpolationKind, val samples: FloatArray) {
        enum class VariableType(val valueName: String) {
            OffsetX("offset_x"), OffsetY("offset_y"), OffsetZ("offset_z"),
            Scale("scale"),
            ScaleX("scale_x"), ScaleY("scale_y"), ScaleZ("scale_z"),
            AxisX("axis_x"), AxisY("axis_y"), AxisZ("axis_z"),
            Angle("angle"),
            OriginX("origin_x"), OriginY("origin_y"), OriginZ("origin_z")
        }
        enum class InterpolationKind(val valueName: String) {
            NEAREST("nearest"),
            LINEAR("linear")
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as JointClip

            if (variable != other.variable) return false
            if (interpolation != other.interpolation) return false
            if (!samples.contentEquals(other.samples)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = variable.hashCode()
            result = 31 * result + interpolation.hashCode()
            result = 31 * result + samples.contentHashCode()
            return result
        }
    }

    class Clip(val loop: Boolean, val jointClips: Map<String, List<JointClip>>, val events: Map<Float, String>)

    // clips
    inner class ClipsBuilder {
        private val clips = mutableMapOf<String, ClipEntryBuilder>()

        fun clip(clipName: String): ClipEntryBuilder =
            clips.computeIfAbsent(clipName) { ClipEntryBuilder() }

        fun build(): Map<String, Clip> =
            clips.entries.stream().collect(Collectors.toMap(Map.Entry<String, *>::component1) {
                val buildResult = it.value.build()
                Clip(buildResult.first, buildResult.second, buildResult.third)
            })

        // clip_name
        inner class ClipEntryBuilder {
            private var loop = false
            private val jointClipsList = mutableMapOf<String, JointClipsListBuilder>()
            private val events = mutableMapOf<Float, String>()

            fun loop(loop: Boolean = true): ClipEntryBuilder {
                this.loop = loop
                return this
            }

            fun event(eventTime: Float, eventText: String): ClipEntryBuilder {
                events += eventTime to eventText
                return this
            }

            fun jointClips(jointName: String): JointClipsListBuilder {
                require(jointName in joints) { "Unknown joint '$jointName'. Please specify joints before clips" }
                return jointClipsList.computeIfAbsent(jointName) { JointClipsListBuilder() }
            }

            fun build(): Triple<Boolean, Map<String, List<JointClip>>, Map<Float, String>> = Triple(
                loop,
                jointClipsList.entries.stream().collect(
                    Collectors.toMap(Map.Entry<String, *>::component1) {
                        it.value.build()
                    }),
                events.toMap()
            )

            // joint_clips
            inner class JointClipsListBuilder {
                private val jointClips = mutableListOf<JointClip>()

                @JvmName("addJointClipVararg")
                fun addJointClip(
                    variable: JointClip.VariableType,
                    interpolation: JointClip.InterpolationKind,
                    vararg samples: Float
                ): JointClipsListBuilder = addJointClip(variable, interpolation, samples)

                fun addJointClip(
                    variable: JointClip.VariableType,
                    interpolation: JointClip.InterpolationKind,
                    samples: FloatArray
                ): JointClipsListBuilder {
                    jointClips += JointClip(variable, interpolation, samples)
                    return this
                }

                fun addJointClip(jointClip: JointClip): JointClipsListBuilder {
                    jointClips += jointClip
                    return this
                }

                fun build(): List<JointClip> {
                    return jointClips.toList()
                }
            }
        }
    }
}
