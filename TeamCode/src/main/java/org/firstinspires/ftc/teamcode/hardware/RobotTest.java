package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotTest extends BaseHardware{

    public DriveTrain driveTrain = new DriveTrain();
    @Override
    public void init (){
        driveTrain.hardwareMap = this.hardwareMap;
        driveTrain.telemetry = this.telemetry;
        driveTrain.init();
    }
    @Override
    public void init_loop() {
        driveTrain.init_loop();
    }
    @Override
    public void start () {
        driveTrain.start();
    }
    @Override
    public void loop() {
        driveTrain.loop();
    }
    @Override
    public void stop () {
        driveTrain.stop();
    }
}
