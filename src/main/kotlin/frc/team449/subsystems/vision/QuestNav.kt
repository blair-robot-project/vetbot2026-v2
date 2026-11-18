package frc.team449.subsystems.vision

import edu.wpi.first.math.Matrix
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Transform3d
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team449.Constants
import gg.questnav.questnav.QuestNav
import org.littletonrobotics.junction.Logger
import kotlin.math.abs

class QuestNav(
    private val consumeVisionMeasurement: (
        visionRobotPoseMeters: Pose2d,
        timeStampSeconds: Double,
        visionMeasurementStdDevs: Matrix<N3, N1>
    ) -> Unit,
    val offset: Transform3d
) : SubsystemBase() {
    val questNav = QuestNav()

    val isTracking: Boolean
        get() = questNav.isTracking

    override fun periodic() {
        questNav.commandPeriodic()

        Logger.recordOutput("Vision/QuestNav/LatencyMS", questNav.latency)
        questNav.batteryPercent.ifPresent { b ->
            Logger.recordOutput("Vision/QuestNav/BatteryPercentage", b)
        }
        Logger.recordOutput("Vision/QuestNav/IsConnected", questNav.isConnected)
        Logger.recordOutput("Vision/QuestNav/IsTracking", isTracking)
        questNav.trackingLostCounter.ifPresent { t ->
            Logger.recordOutput("Vision/QuestNav/TrackingLostCounter", t)
        }

        val rejectedRobotPose = mutableSetOf<Pose3d>()
        val acceptedRobotPose = mutableSetOf<Pose3d>()

        for (questFrames in questNav.allUnreadPoseFrames) {
            if (questFrames.isTracking) {
                val questPose: Pose3d = questFrames.questPose3d

                val robotPose = questPose.transformBy(offset.inverse())

                val rejectPose =
                    abs(robotPose.z) > Constants.VisionConstants.MAX_Z_ERROR_METERS ||
                        robotPose.x < 0.0 ||
                        robotPose.x > Constants.FieldConstants.FIELD_LENGTH_METERS ||
                        robotPose.y < 0.0 ||
                        robotPose.y > Constants.FieldConstants.FIELD_WIDTH_METERS

                if (rejectPose) {
                    rejectedRobotPose.add(robotPose)
                    continue
                }

                acceptedRobotPose.add(robotPose)

                consumeVisionMeasurement(
                    robotPose.toPose2d(),
                    questFrames.dataTimestamp,
                    Constants.VisionConstants.QUESTNAV_STD_DEV,
                )
            }
        }
        Logger.recordOutput("Vision/QuestNav/AcceptedPoses", *acceptedRobotPose.toTypedArray())
        Logger.recordOutput("Vision/QuestNav/RejectedPose", *rejectedRobotPose.toTypedArray())
    }
}
