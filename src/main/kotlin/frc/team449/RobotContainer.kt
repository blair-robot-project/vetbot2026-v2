package frc.team449

import edu.wpi.first.wpilibj2.command.Command
import first.robot.auto.BLineRoutines
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser

object RobotContainer {




    val autoRoutines = BLineRoutines(DriveSubsystem, robotActions) //or a list of all the other subsystems?
    val autoChooser = LoggedDashboardChooser<Command>("Auto Routines")
}
