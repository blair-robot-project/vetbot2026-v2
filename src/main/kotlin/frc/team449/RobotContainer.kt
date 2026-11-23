package frc.team449

import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.team449.subsystems.elevator.ElevatorIOSim
import frc.team449.subsystems.elevator.ElevatorSubsystem

object RobotContainer {
    val driverController: CommandXboxController = CommandXboxController(0)

    val elevator: ElevatorSubsystem =
        ElevatorSubsystem(ElevatorIOSim())

    val bindings = Bindings(this)
}
