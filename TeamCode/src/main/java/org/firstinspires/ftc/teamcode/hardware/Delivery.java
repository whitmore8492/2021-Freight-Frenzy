package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.Settings;

public class Delivery extends BaseHardware {

    private static final String TAGDelivery = "8492-Delivery";
    public static final int TRACK_ticsPerRev = Settings.REV_HD_40_MOTOR_TICKS_PER_REV;
    public static final double TRACK_wheelDistPerRev = 2 * 3.14159;
    public static final double TRACK_gearRatio = 40.0 / 40.0;
    public static final double TRACK_ticsPerInch = TRACK_ticsPerRev / TRACK_wheelDistPerRev / TRACK_gearRatio;
    public static final double TRACK_spin = 1.25 * 15 * 3.14159 * TRACK_ticsPerInch;
    public static final double TRACK_SPEED = .70;

    public static final int ROTATE_ticsPerRev = Settings.REV_HD_40_MOTOR_TICKS_PER_REV;
    public static final double ROTATE_wheelDistPerRev = 2 * 3.14159;
    public static final double ROTATE_gearRatio = 40.0 / 40.0;
    public static final double ROTATE_ticsPerInch = ROTATE_ticsPerRev / ROTATE_wheelDistPerRev / ROTATE_gearRatio;
    public static final double ROTATE_spin = 1.25 * 15 * 3.14159 * ROTATE_ticsPerInch;
    public static final double ROTATE_SPEED = .70;


    private Delivery.Mode DELIVERY_mode_Current = Mode.CARRY;

    private DcMotor Trackmotor = null;
    private DcMotor Rotatemotor = null;
    private Servo Openservo = null;



    public void init(){
        Trackmotor = hardwareMap.dcMotor.get("TrackM");
        Rotatemotor = hardwareMap.dcMotor.get("RotateM");
        Openservo = hardwareMap.servo.get("OpenS");

        Trackmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Trackmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Rotatemotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Rotatemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

       // Openservo.setPosition(); // we need to set initial position

        if (Trackmotor == null) {
            telemetry.log().add("Trackmotor is null...");
        }
        if (Rotatemotor == null) {
            telemetry.log().add("Rotatemotor is null...");
        }
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        // if (Carouselmotor.getCurrentPosition() == Carouselmotor.getTargetPosition()){

        if (DELIVERY_mode_Current == Mode.CARRY) {
           // Carouselmotor.setTargetPosition((int) CAROUSEL_spin);
           // Carouselmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Carouselmotor.setPower(Carousel_SPEED);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        /*if (!Carouselmotor.isBusy()){
            DELIVERY_mode_Current = Mode.STOPPED;
        }*/
        if (DELIVERY_mode_Current == Mode.STOPPED) {
                Trackmotor.setPower(0);
                Rotatemotor.setPower(0);
                RobotLog.aa(TAGDelivery, " Carousel mode " + DELIVERY_mode_Current);
            }


    }

    public void stop() {

    }

    public void cmdDeliveryRun_RECEIVE(){
       DELIVERY_mode_Current = Mode.RECEIVE;
    }
    public void cmdDeliveryRun_CARRY(){
        DELIVERY_mode_Current = Mode.CARRY;
    }
    public void cmdDeliveryRun_LOWER(){
        DELIVERY_mode_Current = Mode.LOWER;
    }
    public String cmdCurrentMode(){
        return DELIVERY_mode_Current.name();
    }

    public enum Mode {
        RECEIVE,
        CARRY,
        LOWER,
        MIDDLE,
        HIGH,
    STOPPED
    }
}
