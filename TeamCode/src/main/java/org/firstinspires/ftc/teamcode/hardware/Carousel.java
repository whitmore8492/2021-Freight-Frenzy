package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.Settings;

public class Carousel extends BaseHardware {

    private static final String TAGCarousel = "8492-Carousel";
    public static final int CAROUSEL_ticsPerRev = Settings.REV_HD_40_MOTOR_TICKS_PER_REV;
    public static final double CAROUSEL_wheelDistPerRev = 2 * 3.14159;

    public static final double CAROUSEL_gearRatio = 40.0 / 40.0;
    public static final double CAROUSEL_ticsPerInch = CAROUSEL_ticsPerRev / CAROUSEL_wheelDistPerRev / CAROUSEL_gearRatio;
    public static final double CAROUSEL_spin = 11 * 3.14159 * CAROUSEL_ticsPerInch;
    public static final double Carousel_SPEED = .75;

    private Carousel.Mode Carousel_mode_Current = Mode.STOPPED;

    private DcMotor Carouselmotor = null;
    private double TargetMotorPowerCarousel = 0.0;


    public void init(){
        Carouselmotor = hardwareMap.dcMotor.get("CaroM");


        if (Carouselmotor == null) {
            telemetry.log().add("Carouselmotor is null...");

            Carouselmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Carouselmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        }
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        // if (Carouselmotor.getCurrentPosition() == Carouselmotor.getTargetPosition()){

        if (Carousel_mode_Current == Mode.RUN_RED) {
            Carouselmotor.setTargetPosition((int) CAROUSEL_spin);
            Carouselmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Carouselmotor.setPower(Carousel_SPEED);
            RobotLog.aa(TAGCarousel, " Carousel mode " + Carousel_mode_Current);
        }
        if (Carousel_mode_Current == Mode.RUN_BLUE) {

            Carouselmotor.setTargetPosition((int) -CAROUSEL_spin);
            Carouselmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Carouselmotor.setPower(-Carousel_SPEED);
            RobotLog.aa(TAGCarousel, " Carousel mode " + Carousel_mode_Current);
        }
        if (!Carouselmotor.isBusy()){
            Carousel_mode_Current = Mode.STOPPED;
        }
        if (Carousel_mode_Current == Mode.STOPPED) {
                Carouselmotor.setPower(0);
                Carouselmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RobotLog.aa(TAGCarousel, " Carousel mode " + Carousel_mode_Current);
            }


    }

    public void stop() {

    }

    public void cmdCarouselRun_RED(){
        Carousel_mode_Current = Mode.RUN_RED;
    }
    public void cmdCarouselRun_BLUE(){
        Carousel_mode_Current = Mode.RUN_BLUE;
    }
    public void cmdCarouselSTOPPED(){
        Carousel_mode_Current = Mode.STOPPED;
    }
    public String cmdCurrentMode(){
        return Carousel_mode_Current.name();
    }

    public enum Mode {
        STOPPED,
        RUN_RED,
        RUN_BLUE,
        UNKNOWN
    }
}
