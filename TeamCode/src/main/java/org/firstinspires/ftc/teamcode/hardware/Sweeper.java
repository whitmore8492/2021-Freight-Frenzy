package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;

public class Sweeper extends BaseHardware {

    private static final String TAGSweeper = "8492-Sweeper";


    public static final double Sweeper_SPEED = .70;

    private Sweeper.Mode Sweeper_mode_Current = Sweeper.Mode.UNKNOWN;

    private DcMotor Sweepermotor = null;
    private double TargetMotorPowerSweeper = 0.0;


    public void init(){
        Sweepermotor = hardwareMap.dcMotor.get("IntakeM");
        Sweepermotor.setDirection(DcMotorSimple.Direction.REVERSE);

        if (Sweepermotor == null) {
            telemetry.log().add("Sweepermotor is null...");

           //Sweepermotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        if (Sweeper_mode_Current == Mode.RUN) {
            Sweepermotor.setPower(Sweeper_SPEED);
            RobotLog.aa(TAGSweeper, " Sweeper mode " + Sweeper_mode_Current);
        }

            if (Sweeper_mode_Current == Mode.STOPPED) {
                Sweepermotor.setPower(0);
                RobotLog.aa(TAGSweeper, " Sweeper mode " + Sweeper_mode_Current);
            }
    }

    public void stop() {

    }

    public void cmdSweeperRun(){
        Sweeper_mode_Current = Mode.RUN;
    }

    public void cmdSweeperSTOPPED(){
        Sweeper_mode_Current = Mode.STOPPED;
    }
    public String cmdCurrentMode(){
        return Sweeper_mode_Current.name();
    }

    public enum Mode {
        STOPPED,
        RUN,
        UNKNOWN
    }
}
