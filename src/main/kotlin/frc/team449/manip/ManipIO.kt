package frc.team449.manip

import edu.wpi.first.math.geometry.Rotation2d
import org.littletonrobotics.junction.AutoLog

interface ManipIO {
    @AutoLog
    open class ManipIOInputs {
        @JvmField var rollMotorAppliedVoltage = 0.0

        @JvmField var rollMotorPosition = 0.0

        @JvmField var rollMotorVelocity = 0.0

        @JvmField var turnMotorVoltage = 0.0

        @JvmField var turnMotorVelocity = 0.0

        @JvmField var turnMotorAngle = Rotation2d()
    }

    fun updateInputs(inputs: ManipIOInputs) {}

    fun setRollVoltage(voltage: Double) {}

    fun setTurnVoltage(voltage: Double) {}

    fun setTurnVelocity(radsPerSecond: Double) {}

    fun setRollVelocity(radsPerSecond: Double) {}
}
