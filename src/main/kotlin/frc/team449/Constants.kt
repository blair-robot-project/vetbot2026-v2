package frc.team449

import kotlin.math.PI

object Constants{
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
}
