package frc.team449

import first.robot.subsystems.drive.DriveSubsystem
import frc.team449.Constants.VisionConstants
import frc.team449.subsystems.vision.QuestNav

object RobotContainer {
    val drive: DriveSubsystem =
        DriveSubsystem()
    val questNav: QuestNav =
        QuestNav(
            drive::addVisionMeasurement,
            VisionConstants.ROBOT_QUESTNAV,
        )
}
