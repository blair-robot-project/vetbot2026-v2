package frc.team449.manip

import edu.wpi.first.math.geometry.Rotation2d
import org.littletonrobotics.junction.AutoLog

interface ManipIO {
    @AutoLog
    open class ManipIOInputs {
        @JvmField var rollMotorAppliedVoltage = 0.0

        @JvmField var rollMotorPosition = 0.0

        @JvmField var rollMotorVelocity = 0.0

        @JvmField var wristMotorVoltage = 0.0

        @JvmField var wristMotorVelocity = 0.0

        @JvmField var wristMotorAngle = Rotation2d()
    }

    fun updateInputs(inputs: ManipIOInputs) {}

    fun setRollVoltage(voltage: Double) {}

    fun setWristVoltage(voltage: Double) {}

    fun setWristVelocity(radsPerSecond: Double) {}

    fun setWristAngle(angleRads: Double) {}

    fun setRollVelocity(radsPerSecond: Double) {}
}
