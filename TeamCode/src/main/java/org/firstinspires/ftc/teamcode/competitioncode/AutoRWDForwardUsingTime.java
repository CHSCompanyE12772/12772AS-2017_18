package org.firstinspires.ftc.teamcode.competitioncode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled; // Leave this line here even when not used, please

/**
 * Used this code when crisis hit and miracles fail, works by being painfully simple.
 */

@Autonomous(name = "AutoRWD Move using Time", group = "RWD")
//@Disabled                            //Enables or disables such OpMode (hide or show on Driver Station OpMode List)
public class AutoRWDForwardUsingTime extends LinearOpMode {

    Hardware_RWD_RearWheelDrive r = new Hardware_RWD_RearWheelDrive(); //Use the shared hardware and function code.
    General12772 g = new General12772(); //Use the shared general robot code.

    //Distance Variables
    private int timeToMove = 1300; //Given in milliseconds. Change this to change distance robot moves.

    @Override
    public void runOpMode() {
        r.init(hardwareMap, false);  //should have named 'isAuto' better, this needs to be false for this OP mode.
        r.mainArmPower = 0;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        r.runtime.reset();
        r.setDriveSpeed(-r.driveSpeedMed); //FIXME: For some reason, this is reversed. IDK why.
        r.update();
        sleep(timeToMove);
        r.setDriveSpeed(0.0);
    }
}