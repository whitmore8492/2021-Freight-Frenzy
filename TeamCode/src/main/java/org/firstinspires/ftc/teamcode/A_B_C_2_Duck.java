package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Settings;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.RobotComp;

@Autonomous(name = "A_B_C_2_Duck", group = "Auton")
// @Autonomous(...) is the other common choice

public class A_B_C_2_Duck extends OpMode {

    //RobotComp robot = new RobotComp();
    RobotComp robot = new RobotComp();




    private stage currentStage = stage._unknown;
    // declare auton power variables
    private double AUTO_DRIVE_TURBO_SPEED = DriveTrain.DRIVETRAIN_TURBOSPEED;
    private double AUTO_DRIVE_SLOW_SPEED = 0.35;
    private double AUTO_DRIVE_NORMAL_SPEED = DriveTrain.DRIVETRAIN_NORMALSPEED;
    private double AUTO_TURN_SPEED = DriveTrain.DRIVETRAIN_TURNSPEED;

    private String RTAG = "8492-Auton";

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        //----------------------------------------------------------------------------------------------
        // These constants manage the duration we allow for callbacks to user code to run for before
        // such code is considered to be stuck (in an infinite loop, or wherever) and consequently
        // the robot controller application is restarted. They SHOULD NOT be modified except as absolutely
        // necessary as poorly chosen values might inadvertently compromise safety.
        //----------------------------------------------------------------------------------------------
        msStuckDetectInit = Settings.msStuckDetectInit;
        msStuckDetectInitLoop = Settings.msStuckDetectInitLoop;
        msStuckDetectStart = Settings.msStuckDetectStart;
        msStuckDetectLoop = Settings.msStuckDetectLoop;
        msStuckDetectStop = Settings.msStuckDetectStop;

        robot.hardwareMap = hardwareMap;
        robot.telemetry = telemetry;
        robot.init();
        telemetry.addData("A_B_C_2_Duck", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        // initialize robot
        robot.init_loop();

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        // start robot
        runtime.reset();
        robot.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        telemetry.addData("Auton_Current_Stage ", currentStage);
        robot.loop();


        // check stage and do what's appropriate
        if (currentStage == stage._unknown) {
            currentStage = stage._00_preStart;
        }

        // delay until covering the line
        /*if (runtime.seconds() < 22) {
            return;
        }*/

        if (currentStage == stage._00_preStart) {
            currentStage = stage._10_Drive_Out;
        }

        if (currentStage == stage._10_Drive_Out) {
            robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED, 0, -4);
            currentStage = stage._20_Turn_To_Carousel;
        }

        if (currentStage == stage._20_Turn_To_Carousel) {
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdTurnByGyro(-AUTO_TURN_SPEED, AUTO_TURN_SPEED, -40);
                currentStage = stage._30_Drive_To_Carousel;
            }
        }
        if (currentStage == stage._30_Drive_To_Carousel){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdDriveByGyro(AUTO_DRIVE_SLOW_SPEED, -45, 3.5);
                currentStage = stage._40_Caro_Start;
            }

        }

        if (currentStage == stage._40_Caro_Start){
            if (robot.driveTrain.getcmdComplete()) {
                robot.carousel.cmdCarouselRun_Auton_BLUE();
                runtime.reset();
                currentStage = stage._45_Back_Up;
            }
        }
        if (currentStage == stage._45_Back_Up){
            if (runtime.milliseconds() > 4500) {
                robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED, -45, -0.5);
                currentStage = stage._50_Turn;
            }

        }
        if (currentStage == stage._50_Turn){
            if (robot.driveTrain.getcmdComplete())  {
                //robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED,0,-40);
                robot.driveTrain.cmdTurnByGyro(AUTO_TURN_SPEED, -AUTO_TURN_SPEED, 0);
                currentStage = stage._60_Back_Up;
            }


        }
        if (currentStage == stage._60_Back_Up){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED, 0, -30);
                robot.delivery.cmdDeliveryRun_HIGH();
                currentStage = stage._70_Turn;
            }
        }
        if (currentStage == stage._70_Turn){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdTurnByGyro(-AUTO_TURN_SPEED, AUTO_TURN_SPEED, -90);
                currentStage = stage._80_Back_Up;
            }
        }
        if (currentStage == stage._80_Back_Up){
            if (robot.driveTrain.getcmdComplete()){
                robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED, -90, -32.5);
                currentStage = stage._90_Deliver;
            }
        }
        if(currentStage == stage._90_Deliver){
            if(robot.driveTrain.getcmdComplete()){
                robot.delivery.cmdDeliveryRun_DROP();
                runtime.reset();
                currentStage = stage._100_Close;
            }
        }
        if(currentStage == stage._100_Close){
            if (runtime.milliseconds() > 1000) {
                robot.delivery.cmdDeliveryRun_CLOSE();
                currentStage = stage._110_Drive_Out;
            }
        }
        if(currentStage == stage._110_Drive_Out){
            robot.driveTrain.cmdDriveByGyro(AUTO_DRIVE_NORMAL_SPEED, -90, 32.5);
            robot.delivery.cmdDeliveryRun_CARRY();
            currentStage = stage._120_Turn;
        }

        if (currentStage == stage._120_Turn){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdTurnByGyro(-AUTO_TURN_SPEED, AUTO_TURN_SPEED, 150);
                currentStage = stage._130_Back_Up;
            }
        }

        if(currentStage == stage._130_Back_Up){
            if(robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_NORMAL_SPEED, 165, -20);
                currentStage = stage._135_Straighten;
            }
        }
        if (currentStage == stage._135_Straighten){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdTurnByGyro(AUTO_TURN_SPEED, -AUTO_TURN_SPEED, 178);
                currentStage = stage._140_End;
            }
        }

        if (currentStage == stage._140_End){
            if (robot.driveTrain.getcmdComplete()) {
                robot.stop();

            }


        }

    }  //  loop

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        robot.stop();
    }

    private enum stage {
        _unknown,
        _00_preStart,
        _10_Drive_Out,
        _20_Turn_To_Carousel,
        _30_Drive_To_Carousel,
        _40_Caro_Start,
        _45_Back_Up,
        _50_Turn,
        _60_Back_Up,
        _70_Turn,
        _80_Back_Up,
        _90_Deliver,
        _100_Close,
        _110_Drive_Out,
        _120_Turn,
        _130_Back_Up,
        _135_Straighten,
        _140_End


    }
}
