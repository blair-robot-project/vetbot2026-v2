package frc.team449.subsystems.elevator

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import first.robot.subsystems.elevator.ElevatorIO
import first.robot.subsystems.elevator.ElevatorIOInputsAutoLogged
import org.littletonrobotics.junction.Logger

class ElevatorSubsystem(
    private val io: ElevatorIO
) : SubsystemBase() {
    private val inputs: ElevatorIOInputsAutoLogged = ElevatorIOInputsAutoLogged()

    var elevatorTargetPosition: Double = 0.0
    val elevatorPos: Double
        get() = inputs.elevatorPosMeters

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Elevator", inputs)

        Logger.recordOutput("Elevator/Target Position", elevatorTargetPosition)
        Logger.recordOutput("Elevator/ActiveCommands", currentCommand?.name ?: "None")
    }

    fun moveElevator(m: Double): Command =
        runOnce {
            elevatorTargetPosition = m
            io.setPosition(m)
        }.withName("Move Elevator")
}
