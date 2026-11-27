package frc.team449

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.RobotBase
import kotlin.math.PI

object Constants {
    // --- OPERATIONAL MODES ---
    enum class Mode {
        REAL,
        SIM,
        REPLAY
    }

    val CURRENT_MODE: Mode = if (RobotBase.isReal()) Mode.REAL else Mode.SIM
    const val TUNING_MODE: Boolean = false

    // --- SYSTEM TIMING ---
    const val LOOP_TIME = 0.02

    // --- PHYSICAL SPECS ---
    const val ROBOT_MASS_KG = 59.8
    const val ROBOT_WIDTH_INCHES = 35.0 // including bumpers (front to rear)
    const val ROBOT_LENGTH_INCHES = 34.125 // including bumpers (left to right)

    object DriveConstants {
        // --- LOOP TIMING ---
        const val ODOMETRY_LOOP_HZ = 100.0

        // --- PHYSICAL SPECS ---
        const val TRACKWIDTH_INCHES = 21.75 // front to rear
        const val WHEELBASE_INCHES = 21.75 // left to right
        const val WHEEL_COF = 1.4

        // --- SPEED LIMITS (STANDARD) ---
        const val MAX_LINEAR_SPEED_METERS_PER_SEC = 4.7244
        const val MAX_ANGULAR_SPEED_RADS_PER_SEC = 2 * PI

        // --- SPEED LIMITS (SLOW) ---
        const val SLOW_LINEAR_SPEED_METERS_PER_SEC = 1.5
        const val SLOW_ANGULAR_SPEED_RADS_PER_SEC = 0.5804

        // --- DEADBANDS & TOLERANCE ---
        const val TRANSLATION_DEADBAND = 0.1
        const val ANGULAR_DEADBAND = 0.1
        const val INTERRUPT_DEADBAND = 0.25
        const val MODULE_ALIGN_TOLERANCE_DEG = 5.0
    }

    object FieldConstants {
        // --- FIELD DIMENSIONS ---
        const val FIELD_LENGTH_METERS = 648 * 0.0254
        const val FIELD_WIDTH_METERS = 324 * 0.0254

        // --- FIELD ELEMENT DIMENSIONS ---
        const val TABLE_LENGTH_METERS = 47 * 0.0254
        const val RAMP_LENGTH_METERS = 90.750 * 0.0254
        const val RAMP_HEIGHT_METERS = 24 * 0.0254
        const val RAMP_WALL_DIST_METERS = 81.531 * 0.0254
        const val PANTRY_LENGTH_METERS = 97 * 0.0254
        const val PANTRY_HEIGHT_METERS = 7 * 0.0254
        const val PANTRY_WALL_DIST_METERS = 12 * 0.0254
        const val DEPOT_LENGTH_METERS = 5 * 0.0254
        const val DEPOT_HEIGHT_METERS = 27 * 0.0254
        const val DEPOT_WALL_DIST_VERT_METERS = 27.766 * 0.0254
        const val DEPOT_WALL_DIST_HORIZ_METERS = 142.611 * 0.0254
    }
}
