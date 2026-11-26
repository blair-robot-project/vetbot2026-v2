package frc.team449.manip

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.system.plant.LinearSystemId
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.simulation.FlywheelSim
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim
import frc.team449.Constants
import frc.team449.Constants.ManipConstants
import org.littletonrobotics.junction.Logger

class ManipIOSim : ManipIOHardware() {
    private val rollSim: FlywheelSim =
        FlywheelSim(
            LinearSystemId.createFlywheelSystem(
                DCMotor.getKrakenX44(1),
                ManipConstants.ROLL_MOI_KG_MM, // moI, idk
                ManipConstants.ROLL_GEARING, // gearing... idk
            ),
            DCMotor.getKrakenX44(1)
        )

    private val wristSim: SingleJointedArmSim =
        SingleJointedArmSim(
            DCMotor.getKrakenX44(1),
            ManipConstants.WRIST_GEARING,
            ManipConstants.WRIST_MOI_KG_MM,
            ManipConstants.ARM_LENGTH_METERS,
            ManipConstants.MIN_ANGLE_RADS,
            ManipConstants.MAX_ANGLE_RADS,
            true,
            ManipConstants.MIN_ANGLE_RADS,
        )

    private val rollMotorSim = rollMotor.simState
    private val wristMotorSim = wristMotor.simState
    private var rollTargetVelocity = 0.0
    init {
        wristMotorSim.setRawRotorPosition(
            Units.radiansToRotations(ManipConstants.MIN_ANGLE_RADS) * ManipConstants.WRIST_GEARING
        )
    }

    override fun setRollVelocity(radsPerSecond: Double) {
        rollTargetVelocity = radsPerSecond
        super.setRollVelocity(radsPerSecond)
    }

    override fun setRollVoltage(voltage: Double) {
        rollTargetVelocity = 0.0
        super.setRollVoltage(voltage)
    }

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        super.updateInputs(inputs)

        // roller
        rollMotorSim.setSupplyVoltage(12.0)
        rollSim.setInput(rollMotorSim.motorVoltage)
        rollSim.update(Constants.LOOP_TIME)

        val rollRotorRps = Units.radiansToRotations(rollSim.angularVelocityRadPerSec) * ManipConstants.ROLL_GEARING
        rollMotorSim.setRotorVelocity(rollRotorRps)
        rollMotorSim.addRotorPosition(rollRotorRps * Constants.LOOP_TIME)
        Logger.recordOutput("ManipSim roll speed", rollTargetVelocity)

        // wrist
        wristMotorSim.setSupplyVoltage(12.0)
        wristSim.setInput(wristMotorSim.motorVoltage)
        wristSim.update(Constants.LOOP_TIME)
        wristMotorSim.setRawRotorPosition(
            Units.radiansToRotations(wristSim.angleRads) * ManipConstants.WRIST_GEARING,
        )
        wristMotorSim.setRotorVelocity(
            Units.radiansToRotations(wristSim.velocityRadPerSec) * ManipConstants.WRIST_GEARING,
        )
    }
}
