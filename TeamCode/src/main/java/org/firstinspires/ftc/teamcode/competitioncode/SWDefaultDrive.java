package org.firstinspires.ftc.teamcode.competitioncode;

/**
 * Main TeleOP mode, currently (and probably forever will) uses Hardware12772.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name="Main Drive with Assists", group="TeamCode")
//@Disabled         //Enables or disables such OpMode (hide or show on Driver Station OpMode List)

public class SWDefaultDrive extends LinearOpMode {

    Hardware12772 r = new Hardware12772(); //Use the shared hardware and function code.

    @Override //Does anyone know what this is or what it does?
    public void runOpMode() {
        r.init(hardwareMap, false); //initialization for non-autonomous code. NO SHAKES ALLOWED >:(
        r.clawsPOS = 0.5;  //Claws are set to an extended position
//        r.initClawServosPOS(r.clawsPOS); //"When you try your best but you don't succeed..."
        //Can't get r.initClawServosPOS to work, so manually set offsets below. See method for details on not working.
        r.leftClawOffset = 0.1;
        r.rightClawOffset = 1.0;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        r.runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Control drive motors
            r.setDriveSpeedWithButtons(gamepad1.a,gamepad1.b,gamepad1.x);
            r.povDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, r.driveSpeedStick);

            //Control Arm power and/or position
/*            r.setArmPositionJoystick(gamepad1.right_stick_y,gamepad1.right_stick_x, //Version from before Xmas break, kept here just in case I broke something
                    r.debounceGamepad1Button(gamepad1.right_stick_button,11),
                    gamepad1.start);
*/            r.setArmPositionJoystick(gamepad1.right_stick_y,  gamepad1.right_stick_x,
                    r.debounce(gamepad1.right_stick_button,1,11),
                    gamepad1.start);

            //Control claw position
            r.setServoPositionTwoButton(gamepad1.left_bumper, gamepad1.right_bumper);

            //All runtime code in Hardware12772
            r.update();

            //BEGIN TELEMETRY SECTION. TELEMETRY WILL NOT WORK IF REFERENCED TO Hardware12772.java FOR SOME REASON!
            //I think its because telemetry is provided by TeleOP library, which only OP mode classes can use.
            telemetry.addData("Status",
                    "Run Time: " + r.runtime.toString()
            );
            telemetry.addData("Drive Speed", r.driveSpeedStick);
            telemetry.addData("Motor Power",
                    " leftDrive: " + r.leftDrive.getPower() +
                           " rightDrive: " + r.rightDrive.getPower() +
                           " Arm: " + r.mainArm.getPower()
            );
            telemetry.addData("Servo POS",
                    " clawPOS: " + r.clawsPOS +
                           " leftClaw: " + r.leftClaw.getPosition() +
                           " rightClaw: " + r.rightClaw.getPosition()
            );
            telemetry.addData("ClawOffsets",
                    "left: " + r.leftClawOffset +
                           " right: " + r.rightClawOffset
            );
            telemetry.addData("Pad1 start&back", gamepad1.start+" "+ gamepad1.back);
            telemetry.addData("Pad2 start&back", gamepad2.start+" "+ gamepad2.back);
            telemetry.update();
        }
    }
}