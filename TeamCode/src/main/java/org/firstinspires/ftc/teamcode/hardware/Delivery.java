package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.common.Settings;

import java.net.PortUnreachableException;

public class Delivery extends BaseHardware {

    private static final String TAGDelivery = "8492-Delivery";
    public static final int TRACK_ticsPerRev = Settings.REV_HD_40_MOTOR_TICKS_PER_REV;
    public static final double TRACK_wheelDistPerRev = 2 * 3.14159;
    public static final double TRACK_gearRatio = 40.0 / 40.0;
    public static final double TRACK_ticsPerInch = TRACK_ticsPerRev / TRACK_wheelDistPerRev / TRACK_gearRatio;
    public static final double TRACK_spin = 1.25 * 15 * 3.14159 * TRACK_ticsPerInch;
    public static final double TRACK_SPEED = .85;

    public static final int ROTATE_ticsPerRev = Settings.REV_HD_40_MOTOR_TICKS_PER_REV;
    public static final double ROTATE_wheelDistPerRev = 2 * 3.14159;
    public static final double ROTATE_gearRatio = 40.0 / 40.0;
    public static final double ROTATE_ticsPerInch = ROTATE_ticsPerRev / ROTATE_wheelDistPerRev / ROTATE_gearRatio;
    public static final double ROTATE_spin = 1.25 * 15 * 3.14159 * ROTATE_ticsPerInch;
    public static final double ROTATE_SPEED = .30;

    public static final int TCARRY_POS = 0;
    public static final int TLOAD_POS = 1027;
    public static final int TLOW_POS = -1500;
    public static final int TSPIN_POS = -1630;
    public static final int TMIDDLE_POS = -1830;
    public static final int THIGH_POS = -2200;
    public static final int HARD_STOP = 1200;

    public static final int RRECEIVE_POS = 1;
    public static final int RCARRY_POS = 1;
    public static final int RSAFETY_POS = 1;
    public static final int RDROP_POS = 0;

    public static final double OCATCH_POS = 0.5;
    public static final double OCLOSE_POS = 1;
    public static final double ODROP_POS = 0.5;

    private Delivery.Mode DELIVERY_mode_Current = Mode.START;
    private Delivery.Mode DELIVERY_mode_Prv = Mode.START;

    private DcMotor Trackmotor = null;
    private Servo Rotateservo = null;
    private Servo Openservo = null;

    private RevColorSensorV3 BoxSense = null;
    // private RevColorSensorV3 BoxSense = null;
    private static final double DistanceSenseThresh = 2.1; // Distance measured in cm
    private boolean BoxCapture = false;
    private int DetectCounter = 0;
    private static final int DetectThresh = 20;

    private ElapsedTime runtime = new ElapsedTime();
    private static final int WaitTime = 1000;
    private boolean MoveRotateComplete = false;

    public void init() {
        Trackmotor = hardwareMap.dcMotor.get("TrackM");
        Rotateservo = hardwareMap.servo.get("RotateS");
        Openservo = hardwareMap.servo.get("OpenS");

        BoxSense = hardwareMap.get(RevColorSensorV3.class, "BoxSense");

        Trackmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Trackmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Rotateservo.setPosition(RRECEIVE_POS);

        Openservo.setPosition(OCLOSE_POS); // we need to set initial position

        if (Trackmotor == null) {
            telemetry.log().add("Trackmotor is null...");
        }
        if (Rotateservo == null) {
            telemetry.log().add("Rotateservo is null...");
        }
    }

    public void init_loop() {
       // telemetry.log().add("BoxSense " + BoxSense.getDistance(DistanceUnit.CM));
//        telemetry.log().add("BoxSense " + BoxSense.getNormalizedColors());
    }

    public void start() {
        runtime.reset();
    }

    public void loop() {
        //telemetry.log().add("BoxSense " + BoxSense.getDistance(DistanceUnit.CM));



        if (DELIVERY_mode_Current == Mode.START) {
            // MoveRotateMotor(RRECEIVE_POS);
            // MoveTrackMotor(TCARRY_POS);
            Rotateservo.setPosition(RRECEIVE_POS);
            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
            telemetry.log().add("Trackmotor tic count " + Trackmotor.getCurrentPosition());
            telemetry.log().add("Rotateservo tic count " + Rotateservo.getPosition());
        }

        if (DELIVERY_mode_Current == Mode.DROP) {
            Openservo.setPosition(ODROP_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }
        if (DELIVERY_mode_Current == Mode.CLOSE) {
            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (DELIVERY_mode_Current == Mode.RECEIVE) {
            if (BoxSense.getDistance(DistanceUnit.CM) <= DistanceSenseThresh){
                DetectCounter++;
                if (DetectCounter >= DetectThresh) {
                    BoxCapture = true;
                }

            }else {
                DetectCounter = 0;
                BoxCapture = false;

            }
            if (DELIVERY_mode_Prv != DELIVERY_mode_Current) {
                if (Rotateservo.getPosition() == RRECEIVE_POS) {
                    MoveTrackMotor(TLOAD_POS);
                }

            }

            MoveRotateServo(RRECEIVE_POS);
            if (MoveRotateComplete && (runtime.milliseconds() > WaitTime)) {
                MoveTrackMotor(TLOAD_POS);
                MoveRotateComplete = false;
            }
            if (Trackmotor.getCurrentPosition() >= TLOAD_POS) {
                Openservo.setPosition(OCATCH_POS);
            }

            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (DELIVERY_mode_Current == Mode.CARRY) {
            if (DELIVERY_mode_Prv != DELIVERY_mode_Current) {
                if (Rotateservo.getPosition() == RCARRY_POS) {
                    MoveTrackMotor(TCARRY_POS);
                }
            }

            MoveRotateServo(RCARRY_POS);
            if (MoveRotateComplete && (runtime.milliseconds() > WaitTime)) {
                MoveTrackMotor(TCARRY_POS);
                MoveRotateComplete = false;
            }
            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (DELIVERY_mode_Current == Mode.LOWER) {
            if (DELIVERY_mode_Prv != DELIVERY_mode_Current) {
                if (Rotateservo.getPosition() == RDROP_POS) {
                    MoveTrackMotor(TLOW_POS);
                }
            }

            MoveRotateServo(RDROP_POS);
            if (MoveRotateComplete && (runtime.milliseconds() > WaitTime)) {
                MoveTrackMotor(TLOW_POS);
                MoveRotateComplete = false;
            }
            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (DELIVERY_mode_Current == Mode.MIDDLE) {
            if (DELIVERY_mode_Prv != DELIVERY_mode_Current) {
                if (Rotateservo.getPosition() == RDROP_POS) {
                    MoveTrackMotor(TMIDDLE_POS);
                }
            }

            MoveRotateServo(RDROP_POS);
            if (MoveRotateComplete && (runtime.milliseconds() > WaitTime)) {
                MoveTrackMotor(TMIDDLE_POS);
                MoveRotateComplete = false;
            }

            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (DELIVERY_mode_Current == Mode.HIGH) {
            if (DELIVERY_mode_Prv != DELIVERY_mode_Current) {
                if (Rotateservo.getPosition() == RDROP_POS) {
                    MoveTrackMotor(THIGH_POS);
                }
            }

            MoveRotateServo(RDROP_POS);
            if (MoveRotateComplete && (runtime.milliseconds() > WaitTime)) {
                MoveTrackMotor(THIGH_POS);
                MoveRotateComplete = false;
            }
            Openservo.setPosition(OCLOSE_POS);
            RobotLog.aa(TAGDelivery, " Delivery mode " + DELIVERY_mode_Current);
        }

        if (Trackmotor.getCurrentPosition() == Trackmotor.getTargetPosition()) {
            Trackmotor.setPower(0);
        }

        if (DELIVERY_mode_Current == Mode.STOPPED) {
            Trackmotor.setPower(0);
            RobotLog.aa(TAGDelivery, " Carousel mode " + DELIVERY_mode_Current);
        }

        DELIVERY_mode_Prv = DELIVERY_mode_Current;

    }

    public void stop() {

    }

    public void cmdDeliveryRun_RECEIVE() {
        DELIVERY_mode_Current = Mode.RECEIVE;
    }

    public void cmdDeliveryRun_CARRY() {
        DELIVERY_mode_Current = Mode.CARRY;
    }

    public void cmdDeliveryRun_LOWER() {
        DELIVERY_mode_Current = Mode.LOWER;
    }

    public void cmdDeliveryRun_MIDDLE() {
        DELIVERY_mode_Current = Mode.MIDDLE;
    }

    public void cmdDeliveryRun_HIGH() {
        DELIVERY_mode_Current = Mode.HIGH;
    }

    public void cmdDeliveryRun_STOP() {
        DELIVERY_mode_Current = Mode.STOPPED;
    }

    public void cmdDeliveryRun_DROP() {
        DELIVERY_mode_Current = Mode.DROP;
    }

    public void cmdDeliveryRun_CLOSE() {
        DELIVERY_mode_Current = Mode.CLOSE;
    }

    public String cmdCurrentMode() {
        return DELIVERY_mode_Current.name();
    }
    public boolean cmdIsBoxFull() {
        return BoxCapture;
    }

    public enum Mode {
        RECEIVE,
        CARRY,
        LOWER,
        MIDDLE,
        HIGH,
        START,
        DROP,
        CLOSE,
        STOPPED
    }

    public double GetMovePowerLevel(int TargetPos) {
        if (Trackmotor.getCurrentPosition() < TargetPos) {
            return 1;

        } else if (Trackmotor.getCurrentPosition() > TargetPos) {
            return -1;
        } else {
            return 0;
        }
    }

    public int getTrackMotorPosition() {
        return Trackmotor.getCurrentPosition();
    }

    private void MoveTrackMotor(int Position) {
        Trackmotor.setTargetPosition(Position);
        Trackmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Trackmotor.setPower(GetMovePowerLevel(Position) * TRACK_SPEED);
    }

    private void MoveRotateServo(double Position) {
        //auto pivit if track above position x, do not rotate box**
        if ((Rotateservo.getPosition() != Position)) {
            MoveTrackMotor(TSPIN_POS);
            if (Trackmotor.getCurrentPosition() <= TSPIN_POS) {
                runtime.reset();
                Rotateservo.setPosition(Position);
                MoveRotateComplete = true;


            }
            //Insert wait before exiting
        }

    }
}
