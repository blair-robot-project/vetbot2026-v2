package frc.team449

import first.robot.subsystems.drive.DriveSubsystem
import frc.team449.Constants.VisionConstants
import frc.team449.subsystems.vision.QuestNav
import frc.team449.subsystems.vision.VisionIOPhotonSim
import frc.team449.subsystems.vision.VisionSubsystem

object RobotContainer {
    val drive: DriveSubsystem =
        DriveSubsystem()
    val questNav: QuestNav =
        QuestNav(
            drive::addVisionMeasurement,
            VisionConstants.ROBOT_QUESTNAV,
        )
    val vision: VisionSubsystem =
        VisionSubsystem(
            drive::addVisionMeasurement,
            { questNav.isTracking },
            VisionIOPhotonSim(
                VisionConstants.CAMERA_ONE_NAME,
                VisionConstants.ROBOT_TO_CAMERA_ONE,
                { drive.pose },
            ),
            VisionIOPhotonSim(
                VisionConstants.CAMERA_TWO_NAME,
                VisionConstants.ROBOT_TO_CAMERA_TWO,
                { drive.pose },
            ),
        )
}
