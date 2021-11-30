package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

public class Sensor_Arm extends BaseHardware {

    private static final String TAG = "8492-Sensor_Arm";

    private static final double UP_POS = 1;
    private static final double READ_POS = 0;
    private Sensor_Arm.Mode Sensor_Arm_mode_Current = Mode.UP;

    public static final double R1N_Lower = 5;

    private Servo Sensor_Arm_servo = null;


private Rev2mDistanceSensor FrontDS = null;
private Rev2mDistanceSensor RLeftDS = null;
private Rev2mDistanceSensor RRightDS = null;



    public void init(){
        Sensor_Arm_servo = hardwareMap.servo.get("SensorArmS");
        if (Sensor_Arm_servo == null) {
            telemetry.log().add("Arm_Rotator_servo is null...");
        }
        //FrontDS = hardwareMap.get(Rev2mDistanceSensor.class, "FrontDS");
        //RLeftDS = hardwareMap.get(Rev2mDistanceSensor.class, "RLeftDS");
        //RRightDS = hardwareMap.get(Rev2mDistanceSensor.class, "RRightDS");
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        // if (Carouselmotor.getCurrentPosition() == Carouselmotor.getTargetPosition()){

        /*if (Arm_Rotator_mode_Current == Mode.CAROUSEL) {
          Arm_Rotator_servo.setPosition(CAROUSEL_POS);
            RobotLog.aa(TAGCarousel, " Arm_Rotator mode " + Arm_Rotator_mode_Current);
        }
        if (Arm_Rotator_mode_Current == Mode.INTAKE) {
            Arm_Rotator_servo.setPosition(INTAKE_POS);
            RobotLog.aa(TAGCarousel, " Arm_Rotator mode " + Arm_Rotator_mode_Current);
        }*/



    }

    public void stop() {

    }

    //public void cmdArmRotatePos_CAROUSEL(){Arm_Rotator_mode_Current = Mode.CAROUSEL; }
    //public void cmdCarouselRun_INTAKE(){ Arm_Rotator_mode_Current = Mode.INTAKE; }
   // public String cmdCurrentMode(){ return Arm_Rotator_mode_Current.name(); }

    public enum Mode {
        UP,
        READ

    }
}
