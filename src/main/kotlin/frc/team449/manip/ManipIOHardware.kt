package frc.team449.manip

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.TalonFX
import com.ctre.phoenix6.signals.GravityTypeValue
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.units.Units
import frc.team449.Constants.ManipConstants

open class ManipIOHardware : ManipIO {

    val rollMotor = TalonFX(ManipConstants.ROLL_ID, CANBus())
    val wristMotor = TalonFX(ManipConstants.WRIST_ID, CANBus())

    private val rollVelocityRequest = VelocityVoltage(0.0)
    private val wristVelocityRequest = VelocityVoltage(0.0)
    private val rollVoltageRequest = VoltageOut(0.0)
    private val wristVoltageRequest = VoltageOut(0.0)
    private val wristPositionRequest = PositionVoltage(0.0)

    private val rollVoltage = rollMotor.motorVoltage
    private val rollPosition = rollMotor.position
    private val rollVelocity = rollMotor.velocity

    private val wristVoltage = wristMotor.motorVoltage
    private val wristPosition = wristMotor.position
    private val wristVelocity = wristMotor.velocity

    private val manipSignals =
        arrayOf(
            rollVoltage,
            rollPosition,
            rollVelocity,
            wristVoltage,
            wristPosition,
            wristVelocity,
        )

    init {
        rollMotor.configurator.apply(rollMotorConfig)
        wristMotor.configurator.apply(wristMotorConfig)
    }

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        BaseStatusSignal.refreshAll(*manipSignals)

        inputs.rollMotorAppliedVoltage = rollVoltage.value.`in`(Units.Volts)
        inputs.rollMotorPosition = rollPosition.value.`in`(Units.Radians)
        inputs.rollMotorVelocity = rollVelocity.value.`in`(Units.RadiansPerSecond)

        inputs.wristMotorAngle = Rotation2d(wristPosition.value.`in`(Units.Radians))
        inputs.wristMotorVelocity = wristVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.wristMotorVoltage = wristVoltage.value.`in`(Units.Volts)
    }

    override fun setRollVoltage(voltage: Double) {
        rollMotor.setControl(rollVoltageRequest.withOutput(voltage))
    }

    override fun setWristVoltage(voltage: Double) {
        wristMotor.setControl(wristVoltageRequest.withOutput(voltage))
    }

    override fun setWristVelocity(radsPerSecond: Double) {
        wristMotor.setControl(
            wristVelocityRequest.withVelocity(radsPerSecond / (2 * Math.PI)),
        )
    }

    override fun setWristAngle(angleRads: Double) {
        wristMotor.setControl(wristPositionRequest.withPosition(angleRads / (2 * Math.PI)))
    }

    override fun setRollVelocity(radsPerSecond: Double) {
        rollMotor.setControl(
            rollVelocityRequest.withVelocity(radsPerSecond / (2 * Math.PI)),
        )
    }

    companion object {
        val rollSlot0Configs =
            Slot0Configs().apply {
                kP = ManipConstants.ROLL_KP
                kI = ManipConstants.ROLL_KI
                kD = ManipConstants.ROLL_KD
                kS = ManipConstants.ROLL_KS
                kV = ManipConstants.ROLL_KV
            }
        val wristSlot0Configs =
            Slot0Configs().apply {
                kP = ManipConstants.WRIST_KP
                kI = ManipConstants.WRIST_KI
                kD = ManipConstants.WRIST_KD
                kS = ManipConstants.WRIST_KS
                kV = ManipConstants.WRIST_KV
                kG = ManipConstants.WRIST_KG
                GravityType = GravityTypeValue.Arm_Cosine
            }

        val rollMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ManipConstants.ROLL_SUPPLY_LIMIT
                    StatorCurrentLimit = ManipConstants.ROLL_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ManipConstants.ROLL_INVERSION
                    NeutralMode = ManipConstants.NEUTRAL_MODE
                }
                Slot0 = rollSlot0Configs
            }
        val wristMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ManipConstants.WRIST_SUPPLY_LIMIT
                    StatorCurrentLimit = ManipConstants.WRIST_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ManipConstants.WRIST_INVERSION
                    NeutralMode = ManipConstants.NEUTRAL_MODE
                }
                Feedback.apply {
                    SensorToMechanismRatio = ManipConstants.WRIST_GEARING
                }
                Slot0 = wristSlot0Configs
            }
    }
}
