package frc.team449.manip

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team449.Constants.ManipConstants
import org.littletonrobotics.junction.Logger

class ManipIOSubsystem(
    private val io: ManipIO
) : SubsystemBase() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()

    var wristTargetRads: Double = 0.0
    override fun periodic() {
        io.updateInputs(inputs)

        Logger.processInputs("Manipulator", inputs)
        Logger.recordOutput("Manipulator/WristTarget", wristTargetRads)
    }

    fun setMotorVoltage(voltage: Double): Command =
        run {
            io.setRollVoltage(voltage)
        }

    fun grabPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(radsPerSecond)
        }

    fun ejectPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(-radsPerSecond)
        }

    fun setAngle(setpoint: Double): Command =
        runOnce {
            wristTargetRads = setpoint.coerceIn(ManipConstants.MIN_ANGLE_RADS, ManipConstants.MAX_ANGLE_RADS)
            io.setWristAngle(wristTargetRads)
        }

    // does NOT freeze the wrist
    fun stopRoller(): Command =
        runOnce {
            io.setRollVoltage(0.0)
        }
}
