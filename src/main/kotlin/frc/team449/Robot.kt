package frc.team449

import edu.wpi.first.wpilibj.DataLogManager
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.networktables.NT4Publisher

class Robot : LoggedRobot() {
    private val robotContainer = RobotContainer()

    init {
        DataLogManager.start()

        DriverStation.startDataLog(DataLogManager.getLog(), true)
        Logger.addDataReceiver(NT4Publisher())

        Logger.start()
        robotContainer.bindings.robotContainer.bindings.bindControls()
    }

    override fun robotInit() {}

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    override fun autonomousInit() {
    }

    override fun autonomousPeriodic() {}

    override fun teleopInit() {}

    override fun teleopPeriodic() {}
}
