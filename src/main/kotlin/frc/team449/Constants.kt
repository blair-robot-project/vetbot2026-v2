package frc.team449

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

object Constants {
    const val LOOP_TIME = 0.02

    // ...your other constants

    object ManipConstants {
        // --- HARDWARE IDs (placeholders: match Phoenix Tuner) ---
        const val ROLL_ID = 1
        const val WRIST_ID = 2

        // --- HARDWARE CONFIGURATION ---
        val ROLL_INVERSION = InvertedValue.CounterClockwise_Positive
        val WRIST_INVERSION = InvertedValue.CounterClockwise_Positive
        val NEUTRAL_MODE = NeutralModeValue.Brake

        // --- CURRENT LIMITS (amps) ---
        const val ROLL_SUPPLY_LIMIT = 30.0
        const val ROLL_STATOR_LIMIT = 60.0
        const val WRIST_SUPPLY_LIMIT = 30.0
        const val WRIST_STATOR_LIMIT = 60.0

        // --- VELOCITY GAINS (Slot0) ---
        const val ROLL_KP = 0.1
        const val ROLL_KI = 0.0
        const val ROLL_KD = 0.0
        const val ROLL_KS = 0.0
        const val ROLL_KV = 0.12

        const val WRIST_KP = 20.0
        const val WRIST_KI = 0.0
        const val WRIST_KD = 0.0
        const val WRIST_KS = 0.0
        const val WRIST_KV = 0.12
        const val WRIST_KG = 0.5

        // --- PHYSICAL SPECS & GEARING (motor:mechanism, >1 is a reduction) ---
        const val ROLL_GEARING = 58.0 / 16.0
        const val ROLL_MOI_KG_MM = 0.001

        // --- TURN PIVOT (sim) ---
        const val WRIST_MOI_KG_MM = 0.05
        const val ARM_LENGTH_METERS = 0.3
        const val MIN_ANGLE_RADS = 0.0
        const val MAX_ANGLE_RADS = Math.PI / 2
        const val WRIST_KP_CONTROLLER = 0.0
        const val WRIST_KI_CONTROLLER = 0.0
        const val WRIST_KD_CONTROLLER = 0.0
        const val WRIST_GEARING = 30.80
        const val ROLL_SPEED = 100.0
    }
}
