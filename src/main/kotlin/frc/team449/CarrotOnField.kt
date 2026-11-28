package frc.team449

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Rotation3d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.units.Units
import edu.wpi.first.units.Units.Inches
import edu.wpi.first.units.Units.Kilograms
import org.dyn4j.geometry.Circle
import org.dyn4j.geometry.Rectangle
import org.dyn4j.geometry.Rotation
import org.ironmaple.simulation.gamepieces.GamePieceOnFieldSimulation
import org.ironmaple.simulation.gamepieces.GamePieceProjectile
import kotlin.math.PI

class CarrotOnField(
    initialPosition: Translation2d,
    horizontal: Boolean,
    initialRotation: Rotation3d = Rotation3d.kZero,
) : GamePieceProjectile(
    if (horizontal) CARROT_HORIZONTAL_INFO else CARROT_VERTICAL_INFO,
    initialPosition,
    Translation2d(),
    if (horizontal) 3.0 else 9.0,
    0.0,
    if (horizontal) initialRotation else Rotation3d(0.0, PI / 2, 0.0)
) {
    companion object {
        val CARROT_HORIZONTAL_INFO =
            GamePieceOnFieldSimulation.GamePieceInfo(
                "Carrot",
                Rectangle(
                    Inches.of(18.0).`in`(Units.Meters),
                    Inches.of(6.0).`in`(Units.Meters),
                ),
                Inches.of(6.0),
                Kilograms.of(0.647),
                1.8,
                5.0,
                0.8,
            )

        val CARROT_VERTICAL_INFO =
            GamePieceOnFieldSimulation.GamePieceInfo(
                "Carrot",
                Circle(
                    Inches.of(6.0).`in`(Units.Meters),
                ),
                Inches.of(18.0),
                Kilograms.of(0.647),
                1.8,
                5.0,
                0.8,
            )
    }
}
