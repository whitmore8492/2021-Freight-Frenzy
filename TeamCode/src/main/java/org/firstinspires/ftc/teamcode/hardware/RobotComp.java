package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotComp extends BaseHardware{
    public DriveTrain driveTrain = new DriveTrain();
    public Sweeper sweeper = new Sweeper();
    public Carousel carousel = new Carousel();
    public Arm_Rotator arm_rotator = new Arm_Rotator();
    public Delivery delivery = new Delivery();

    private static int TrackUpLimit = 400;
    private static int TrackLowLimit = 400;

    @Override
    public void init (){
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
    }
    @Override
    public void init_loop() {
        driveTrain.init_loop();
        sweeper.init_loop();
        carousel.init_loop();
        arm_rotator.init_loop();
        delivery.init_loop();
    }
    @Override
    public void start () {
        driveTrain.start();
        sweeper.start();
        carousel.start();
        arm_rotator.start();
        delivery.start();
    }
    @Override
    public void loop() {
        driveTrain.loop();
        sweeper.loop();
        carousel.loop();
        arm_rotator.loop();
        delivery.loop();


        if (delivery.getTrackMotorPosition() < TrackLowLimit) {
            arm_rotator.cmdArmRotatePos_CAROUSEL();
        }
        else if (delivery.getTrackMotorPosition() > TrackLowLimit){
            arm_rotator.cmdCarouselRun_INTAKE();
        }



    }

    @Override
    public void stop () {
        driveTrain.stop();
        sweeper.stop();
        carousel.stop();
        arm_rotator.stop();
        delivery.stop();
    }
}
