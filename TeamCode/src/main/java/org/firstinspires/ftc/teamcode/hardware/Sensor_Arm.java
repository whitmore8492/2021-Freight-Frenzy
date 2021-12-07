package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;

public class Sensor_Arm extends BaseHardware {

    private static final String TAG = "8492-Sensor_Arm";

    private static final double UP_POS = 0;
    private static final double READ_POS = .6;
    private Sensor_Arm.Mode Sensor_Arm_mode_Current = Mode.UP;

    public static final double R1N_Lower = 36;
    public static final double R1N_Upper = 39;
    public static final double R1M_Lower = 42;
    public static final double R1M_Upper = 44;
    public static final double R1F_Lower = 48;
    public static final double R1F_Upper = 51;

    public static final double R2N_Lower = 25;
    public static final double R2N_Upper = 29;
    public static final double R2M_Lower = 45;
    public static final double R2M_Upper = 48;
    public static final double R2F_Lower = 57;
    public static final double R2F_Upper = 59;

    public static final double B1N_Lower = 5;
    public static final double B1N_Upper = 5;
    public static final double B1M_Lower = 5;
    public static final double B1M_Upper = 5;
    public static final double B1F_Lower = 5;
    public static final double B1F_Upper = 5;

    public static final double B2N_Lower = 5;
    public static final double B2N_Upper = 5;
    public static final double B2M_Lower = 5;
    public static final double B2M_Upper = 5;
    public static final double B2F_Lower = 5;
    public static final double B2F_Upper = 5;

    private static final double MaxDetectRange = 10; // in inches

    private Servo Sensor_Arm_servo = null;


private Rev2mDistanceSensor FrontDS = null;
private Rev2mDistanceSensor RLeftDS = null;
private Rev2mDistanceSensor RRightDS = null;



    public void init(){
        Sensor_Arm_servo = hardwareMap.servo.get("SensorArmS");
        if (Sensor_Arm_servo == null) {
            telemetry.log().add("Arm_Rotator_servo is null...");
        }
        FrontDS = hardwareMap.get(Rev2mDistanceSensor.class, "FrontDS");
        RLeftDS = hardwareMap.get(Rev2mDistanceSensor.class, "RLeftDS");
        RRightDS = hardwareMap.get(Rev2mDistanceSensor.class, "RRightDS");
    }

    public void init_loop() {
        //cmd_Sensor_Arm_Servo_Read();
        telemetry.log().add("FrontDS " + FrontDS.getDistance(DistanceUnit.CM));
        //telemetry.log().add("RLeftDS " + RLeftDS.getDistance(DistanceUnit.CM));
       //telemetry.log().add("RRightDS " + RRightDS.getDistance(DistanceUnit.CM));
    }

    public void start() {
        //cmd_Sensor_Arm_Servo_Up();
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
        //cmd_Sensor_Arm_Servo_Up();

    }

    //public void cmdArmRotatePos_CAROUSEL(){Arm_Rotator_mode_Current = Mode.CAROUSEL; }
    //public void cmdCarouselRun_INTAKE(){ Arm_Rotator_mode_Current = Mode.INTAKE; }
   // public String cmdCurrentMode(){ return Arm_Rotator_mode_Current.name(); }
    public double cmd_Get_Front_Dist(){
        return FrontDS.getDistance(DistanceUnit.CM);
    }

    public void cmd_Sensor_Arm_Servo_Up(){
        Sensor_Arm_servo.setPosition(UP_POS);
    }
    public void cmd_Sensor_Arm_Servo_Read(){
        Sensor_Arm_servo.setPosition(READ_POS);
    }

    public Position Cmd_Get_POS(int seat, Alliance Cur){
         double SDistance = 0;
        switch (Cur){
            case RED:
                switch (seat){
                    case 1:
                        SDistance = RLeftDS.getDistance(DistanceUnit.CM);
                        if (SDistance >= R1N_Lower && SDistance <= R1N_Upper){
                            return Position.NEAR;
                        }
                        else if  (SDistance >= R1M_Lower && SDistance <= R1M_Upper){
                            return Position.MEDIUM;
                        }
                        else if  (SDistance >= R1F_Lower && SDistance <= R1F_Upper){
                            return Position.FAR;
                        }
                        else {
                            return Position.UNKNOWN;
                        }

                    case 2:
                        SDistance = RLeftDS.getDistance(DistanceUnit.CM);
                        if (SDistance >= R2N_Lower && SDistance <= R2N_Upper){
                            return Position.NEAR;
                        }
                        else if  (SDistance >= R2M_Lower && SDistance <= R2M_Upper){
                            return Position.MEDIUM;
                        }
                        else if  (SDistance >= R2F_Lower && SDistance <= R2F_Upper){
                            return Position.FAR;
                        }
                        else {
                            return Position.UNKNOWN;
                        }

                }
                break;
            case BLUE:
                switch (seat){
                    case 1:
                        SDistance = RLeftDS.getDistance(DistanceUnit.CM);
                        if (SDistance >= B1N_Lower && SDistance <= B1N_Upper){
                            return Position.NEAR;
                        }
                        else if  (SDistance >= B1M_Lower && SDistance <= B1M_Upper){
                            return Position.MEDIUM;
                        }
                        else if  (SDistance >= B1F_Lower && SDistance <= B1F_Upper){
                            return Position.FAR;
                        }
                        else {
                            return Position.UNKNOWN;
                        }

                    case 2:
                        SDistance = RLeftDS.getDistance(DistanceUnit.CM);
                        if (SDistance >= B2N_Lower && SDistance <= B2N_Upper){
                            return Position.NEAR;
                        }
                        else if  (SDistance >= B2M_Lower && SDistance <= B2M_Upper){
                            return Position.MEDIUM;
                        }
                        else if  (SDistance >= B2F_Lower && SDistance <= B2F_Upper){
                            return Position.FAR;
                        }
                        else {
                            return Position.UNKNOWN;
                        }

                    default:
                        return Position.UNKNOWN;
                }

            default:
                return Position.UNKNOWN;
        }
        return Position.UNKNOWN;
    }
    public Boolean cmd_Read_Position_Short(Side CUR){
        double SDistance = 0;
        switch (CUR){
            case LEFT:
                SDistance = RLeftDS.getDistance(DistanceUnit.INCH);
                if (SDistance <= MaxDetectRange){
                    return true;
                }
                else {
                    return false;
                }

            case RIGHT:
                SDistance = RRightDS.getDistance(DistanceUnit.INCH);
                if (SDistance <= MaxDetectRange){
                    return true;
                }
                else {
                    return false;
                }

            default:
                return false;
        }

    }

    public enum Side{
        LEFT,
        RIGHT
    }

    public enum Alliance{
        RED,
        BLUE
    }

    public enum Position {
        NEAR,
        MEDIUM,
        FAR,
        UNKNOWN
    }

    public enum Mode {
        UP,
        READ

    }
}
