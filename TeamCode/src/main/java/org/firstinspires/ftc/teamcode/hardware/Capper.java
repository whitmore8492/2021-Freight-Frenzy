package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Capper extends BaseHardware {

    private static final String TAGCapper = "8492-Capper";

    private static final double OUT_POS = 1;
    private static final double IN_POS = 0;
    private Capper.Mode Cap_Mode_Current = Mode.PARK;

    private static final int PARK_POS = 0;
    private static final int COLLECT_POS = 179;

    private Servo Cap_Plunger_servo = null;
    private DcMotor Cap_Arm_Motor = null;
    private static final double AutonArmSpeedCap = .65;
    private static final double ArmSpeedCap = .5;


    public void init(){
        Cap_Plunger_servo = hardwareMap.servo.get("CapPlungerS");
        Cap_Arm_Motor = hardwareMap.dcMotor.get("CapArmM");


        if (Cap_Plunger_servo == null) {
            telemetry.log().add("Cap_Plunger_Servo is null...");


        }
        if (Cap_Arm_Motor == null) {
            telemetry.log().add(("Cap_Arm_Motor is null..."));
        }
        // setting servo parameters
        Cap_Plunger_servo.setPosition(OUT_POS);
        // setting motor parameters
        Cap_Arm_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Cap_Arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void init_loop() {
    //telemetry.log().add("CapperMotorPosition " + Cap_Arm_Motor.getCurrentPosition());
    }

    public void start() {

    }

    public void loop() {

        // if (Carouselmotor.getCurrentPosition() == Carouselmotor.getTargetPosition()){

       switch (Cap_Mode_Current){

           case PARK:
               MoveCapMotor(PARK_POS);

               break;

           case CAPPING:
               MoveCapMotor(COLLECT_POS);
               break;

           case STOP:
               Cap_Arm_Motor.setPower(0);
               break;



       }


    }

    public void stop() {

    }

    public void cmdTeleOp(double ArmSpeed){

        Cap_Arm_Motor.setPower(ArmSpeed * ArmSpeedCap);

    }

    public void cmdpark(){
        // move motor to park
        Cap_Mode_Current = Mode.PARK;
    }
    public void cmdCollect(){
        // move motor to Collect
        Cap_Mode_Current = Mode.CAPPING;

    }
    public void cmdStop(){
        Cap_Mode_Current = Mode.STOP;
    }
    public int  cmdgetcurrentpos(){
       return Cap_Arm_Motor.getCurrentPosition();
    }
    public void cmdPlungerGrab(){
        Cap_Plunger_servo.setPosition(OUT_POS);
    }
    public void cmdPlungerRelease(){
        Cap_Plunger_servo.setPosition(IN_POS);
    }
    private void MoveCapMotor(int Position) {
        Cap_Arm_Motor.setTargetPosition(Position);
        Cap_Arm_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Cap_Arm_Motor.setPower(AutonArmSpeedCap);
        if (Cap_Arm_Motor.getCurrentPosition() == Position ){
            Cap_Mode_Current = Mode.STOP;
        }
        if (Cap_Arm_Motor.getCurrentPosition() > (COLLECT_POS * .8) ){
            Cap_Arm_Motor.setPower(AutonArmSpeedCap * .5);
        }
    }

    public enum Mode {
        PARK,
        CAPPING,
        STOP

    }
}