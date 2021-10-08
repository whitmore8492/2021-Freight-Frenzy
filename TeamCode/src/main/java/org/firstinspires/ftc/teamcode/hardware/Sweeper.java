package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Sweeper extends BaseHardware {

    private static final String TAGSweeper = "8492-Sweeper";


    public static final double Sweeper_SPEED = .25;

    private Sweeper.Mode Sweeper_mode_Current = Sweeper.Mode.UNKNOWN;

    private DcMotor Sweepermotor = null;
    private double TargetMotorPowerSweeper = 0.0;


    public void init(){
        Sweepermotor = hardwareMap.dcMotor.get("IntakeM");


        if (Sweepermotor == null) {
            telemetry.log().add("Sweepermotor is null...");

            Sweepermotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        if (Sweeper_mode_Current == Mode.Run)
        Sweepermotor.setPower(Sweeper_SPEED);

        if (Sweeper_mode_Current == Mode.STOPPED){
            Sweepermotor.setPower(0);
        }
    }

    public void stop() {

    }

    public void cmdSweeperRun(){
        Sweeper_mode_Current = Mode.Run;
    }

    public void cmdSweeperSTOPPED(){
        Sweeper_mode_Current = Mode.STOPPED;
    }

    public enum Mode {
        STOPPED,
        Run,
        UNKNOWN
    }
}
