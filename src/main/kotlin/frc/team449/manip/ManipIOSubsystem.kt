package frc.team449.manip

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team449.Constants.ManipConstants
import org.littletonrobotics.junction.Logger

class ManipIOSubsystem(
    private val io: ManipIO
) : SubsystemBase() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()
    private val turnController: PIDController = PIDController(
        ManipConstants.TURN_KP_CONTROLLER,
        ManipConstants.TURN_KI_CONTROLLER,
        ManipConstants.TURN_KD_CONTROLLER,
    )

    private var turnSetpointInitilized = false
    override fun periodic() {
        io.updateInputs(inputs)
        if (!turnSetpointInitilized) {
            turnController.setpoint = inputs.turnMotorAngle.radians
            turnSetpointInitilized = true
        }
        io.setTurnVoltage(turnController.calculate(inputs.turnMotorAngle.radians))

        Logger.processInputs("Manipulator", inputs)
        Logger.recordOutput("Manipulator/TurnSetpoint", turnController.setpoint)
    }

    fun setMotorVoltage(voltage: Double): Command =
        runOnce {
            io.setRollVelocity(voltage)
        }

    fun grabPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(radsPerSecond)
        }

    fun ejectPiece(radsPerSecond: Double): Command =
        runOnce {
            io.setRollVelocity(-radsPerSecond)
        }

    fun setAngle(setpoint: Double): Command =
        runOnce {
            turnController.setpoint = setpoint.coerceIn(ManipConstants.MIN_ANGLE_RADS, ManipConstants.MAX_ANGLE_RADS)
        }

    // does NOT freeze pivot
    fun stopRoller(): Command =
        runOnce {
            io.setRollVoltage(0.0)
        }
}
