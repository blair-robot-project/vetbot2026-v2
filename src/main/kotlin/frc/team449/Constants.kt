package frc.team449

import edu.wpi.first.apriltag.AprilTagFieldLayout
import edu.wpi.first.math.VecBuilder
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Transform3d
import edu.wpi.first.wpilibj.Filesystem

object Constants {
    object FieldConstants {
        // --- FIELD DIMENSIONS ---
        val FIELD_LENGTH_METERS = VisionConstants.BUNNYBOTS_FIELD_LAYOUT.fieldLength
        val FIELD_WIDTH_METERS = VisionConstants.BUNNYBOTS_FIELD_LAYOUT.fieldWidth
    }

    object VisionConstants {
        // --- APRIL TAG FIELD ---
        val BUNNYBOTS_FIELD_LAYOUT: AprilTagFieldLayout =
            AprilTagFieldLayout(
                Filesystem.getDeployDirectory().absolutePath + "/Bunnybots_2026.json",
            )

        // --- QUESTNAV STDDEV ---
        val QUESTNAV_STD_DEV =
            VecBuilder.fill(
                0.02,
                0.02,
                0.0872665,
            )

        // --- ROBOT TO QUESTNAV TRANSFORMATION
        val ROBOT_QUESTNAV = Transform3d.kZero

        // --- STD DEVS ---
        const val LINEAR_STD_DEV_BASELINE_METERS = 0.02

        // --- CAMERA NAMES ---
        const val CAMERA_ONE_NAME: String = "arducam-one"
        const val CAMERA_TWO_NAME: String = "arducam-two"

        // --- ROBOT TO CAMERA TRANSFORMATION ---
        val ROBOT_TO_CAMERA_ONE: Pose3d = Pose3d.kZero
        val ROBOT_TO_CAMERA_TWO: Pose3d = Pose3d.kZero

        // --- THRESHOLDS ---
        const val MAX_AMBIGUITY = 0.15 // check rly quick
        const val MAX_Z_ERROR_METERS = 0.5 // check
        const val ANGULAR_STD_DEV_BASELINE_RADIANS = 0.06

        val CAMERA_STD_DEV_FACTORS: DoubleArray =
            doubleArrayOf(
                1.0,
                1.0,
            )
    }
}
