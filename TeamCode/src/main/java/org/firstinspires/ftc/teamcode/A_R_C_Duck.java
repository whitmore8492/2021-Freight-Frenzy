package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Settings;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.RobotComp;

@Autonomous(name = "A_R_C_Duck", group = "Auton")
// @Autonomous(...) is the other common choice

public class A_R_C_Duck extends OpMode {

    //RobotComp robot = new RobotComp();
    RobotComp robot = new RobotComp();




    private stage currentStage = stage._unknown;
    // declare auton power variables
    private double AUTO_DRIVE_TURBO_SPEED = DriveTrain.DRIVETRAIN_TURBOSPEED;
    private double AUTO_DRIVE_SLOW_SPEED = DriveTrain.DRIVETRAIN_SLOWSPEED;
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
        telemetry.addData("A_R_C_Duck", "Initialized");
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
            robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED, 0, -25);
            currentStage = stage._20_Turn_To_Carousel;
        }

        if (currentStage == stage._20_Turn_To_Carousel) {
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdTurnByGyro(AUTO_TURN_SPEED, -AUTO_TURN_SPEED, 45);
                currentStage = stage._30_Drive_To_Carousel;
            }
        }
        if (currentStage == stage._30_Drive_To_Carousel){
            if (robot.driveTrain.getcmdComplete()) {
                robot.driveTrain.cmdDriveByGyro(AUTO_DRIVE_SLOW_SPEED, 45, 20);
                currentStage = stage._40_Caro_Start;
            }

        }

        if (currentStage == stage._40_Caro_Start){
            if (robot.driveTrain.getcmdComplete()) {
                robot.carousel.cmdCarouselRun_RED();
                runtime.reset();
                currentStage = stage._50_Back_Up;
            }
        }
        if (currentStage == stage._50_Back_Up){
            if (runtime.milliseconds() > 5000) {
                robot.driveTrain.cmdDriveByGyro(-AUTO_DRIVE_SLOW_SPEED,0,-10);
                currentStage = stage._60_Turn;
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
        _50_Back_Up,
        _60_Turn,
        _100_End


    }
}
