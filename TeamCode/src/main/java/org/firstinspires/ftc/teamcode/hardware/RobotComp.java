package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotComp extends BaseHardware {
    public DriveTrain driveTrain = new DriveTrain();
    public Sweeper sweeper = new Sweeper();
    public Carousel carousel = new Carousel();
    public Arm_Rotator arm_rotator = new Arm_Rotator();
    public Delivery delivery = new Delivery();
    public Capper capper = new Capper();
    public Sensor_Arm sensor_arm = new Sensor_Arm();

    private static int TrackUpLimit = 400;
    private static int TrackLowLimit = 400;
    private ElapsedTime runtime = new ElapsedTime();

    private int Detection_Count = 0;
    private int Hit_Count = 0;
    private int Required_Hit_Count = 1;

    @Override
    public void init() {
        // Must set Hardware Map and telemetry before calling init
        driveTrain.hardwareMap = this.hardwareMap;
        driveTrain.telemetry = this.telemetry;
        driveTrain.init();

        sweeper.hardwareMap = this.hardwareMap;
        sweeper.telemetry = this.telemetry;
        sweeper.init();

        carousel.hardwareMap = this.hardwareMap;
        carousel.telemetry = this.telemetry;
        carousel.init();

        arm_rotator.hardwareMap = this.hardwareMap;
        arm_rotator.telemetry = this.telemetry;
        arm_rotator.init();

        delivery.hardwareMap = this.hardwareMap;
        delivery.telemetry = this.telemetry;
        delivery.init();

        capper.hardwareMap = this.hardwareMap;
        capper.telemetry = this.telemetry;
        capper.init();

        sensor_arm.hardwareMap = this.hardwareMap;
        sensor_arm.telemetry = this.telemetry;
        sensor_arm.init();
    }

    @Override
    public void init_loop() {
        driveTrain.init_loop();
        sweeper.init_loop();
        carousel.init_loop();
        arm_rotator.init_loop();
        delivery.init_loop();
        capper.init_loop();
        sensor_arm.init_loop();
    }

    @Override
    public void start() {
        driveTrain.start();
        sweeper.start();
        carousel.start();
        arm_rotator.start();
        delivery.start();
        capper.start();
        sensor_arm.start();
    }

    @Override
    public void loop() {
        driveTrain.loop();
        sweeper.loop();
        carousel.loop();
        arm_rotator.loop();
        delivery.loop();
        capper.loop();
        sensor_arm.loop();


        if (delivery.getTrackMotorPosition() < TrackLowLimit) {
            arm_rotator.cmdArmRotatePos_CAROUSEL();
            sweeper.cmdSweeperSTOPPED();
        }
        /*else if (delivery.getTrackMotorPosition() > TrackLowLimit) {
            arm_rotator.cmdCarouselRun_INTAKE();
            //sweeper.cmdSweeperRun();
        }*/
    /*if ((delivery.cmdCurrentMode() == Delivery.Mode.RECEIVE.toString()) && delivery.cmdIsBoxFull()){
        sweeper.cmdSweeperSTOPPED();
    }*/
    if (delivery.cmdCurrentMode() == Delivery.Mode.RECEIVE.toString()){
        if (delivery.getTrackMotorPosition() >= Delivery.TLOAD_POS){
            sweeper.cmdSweeperRun();

        }
        if (delivery.cmdIsBoxFull()){
            sweeper.cmdSweeperSTOPPED();
            delivery.cmdDeliveryRun_CARRY();
        }
        if (delivery.getTrackMotorPosition() > TrackLowLimit) {
            arm_rotator.cmdCarouselRun_INTAKE();
            //sweeper.cmdSweeperRun();
        }
    }


    }
    //if (delivery.cmdCurrentMode() == ); // If we are in a delivery postion, and box is empty, and the box just closed, then return to carry postition.
    public void cmdReturnTo_Carry(){
   // if (delivery.cmdCurrentMode() == Delivery.Mode.HIGH.toString() ||delivery.cmdCurrentMode() == Delivery.Mode.MIDDLE.toString() ||delivery.cmdCurrentMode() == Delivery.Mode.LOWER.toString()  ){
     delivery.cmdDeliveryRun_CARRY();
    //}



    }
    @Override
    public void stop() {
        driveTrain.stop();
        sweeper.stop();
        carousel.stop();
        arm_rotator.stop();
        delivery.stop();
        capper.stop();
        arm_rotator.stop();
    }
    public void cmd_Set_Delivery_By_Sensor (int seat, Sensor_Arm.Alliance Cur) {
        Sensor_Arm.Position Current_Pos = sensor_arm.Cmd_Get_POS(seat,Cur);
        switch (Current_Pos){
            case NEAR:
                delivery.cmdDeliveryRun_LOWER();
                break;
            case MEDIUM:
                delivery.cmdDeliveryRun_MIDDLE();
                break;
            case FAR:
                delivery.cmdDeliveryRun_HIGH();
                break;
            default:
                delivery.cmdDeliveryRun_HIGH();
                break;
        }


    }

    public boolean cmd_Set_Delivery_By_Sensor_Short (Sensor_Arm.Side CUR, Delivery.Mode Pos){
        telemetry.log().add("**ReadPos " + Pos.toString());
        if(sensor_arm.cmd_Read_Position_Short(CUR)){
            Hit_Count++;
            telemetry.log().add("**HitCount " + Hit_Count);
            if (Hit_Count >= Required_Hit_Count) {
                telemetry.log().add("**SettingPos " + Pos.toString());
                delivery.cmdDeliveryRun(Pos);

                return true;
            }
            else{
                return false;
            }
        }
            else{
                return false;
        }
    }
    public void cmd_Set_Required_Hit_Count(int count){
       Required_Hit_Count = count;
    }
    public void cmd_Reset_Hit_Count(){
        Hit_Count = 0;
    }

}
