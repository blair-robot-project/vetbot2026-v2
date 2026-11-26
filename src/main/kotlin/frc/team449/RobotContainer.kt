package frc.team449
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.team449.manip.ManipIOSim
import frc.team449.manip.ManipIOSubsystem

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The container for the robot. Contains subsystems, OI devices, and commands.
    val driverController: CommandXboxController = CommandXboxController(0)
    val manipulator: ManipIOSubsystem = ManipIOSubsystem(ManipIOSim())
    val bindings = Bindings(this)
}
