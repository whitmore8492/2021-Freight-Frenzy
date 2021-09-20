package org.firstinspires.ftc.teamcode.common;

public class CommonLogic extends Object {

    //*********************************************************************************************
    public static double CapMotorPower(double MotorPower, double negCapValue, double posCapValue) {
        // logic to cap the motor power between a good range
        double retValue = MotorPower;

        if (MotorPower < negCapValue) {
            retValue = negCapValue;
        }

        if (MotorPower > posCapValue) {
            retValue = posCapValue;
        }

        return retValue;
    }

    //*********************************************************************************************
    public static double joyStickMath(double joyValue) {
        int sign = 1;
        double retValue = 0;
        if (joyValue < 0) {
            sign = -1;
        }
        return Math.abs(Math.pow(joyValue, 2)) * sign;
    }

    //*********************************************************************************************
    public static boolean inRange(int value, int targetValue, int tol) {
        // function to tell if an encoder is within tolerance

        boolean retValue = false;

        if ((value >= (targetValue - tol)) && (value <= (targetValue + tol))) {
            retValue = true;
        }

        return retValue;

    }
    //*********************************************************************************************

    public static boolean indexCheck(int value, int low, int high) {

        return (value >= low && value <= high);
    }

    // returns true only on the previous state was false and the current state is true
    // AKA a button is freshly pressed do something.   If it as already been pressed
    // it is not worth doing again.
    public static boolean oneShot(boolean currState, boolean prevState) {

        return (prevState == false && currState == true);
    }

}
