package frc.team449.manip

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.system.plant.LinearSystemId
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.simulation.DCMotorSim
import frc.team449.Constants
import frc.team449.Constants.ManipConstants

class ManipIOSim : ManipIOHardware() {
    private val rollSim: DCMotorSim =
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX44(1),
                ManipConstants.ROLL_MOI_KG_MM, // moI, idk
                ManipConstants.ROLL_GEARING, // gearing... idk
            ),
            DCMotor.getKrakenX60(1)
        )

    private val turnSim: DCMotorSim =
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX44(1),
                ManipConstants.TURN_MOI_KG_MM, // moI, idk
                ManipConstants.TURN_GEARING, // gearing... idk
            ),
            DCMotor.getKrakenX60(1)
        )

    private val rollMotorSim = rollMotor.simState
    private val turnMotorSim = turnMotor.simState
    init {
        turnMotorSim.setRawRotorPosition(
            Units.radiansToRotations(ManipConstants.MIN_ANGLE_RADS) * ManipConstants.TURN_GEARING
        )
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
        // turn pivot
        turnMotorSim.setSupplyVoltage(12.0)
        turnSim.setInput(turnMotorSim.motorVoltage)
        turnSim.update(Constants.LOOP_TIME)
        turnMotorSim.setRawRotorPosition(
            Units.radiansToRotations(turnSim.angularPositionRad) * ManipConstants.TURN_GEARING,
        )
        turnMotorSim.setRotorVelocity(
            Units.radiansToRotations(turnSim.angularVelocityRadPerSec) * ManipConstants.TURN_GEARING,
        )
    }
}
