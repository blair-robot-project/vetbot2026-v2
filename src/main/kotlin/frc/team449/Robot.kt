package frc.team449

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser

class Robot : LoggedRobot() {
    private val robotContainer = RobotContainer

    override fun robotInit() {
        robotContainer.autoRoutines.createAutoChooser(robotContainer.autoChooser)
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    override fun autonomousInit() {
    }

    override fun autonomousPeriodic() {}

    override fun teleopInit() {}

    override fun teleopPeriodic() {}
}
