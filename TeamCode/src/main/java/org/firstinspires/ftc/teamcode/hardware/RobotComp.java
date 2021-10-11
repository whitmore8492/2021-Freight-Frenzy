package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotComp extends BaseHardware{
    public DriveTrain driveTrain = new DriveTrain();
    public Sweeper sweeper = new Sweeper();
    @Override
    public void init (){
        // Must set Hardware Map and telemetry before calling init
        driveTrain.hardwareMap = this.hardwareMap;
        driveTrain.telemetry = this.telemetry;
        driveTrain.init();

        sweeper.hardwareMap = this.hardwareMap;
        sweeper.telemetry = this.telemetry;
        sweeper.init();
    }
    @Override
    public void init_loop() {
        driveTrain.init_loop();
        sweeper.init_loop();
    }
    @Override
    public void start () {
        driveTrain.start();
        sweeper.start();
    }
    @Override
    public void loop() {
        driveTrain.loop();
        sweeper.loop();
    }

    @Override
    public void stop () {
        driveTrain.stop();
        sweeper.stop();
    }
}
