package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Capper extends BaseHardware {

    private static final String TAGCarousel = "8492-Capper";

    private static final double OUT_POS = 1;
    private static final double IN_POS = 0;
    private Capper.Mode Cap_Mode_Current = Mode.PARK;

    private Servo Cap_Plunger_servo = null;
    private DcMotor Cap_Arm_Motor = null;



    public void init(){
        Cap_Plunger_servo = hardwareMap.servo.get("CapPlungerS");
        Cap_Arm_Motor = hardwareMap.dcMotor.get("CapArmM");


        if (Cap_Plunger_servo == null) {
            telemetry.log().add("Cap_Plunger_Servo is null...");
            Cap_Plunger_servo.setPosition(IN_POS);



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
        PARK,
        CAPPING

    }
}
