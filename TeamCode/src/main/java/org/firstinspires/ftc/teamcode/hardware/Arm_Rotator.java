package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.Settings;

public class Arm_Rotator extends BaseHardware {

    private static final String TAGCarousel = "8492-Arm_Rotator";

    private static final double CAROUSEL_POS = .88;
    private static final double INTAKE_POS = .22;
    private Arm_Rotator.Mode Arm_Rotator_mode_Current = Mode.CAROUSEL;

    private Servo Arm_Rotator_servo = null;



    public void init(){
        Arm_Rotator_servo = hardwareMap.servo.get("ArmRotateS");


        if (Arm_Rotator_servo == null) {
            telemetry.log().add("Arm_Rotator_servo is null...");
            Arm_Rotator_servo.setPosition(CAROUSEL_POS);



        }
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        // if (Carouselmotor.getCurrentPosition() == Carouselmotor.getTargetPosition()){

        if (Arm_Rotator_mode_Current == Mode.CAROUSEL) {
          Arm_Rotator_servo.setPosition(CAROUSEL_POS);
            RobotLog.aa(TAGCarousel, " Arm_Rotator mode " + Arm_Rotator_mode_Current);
        }
        if (Arm_Rotator_mode_Current == Mode.INTAKE) {
            Arm_Rotator_servo.setPosition(INTAKE_POS);
            RobotLog.aa(TAGCarousel, " Arm_Rotator mode " + Arm_Rotator_mode_Current);
        }



    }

    public void stop() {

    }

    public void cmdArmRotatePos_CAROUSEL(){Arm_Rotator_mode_Current = Mode.CAROUSEL; }
    public void cmdCarouselRun_INTAKE(){ Arm_Rotator_mode_Current = Mode.INTAKE; }
    public String cmdCurrentMode(){ return Arm_Rotator_mode_Current.name(); }

    public enum Mode {
        CAROUSEL,
        INTAKE

    }
}
