package frc.team449

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj.DriverStation
import org.ironmaple.simulation.SimulatedArena
import org.ironmaple.simulation.gamepieces.GamePieceProjectile

object Arena2026HarvestHavoc : SimulatedArena(HarvestHavocFieldObstaclesMap) {
    var shouldClock: Boolean = true

    var clock: Double = 0.0

    object HarvestHavocFieldObstaclesMap : FieldMap() {
        const val FIELD_WIDTH = Constants.FieldConstants.FIELD_LENGTH_METERS
        const val FIELD_HEIGHT = Constants.FieldConstants.FIELD_WIDTH_METERS
        const val TABLE_LENGTH = Constants.FieldConstants.TABLE_LENGTH_METERS
        const val RAMP_WIDTH = Constants.FieldConstants.RAMP_LENGTH_METERS
        const val RAMP_HEIGHT = Constants.FieldConstants.RAMP_HEIGHT_METERS
        const val RAMP_DIST = Constants.FieldConstants.RAMP_WALL_DIST_METERS
        const val PANTRY_WIDTH = Constants.FieldConstants.PANTRY_LENGTH_METERS
        const val PANTRY_HEIGHT = Constants.FieldConstants.PANTRY_HEIGHT_METERS
        const val PANTRY_DIST = Constants.FieldConstants.PANTRY_WALL_DIST_METERS
        const val DEPOT_WIDTH = Constants.FieldConstants.DEPOT_LENGTH_METERS
        const val DEPOT_HEIGHT = Constants.FieldConstants.DEPOT_HEIGHT_METERS
        const val DEPOT_VERT_DIST = Constants.FieldConstants.DEPOT_WALL_DIST_VERT_METERS
        const val DEPOT_HORIZ_DIST = Constants.FieldConstants.DEPOT_WALL_DIST_HORIZ_METERS

        init {
            // add outer walls
            super.addBorderLine(Translation2d(0.0, 0.0), Translation2d(0.0, FIELD_HEIGHT))
            super.addBorderLine(Translation2d(FIELD_WIDTH, 0.0), Translation2d(FIELD_WIDTH, FIELD_HEIGHT))
            super.addBorderLine(Translation2d(FIELD_WIDTH, FIELD_HEIGHT), Translation2d(0.0, FIELD_HEIGHT))
            super.addBorderLine(Translation2d(0.0, 0.0), Translation2d(FIELD_WIDTH, 0.0))

            // Add the table
            super.addRectangularObstacle(
                TABLE_LENGTH,
                TABLE_LENGTH,
                Pose2d(FIELD_WIDTH / 2, FIELD_HEIGHT / 2, Rotation2d.fromDegrees(45.0))
            )

            // Add the ramps
            super.addRectangularObstacle(
                RAMP_WIDTH,
                RAMP_HEIGHT,
                Pose2d(RAMP_WIDTH / 2, RAMP_HEIGHT / 2 + RAMP_DIST, Rotation2d.kZero),
            )

            super.addRectangularObstacle(
                RAMP_WIDTH,
                RAMP_HEIGHT,
                Pose2d(FIELD_WIDTH - RAMP_WIDTH / 2, RAMP_HEIGHT / 2 + RAMP_DIST, Rotation2d.kZero),
            )

            // Add the pantries
            super.addRectangularObstacle(
                PANTRY_WIDTH,
                PANTRY_HEIGHT,
                Pose2d(PANTRY_WIDTH / 2 + PANTRY_DIST, FIELD_HEIGHT - PANTRY_HEIGHT / 2, Rotation2d.kZero),
            )

            super.addRectangularObstacle(
                PANTRY_WIDTH,
                PANTRY_HEIGHT,
                Pose2d(FIELD_WIDTH - PANTRY_WIDTH / 2 - PANTRY_DIST, FIELD_HEIGHT - PANTRY_HEIGHT / 2, Rotation2d.kZero),
            )

            // Add the depots
            super.addRectangularObstacle(
                DEPOT_HEIGHT,
                DEPOT_WIDTH,
                Pose2d(DEPOT_HEIGHT / 2 + DEPOT_HORIZ_DIST, DEPOT_WIDTH / 2, Rotation2d.kZero),
            )

            super.addRectangularObstacle(
                DEPOT_HEIGHT,
                DEPOT_WIDTH,
                Pose2d(FIELD_WIDTH - DEPOT_HEIGHT / 2 - DEPOT_HORIZ_DIST, DEPOT_WIDTH / 2, Rotation2d.kZero),
            )

            super.addRectangularObstacle(
                DEPOT_WIDTH,
                DEPOT_HEIGHT,
                Pose2d(DEPOT_WIDTH / 2, DEPOT_HEIGHT / 2 + DEPOT_VERT_DIST, Rotation2d.kZero),
            )

            super.addRectangularObstacle(
                DEPOT_WIDTH,
                DEPOT_HEIGHT,
                Pose2d(FIELD_WIDTH - DEPOT_WIDTH / 2, DEPOT_HEIGHT / 2 + DEPOT_VERT_DIST, Rotation2d.kZero),
            )
        }
    }

    init {
        val settings = physicsWorld.getSettings()

        settings.setMinimumAtRestTime(0.02)

        physicsWorld.setSettings(settings)

        resetFieldForAuto()
    }

    /**
     *
     *
     * <h2>Adds a game piece too the arena with a certain random variance.</h2>
     *
     * This method is useful for certain spawners like the return cutes on the hub to prevent the game pieces from being
     * returned to the exact same position every time.
     *
     * @param info the info of the game piece
     * @param robotPosition the position of the robot (not the shooter) at the time of launching the game piece
     * @param shooterPositionOnRobot the translation from the shooter's position to the robot's center, in the robot's
     * frame of reference
     * @param chassisSpeedsFieldRelative the field-relative velocity of the robot chassis when launching the game piece,
     * influencing the initial velocity of the game piece
     * @param shooterFacing the direction in which the shooter is facing at launch
     * @param initialHeight the initial height of the game piece when launched, i.e., the height of the shooter from the
     * ground
     * @param launchingSpeed the speed at which the game piece is launch
     * @param shooterAngle the pitch angle of the shooter when launching
     * @param xVariance The max amount of variance that should be added too the x coordinate of the game piece.
     * @param yVariance The max amount of variance that should be added too the y coordinate of the game piece.
     * @param yawVariance The max amount of variance that should be added too the yaw of the game piece.
     * @param speedVariance The max amount of variance that should be added too the speed of the game piece.
     * @param pitchVariance The max amount of variance that should be added too the pitch of the game piece.
     */
    /*fun addPieceWithVariance(
        piecePose: Translation2d,
        yaw: Rotation2d,
        height: Distance,
        speed: LinearVelocity,
        pitch: Angle,
        xVariance: Double,
        yVariance: Double,
        yawVariance: Double,
        speedVariance: Double,
        pitchVariance: Double
    ) {
        addGamePieceProjectile(
            RebuiltFuelOnFly(
                piecePose.plus(Translation2d(randomInRange(xVariance), randomInRange(yVariance))),
                Translation2d(),
                ChassisSpeeds(),
                yaw.plus(Rotation2d.fromDegrees(randomInRange(yawVariance))),
                height,
                speed.plus(Units.MetersPerSecond.of(randomInRange(speedVariance))),
                Units.Degrees.of(pitch.`in`(Units.Degrees) + randomInRange(pitchVariance))
            )
        )
    }*/

    override fun placeGamePiecesOnField() {
        for (i in 0..4) {
            var carrot: GamePieceProjectile =
                CarrotOnField(
                    Translation2d(Units.Inches.of(241.611), Units.Inches.of(78.0 + i * 42.0)),
                    i != 0 && i != 4,
                ).enableBecomesGamePieceOnFieldAfterTouchGround()

            carrot.addGamePieceAfterTouchGround(this)
            addGamePieceProjectile(carrot)

            carrot =
                CarrotOnField(
                    Translation2d(Units.Inches.of(648 - 241.611), Units.Inches.of(78.0 + i * 42.0)),
                    i != 0 && i != 4,
                ).enableBecomesGamePieceOnFieldAfterTouchGround()

            carrot.addGamePieceAfterTouchGround(this)
            addGamePieceProjectile(carrot)
        }

        setupValueForMatchBreakdown("CarrotsAreYummy")
    }

    @Synchronized
    override fun getGamePiecesPosesByType(type: String?): MutableList<Pose3d?>? {
        val poses = super.getGamePiecesPosesByType(type)

        return poses
    }

    public override fun simulationSubTick(tickNum: Int) {
        if (shouldClock && !DriverStation.isAutonomous() && DriverStation.isEnabled()) {
            clock -= getSimulationDt().`in`(Units.Seconds)

            if (clock <= 0) {
                clock = 25.0
            }
        } else {
            clock = 25.0
        }

        super.simulationSubTick(tickNum)
    }

    fun setShouldRunClock(shouldRunClock: Boolean) {
        shouldClock = shouldRunClock
    }

    fun randomInRange(variance: Double): Double = (Math.random() - 0.5) * variance
}
