package frc.team449

import edu.wpi.first.wpilibj2.command.button.CommandXboxController

class Bindings(
    val robotContainer: RobotContainer
) {
    val driverController: CommandXboxController = robotContainer.driverController

    fun bindings() {
        driverController.x().onTrue(
            robotContainer.elevator.moveElevator(1.0),
        )
    }
}
