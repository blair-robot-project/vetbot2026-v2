package first.robot.auto
import com.ctre.phoenix6.swerve.SwerveRequest
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.kinematics.ChassisSpeeds
import frc.robot.lib.BLine.FollowPath
import org.littletonrobotics.junction.Logger
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser
import kotlin.io.path.Path


class BLineRoutines (
    private val drive: DriveSubsystem

) {
    private val translationController = PIDController(0.0,0.0,0.0)
    private val rotationController = PIDController(0.0,0.0,0.0)
    private val crossTrackController = PIDController(0.0, 0.0, 0.0)

    private val applySpeedsRequest = SwerveRequest.ApplyRobotSpeeds()

    private val baseBuilder =
        FollowPath.Builder(
            drive,
            drive.pose2d,
            drive.relativeSpeedsSupplier,
            {speeds: ChassisSpeeds ->
                drive.setControl(applySpeedsRequest.withSpeeds(speeds))
            },
            translationController,
            rotationController,
            crossTrackController
        ).withDefaultShouldFlip()

    fun registerEventTriggers() {
        FollowPath.registerEventTrigger("start intake", ) //put command in once we have it
        FollowPath.registerEventTrigger("outtake in depot") //put command in once we have it
        FollowPath.registerEventTrigger("put up intake") //put command in once we have it
    }

    fun logFollowPath() {
        FollowPath.setPoseLoggingConsumer {pair ->
            Logger.recordOutput(pair.first, pair.second)
        }

        FollowPath.setTranslationListLoggingConsumer { pair ->
            Logger.recordOutput(pair.first, *pair.second)
        }

        FollowPath.setDoubleLoggingConsumer {
                pair ->
            Logger.recordOutput(pair.first, pair.second)
        }
        FollowPath.setBooleanLoggingConsumer {
                pair -> Logger.recordOutput(pair.first, pair.second)
        }

    }

    init {
        logFollowPath()
        registerEventTriggers()
    }

    private fun carrotCakeOptimizer(): Command {
        private val resetBuilder = baseBuilder.withPoseReset(drive::resetOdometry)

        return Commands.sequence(
            //align drive modules
            resetBuilder.build(Path("startToIntakeCarrot.json")),
            baseBuilder.build(Path("carrotToOven.json")),
            baseBuilder.build(Path("ovenToRamp.json")),
            baseBuilder.build(Path("rampToTable.json"))
        )
    }

    fun createAutoChooser(autoChooser: LoggedDashboardChooser<Command>) {
        autoChooser.addOption("Carrot Cake Optimizer!", carrotCakeOptimizer())
    }


}