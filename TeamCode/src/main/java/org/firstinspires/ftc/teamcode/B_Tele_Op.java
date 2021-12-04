//package org.firstinspires.ftc.robotcontroller.external.samples;
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.CommonLogic;
import org.firstinspires.ftc.teamcode.common.Settings;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.RobotComp;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "B_Tele_Op", group = "TeleOp")
//@Disabled
public class B_Tele_Op extends OpMode {
    private static final String TAGTeleop = "8492-Teleop";
    //RobotTest robot = new RobotTest();
    RobotComp robot = new RobotComp();
    //    // Declare OpMode members.
    private boolean gp1_prev_a = false;
    private boolean gp1_prev_b = false;
    private boolean gp1_prev_x = false;
    private boolean gp1_prev_y = false;
    private boolean gp1_prev_right_bumper = false;
    private boolean gp1_prev_left_bumper = false;
    private boolean gp1_prev_dpad_up = false;
    private boolean gp1_prev_dpad_down = false;
    private boolean gp1_prev_dpad_left = false;
    private boolean gp1_prev_dpad_right = false;

    private boolean gp2_prev_a = false;
    private boolean gp2_prev_b = false;
    private boolean gp2_prev_x = false;
    private boolean gp2_prev_y = false;
    private boolean gp2_prev_right_bumper = false;
    private boolean gp2_prev_left_bumper = false;
    private boolean gp2_prev_dpad_up = false;
    private boolean gp2_prev_dpad_down = false;
    private boolean gp2_prev_dpad_left = false;
    private boolean gp2_prev_dpad_right = false;
    private double LeftMotorPower = 0;
    private double RightMotorPower = 0;

    //*********************************************************************************************
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        //----------------------------------------------------------------------------------------------
        // Safety Management
        //
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

        telemetry.addData("Tele_Op_test", "Initialized");

        robot.hardwareMap = hardwareMap;
        robot.telemetry = telemetry;
        robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_NORMALSPEED);
        robot.init();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery


    }

    //*********************************************************************************************
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        robot.init_loop();
    }

    //*********************************************************************************************
    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        Runtime.getRuntime();
        robot.start();
        robot.driveTrain.setMotorMode_RUN_WITHOUT_ENCODER();
    }

    //*********************************************************************************************
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        robot.loop();
        write2Log();

        //***********   Gamepad 1 controls ********
        robot.driveTrain.cmdTeleOp(CommonLogic.joyStickMath(-gamepad1.left_stick_y),
                CommonLogic.joyStickMath(-gamepad1.right_stick_y));


        //***********   Pushers
        if (CommonLogic.oneShot(gamepad1.a, gp1_prev_a)) {
            //robot.subPushers.cmdMoveAllDown();
            robot.carousel.cmdCarouselRun_BLUE();
        }

        if (CommonLogic.oneShot(gamepad1.b, gp1_prev_b)) {
            //robot.subPushers.cmdMoveAllUp();
            robot.carousel.cmdCarouselSTOPPED();
        }

        // Bumpers high and lower Powers for the wheels,
        /*if (CommonLogic.oneShot(gamepad1.left_bumper, gp1_prev_left_bumper)) {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_TURBOSPEED);
        }*/
        if ((gamepad1.left_trigger > .8) && (gamepad1.right_trigger < .8)) {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_SLOWSPEED);
            RobotLog.aa(TAGTeleop, "GamepadLB: " + gamepad1.left_bumper);
            telemetry.addData(TAGTeleop, "GamepadLB: " + gamepad1.left_bumper);
        } else if ((gamepad1.left_trigger < .8) && (gamepad1.right_trigger > .8)) {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_TURBOSPEED);
            RobotLog.aa(TAGTeleop, "GamepadRB: " + gamepad1.right_bumper);
            telemetry.addData(TAGTeleop, "GamepadRB: " + gamepad1.right_bumper);

        } else {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_NORMALSPEED);
        }

        /*if (CommonLogic.oneShot(gamepad1.right_bumper, gp1_prev_right_bumper)) {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_SLOWSPEED);
        }*/
        /*if (gamepad1.right_bumper) {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_TURBOSPEED);
            RobotLog.aa(TAGTeleop,"GamepadRB: " + gamepad1.right_bumper);
            telemetry.addData (TAGTeleop, "GamepadRB: " + gamepad1.right_bumper);
        } else if(gamepad1.right_bumper == false)
        {
            robot.driveTrain.setMaxPower(DriveTrain.DRIVETRAIN_NORMALSPEED);
        }
       */
        //***********  Grabbers
        if (CommonLogic.oneShot(gamepad1.dpad_right, gp1_prev_dpad_right)) {
            //if (RBTChassis.subGrabbers.getIsUpRight()) {
            //robot.subGrabbers.cmdMoveDownRight();
            //}
        }

        if (CommonLogic.oneShot(gamepad1.dpad_up, gp1_prev_dpad_up)) {
            // if (RBTChassis.subGrabbers.getIsDownRight()) {
            //robot.subGrabbers.cmdMoveUpRight();
            //}

        }

        if (CommonLogic.oneShot(gamepad1.dpad_left, gp1_prev_dpad_left)) {
            //if (robot.subGrabbers.getIsDownLeft()) {
            //    robot.subGrabbers.cmdMoveUpLeft();
            //}
            robot.carousel.cmdCarouselSTOPPED();
            robot.delivery.cmdDeliveryRun_STOP();
        }

        if (CommonLogic.oneShot(gamepad1.dpad_down, gp1_prev_dpad_down)) {
            //if (robot.subGrabbers.getIsUpLeft()) {
            //    robot.subGrabbers.cmdMoveDownLeft();
            //}
        }

        //***********   Gamepad 2 controls ********

        // Bumpers close and open the gripper
        if (CommonLogic.oneShot(gamepad2.left_bumper, gp2_prev_left_bumper)) {
            //if (robot.subGripper.getIsOpen()) {
            //    robot.subGripper.cmd_close();
            //}
            robot.carousel.cmdCarouselRun_BLUE();

            RobotLog.aa(TAGTeleop, " gp2_prev_left_bumper : " + gp2_prev_left_bumper);

        }


        if (CommonLogic.oneShot(gamepad2.right_bumper, gp2_prev_right_bumper)) {
            //if (robot.subGripper.getIsClosed()) {
            //    robot.subGripper.cmd_open();
            // }

            if (robot.sweeper.cmdCurrentMode() == "STOPPED") {
                robot.sweeper.cmdSweeperRun();
            } else {
                robot.sweeper.cmdSweeperSTOPPED();
            }
        }


        if (CommonLogic.oneShot(gamepad2.a, gp2_prev_a)) {
            //robot.subExtender.decPositionIndex();
            robot.delivery.cmdDeliveryRun_RECEIVE();
        }

        if (CommonLogic.oneShot(gamepad2.b, gp2_prev_b)) {
            //robot.subLeg.pick();
            robot.delivery.cmdDeliveryRun_TSHARED_LOW();
        }

        if (CommonLogic.oneShot(gamepad2.y, gp2_prev_y)) {
            //robot.subExtender.incPositionIndex();
            robot.delivery.cmdDeliveryRun_HIGH();
        }

        if (CommonLogic.oneShot(gamepad2.x, gp2_prev_x)) {
            //robot.subLeg.place();
            robot.delivery.cmdDeliveryRun_TSHARED_HIGH();
        }

        if (Math.abs(gamepad2.left_stick_y) > Settings.JOYSTICK_DEADBAND_STICK) {
            //robot.subLifter.stickControl(-gamepad2.left_stick_y);
            //robot.capper.cmdTeleOp((gamepad2.left_stick_y * 0.5) + (gamepad2.right_stick_y * 0.5));

        }
        robot.capper.cmdTeleOp(gamepad2.left_stick_y);
        if (Math.abs(gamepad2.right_stick_y) > Settings.JOYSTICK_DEADBAND_STICK) {
            //robot.subLifter.stickControl(-gamepad2.left_stick_y);
           // robot.capper.cmdTeleOp(gamepad2.right_stick_y * .5);
        }


        if (CommonLogic.oneShot(gamepad2.dpad_up, gp2_prev_dpad_up)) {
            //robot.subLifter.incPositionIndex();
            robot.capper.cmdpark();
        }

        if (CommonLogic.oneShot(gamepad2.dpad_down, gp2_prev_dpad_down)) {
            //robot.subLifter.decPositionIndex();
            robot.capper.cmdCollect();
        }
        if (CommonLogic.oneShot(gamepad2.dpad_right, gp2_prev_dpad_right)) {
            //robot.subLifter.decPositionIndex();
            robot.sweeper.cmdSweeperSTOPPED();
            robot.delivery.cmdDeliveryRun_CARRY();
        }

        if (CommonLogic.oneShot(gamepad2.dpad_left, gp2_prev_dpad_left)) {
            //robot.subLifter.decPositionIndex();
            robot.carousel.cmdCarouselRun_BLUE();
        }

        if (gamepad2.right_trigger > .8) {
            robot.delivery.cmdDeliveryRun_DROP();
        }
        if ((gamepad2.right_trigger < .79) && (gamepad2.right_trigger > .01)) {
            robot.delivery.cmdDeliveryRun_CLOSE();
            //robot.cmdReturnTo_Carry();
        }
        if (gamepad2.left_trigger > .8) {
            robot.capper.cmdPlungerRelease();
        }
        if ((gamepad2.left_trigger < .79) && (gamepad2.left_trigger > .01)) {
            robot.capper.cmdPlungerGrab();
        }

        // Update the previous status for gamepad1
        gp1_prev_a = gamepad1.a;
        gp1_prev_b = gamepad1.b;
        gp1_prev_x = gamepad1.x;
        gp1_prev_y = gamepad1.y;
        gp1_prev_left_bumper = gamepad1.left_bumper;
        gp1_prev_right_bumper = gamepad1.right_bumper;
        gp1_prev_dpad_down = gamepad1.dpad_down;
        gp1_prev_dpad_left = gamepad1.dpad_left;
        gp1_prev_dpad_up = gamepad1.dpad_up;
        gp1_prev_dpad_right = gamepad1.dpad_right;

        // Update the previous status for gamepad 2
        gp2_prev_a = gamepad2.a;
        gp2_prev_b = gamepad2.b;
        gp2_prev_x = gamepad2.x;
        gp2_prev_y = gamepad2.y;
        gp2_prev_left_bumper = gamepad2.left_bumper;
        gp2_prev_right_bumper = gamepad2.right_bumper;
        gp2_prev_dpad_down = gamepad2.dpad_down;
        gp2_prev_dpad_left = gamepad2.dpad_left;
        gp2_prev_dpad_up = gamepad2.dpad_up;
        gp2_prev_dpad_right = gamepad2.dpad_right;


    }

    //*********************************************************************************************
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

        robot.stop();

    }

    //*********************************************************************************************
    private void write2Log() {

//
//    RobotLog.aa(TAGTeleop, " gp1_prev_a : " + gp1_prev_a);
//    RobotLog.aa(TAGTeleop, " gp1_prev_b : " + gp1_prev_b);
//    RobotLog.aa(TAGTeleop, " gp1_prev_x : " + gp1_prev_x);
//    RobotLog.aa(TAGTeleop, " gp1_prev_y : " + gp1_prev_y);
//    RobotLog.aa(TAGTeleop, " gp1_prev_right_bumper : " + gp1_prev_right_bumper);
//   RobotLog.aa(TAGTeleop, " gp1_prev_left_bumper : " + gp1_prev_left_bumper);
//    RobotLog.aa(TAGTeleop, " gp1_prev_dpad_up : " + gp1_prev_dpad_up);
//    RobotLog.aa(TAGTeleop, " gp1_prev_dpad_down : " + gp1_prev_dpad_down);
//    RobotLog.aa(TAGTeleop, " gp1_prev_dpad_left : " + gp1_prev_dpad_left);
//    RobotLog.aa(TAGTeleop, " gp1_prev_dpad_right : " + gp1_prev_dpad_right);
//
//    RobotLog.aa(TAGTeleop, " gp2_prev_a : " + gp2_prev_a);
//    RobotLog.aa(TAGTeleop, " gp2_prev_b : " + gp2_prev_b);
//    RobotLog.aa(TAGTeleop, " gp2_prev_x : " + gp2_prev_x);
//    RobotLog.aa(TAGTeleop, " gp2_prev_y : " + gp2_prev_y);
//    RobotLog.aa(TAGTeleop, " gp2_prev_right_bumper : " + gp2_prev_right_bumper);
//    RobotLog.aa(TAGTeleop, " gp2_prev_left_bumper : " + gp2_prev_left_bumper);
        RobotLog.aa(TAGTeleop, " gp2_prev_dpad_up : " + gp2_prev_dpad_up);
        RobotLog.aa(TAGTeleop, " gp2_prev_dpad_down : " + gp2_prev_dpad_down);
//    RobotLog.aa(TAGTeleop, " gp2_prev_dpad_left : " + gp2_prev_dpad_left);
//    RobotLog.aa(TAGTeleop, " gp2_prev_dpad_right : " + gp2_prev_dpad_right);
//
//

    }

}

