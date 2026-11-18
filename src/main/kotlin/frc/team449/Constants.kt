package frc.team449

import edu.wpi.first.apriltag.AprilTagFieldLayout
import edu.wpi.first.math.VecBuilder
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

        // --- THRESHOLDS ---
        const val MAX_Z_ERROR_METERS = 0.5 // check
    }
}
