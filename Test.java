package org.firstinspires.ftc.robotcontroller.external.samples;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Test", group="Robot")
/*@Disabled*/
public class Test extends LinearOpMode {
    /*
        REMEMBER THE MOTION IS REVERSED SO FORWARDS = BACKWARDS
        SO START ROBOT BACKWARDS
        This is a test
    */
    
    /* Declare OpMode members. */
    private DcMotor         leftFront           = null;
    private DcMotor         rightFront          = null;
    private DcMotor         leftBack            = null;
    private DcMotor         rightBack           = null;
    
    
    private DcMotor         intake              = null;
    private DcMotorEx       launcher            = null;
    private DcMotor         transfer            = null;
    
    private ElapsedTime     runtime             = new ElapsedTime();
    private ElapsedTime     gametime            = new ElapsedTime();


    static final double     FORWARD_SPEED       = 0.5;
    static final double     REVERSE_SPEED       = -0.5;
    static final double     STOP                = 0.0;
    static final double     FIRE_SPEED_HIGH     = 0.95;
    static final double     FIRE_SPEED_LOW      = 0.85;
    static final double     INTAKE_SPEED        = -1.0;
    static final double     TRANSFER_SPEED      = -1.0;
    static final boolean    NECTAR              = true;
    static final boolean    POLLEN              = false;
    static final double     mainWheelTicksPerRev= 28;   // Bare REV-41-1291 counts/rev
    static final double     driveWheelDiameter  = 2.95; // 75mm REV mecanum wheel (use 3.94 if 100mm)
    static final double     driveWheelRatioNom  = 12;   // Driven gear/sprocket (wheel side)
    static final double     driveWheelRatioDom  = 1;    // Driving gear/sprocket (motor side)
    static final double     robotTrackWidth     = 14.0; 


        // Define Functions

    /*public void waitSeconds(double seconds) {
        sleep(seconds*1000);
        }*/
    public int calculateTicksPerInch(double Nom, double Dom, double ticksPerRev, double wheelDiameter, double inches) {
        double ticksPerInch = (ticksPerRev * (Nom / Dom)) / (wheelDiameter * Math.PI);
        int targetTicks = (int)(inches * ticksPerInch);
        return targetTicks;
        }
    public void RunUsingEn(){
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    public void STOP(){
        leftFront.setPower(STOP);
        rightFront.setPower(STOP);
        leftBack.setPower(STOP);
        rightBack.setPower(STOP);
        }
    public void resetEncoders(){
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    public void RunToPosition() {
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
    public void driveForward(double seconds) {
    
        leftFront.setPower(FORWARD_SPEED);
        rightFront.setPower(FORWARD_SPEED);
        leftBack.setPower(FORWARD_SPEED);
        rightBack.setPower(FORWARD_SPEED);

        runtime.reset();

    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Driving Forward: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        leftFront.setPower(STOP);
        rightFront.setPower(STOP);
        leftBack.setPower(STOP);
        rightBack.setPower(STOP);
    }
    public void tickTurnRight(double degrees, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        double turningInches = (robotTrackWidth * Math.PI) * (degrees / 360.0);
        int targetTicks = calculateTicksPerInch(Nom, Dom, ticksPerRev, wheelDiameter, turningInches);

        // 2. Reset baseline
        resetEncoders();

        leftFront.setTargetPosition(targetTicks);
        rightFront.setTargetPosition(-targetTicks);
        leftBack.setTargetPosition(targetTicks);
        rightBack.setTargetPosition(-targetTicks);

        RunToPosition();
        
        leftFront.setPower(FORWARD_SPEED);
        rightFront.setPower(FORWARD_SPEED);
        leftBack.setPower(FORWARD_SPEED);
        rightBack.setPower(FORWARD_SPEED);

        
        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            telemetry.addData("Turning Right to Target Ticks", targetTicks);
            telemetry.update();
        }
        
        STOP();
        RunUsingEn();
    }
    public void tickTurnLeft(double degrees, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        double turningInches = (robotTrackWidth * Math.PI) * (degrees / 360.0);
        int targetTicks = calculateTicksPerInch(Nom, Dom, ticksPerRev, wheelDiameter, turningInches);

        // 2. Reset baseline
        resetEncoders();

        leftFront.setTargetPosition(-targetTicks);
        rightFront.setTargetPosition(targetTicks);
        leftBack.setTargetPosition(-targetTicks);
        rightBack.setTargetPosition(targetTicks);

        RunToPosition();
        
        leftFront.setPower(FORWARD_SPEED);
        rightFront.setPower(FORWARD_SPEED);
        leftBack.setPower(FORWARD_SPEED);
        rightBack.setPower(FORWARD_SPEED);

        
        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            telemetry.addData("Turning Right to Target Ticks", targetTicks);
            telemetry.update();
        }
        
        STOP();
        RunUsingEn();
    }
    public void tickDriveForward(double inches, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        // Step 1: Calculate your conversion factor and targets
        int targetTicks = calculateTicksPerInch(Nom,Dom,ticksPerRev,wheelDiameter,inches);

        // Step 2: Reset encoders to ensure accuracy from a fresh baseline
        resetEncoders();

        // Step 3: Set target positions 
        // (Note: The FTC SDK handles your reversed motor directions automatically here)
        leftFront.setTargetPosition(targetTicks);
        rightFront.setTargetPosition(targetTicks);
        leftBack.setTargetPosition(targetTicks);
        rightBack.setTargetPosition(targetTicks);

        // Step 4: Switch to RUN_TO_POSITION mode
        RunToPosition();

        // Step 5: Set maximum speed/power limit
        leftFront.setPower(Math.abs(FORWARD_SPEED));
        rightFront.setPower(Math.abs(FORWARD_SPEED));
        leftBack.setPower(Math.abs(FORWARD_SPEED));
        rightBack.setPower(Math.abs(FORWARD_SPEED));

        // Step 6: Wait until ALL motors arrive at the destination
        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            int avgCurrentPosition = (leftFront.getCurrentPosition() + rightFront.getCurrentPosition() + 
                                      leftBack.getCurrentPosition() + rightBack.getCurrentPosition()) / 4;

            telemetry.addData("Target Ticks", targetTicks);
            telemetry.addData("Current Avg Ticks", avgCurrentPosition);
            telemetry.update();
        }

        // Step 7: Stop the motors and drop out of RUN_TO_POSITION
        STOP();

        RunUsingEn();
        
    }
    public void tickStrafeLeft(double inches, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        int targetTicks = calculateTicksPerInch(Nom,Dom,ticksPerRev,wheelDiameter,inches);

        resetEncoders();

        // Left Front (-) | Right Front (+) | Left Back (+) | Right Back (-)
        leftFront.setTargetPosition(-targetTicks);
        rightFront.setTargetPosition(targetTicks);
        leftBack.setTargetPosition(targetTicks);
        rightBack.setTargetPosition(-targetTicks);

        RunToPosition();

        leftFront.setPower(Math.abs(FORWARD_SPEED));
        rightFront.setPower(Math.abs(FORWARD_SPEED));
        leftBack.setPower(Math.abs(FORWARD_SPEED));
        rightBack.setPower(Math.abs(FORWARD_SPEED));

        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            telemetry.addData("Strafing Left to Target", targetTicks);
            telemetry.update();
        }

        STOP();

        RunUsingEn();
    }

    public void tickStrafeRight(double inches, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        int targetTicks = calculateTicksPerInch(Nom,Dom,ticksPerRev,wheelDiameter,inches);

        resetEncoders();

        // Left Front (+) | Right Front (-) | Left Back (-) | Right Back (+)
        leftFront.setTargetPosition(targetTicks);
        rightFront.setTargetPosition(-targetTicks);
        leftBack.setTargetPosition(-targetTicks);
        rightBack.setTargetPosition(targetTicks);

        RunToPosition();

        leftFront.setPower(Math.abs(FORWARD_SPEED));
        rightFront.setPower(Math.abs(FORWARD_SPEED));
        leftBack.setPower(Math.abs(FORWARD_SPEED));
        rightBack.setPower(Math.abs(FORWARD_SPEED));

        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            telemetry.addData("Strafing Right to Target", targetTicks);
            telemetry.update();
        }

        STOP();

        RunUsingEn();
    }
    public void tickDriveBackward(double inches, double ticksPerRev, double wheelDiameter, double Nom, double Dom) {
        int targetTicks = calculateTicksPerInch(Nom,Dom,ticksPerRev,wheelDiameter,inches);

        resetEncoders();

        leftFront.setTargetPosition(-targetTicks);
        rightFront.setTargetPosition(-targetTicks);
        leftBack.setTargetPosition(-targetTicks);
        rightBack.setTargetPosition(-targetTicks);

        RunToPosition();
        

        // Keep power positive
        leftFront.setPower(Math.abs(FORWARD_SPEED));
        rightFront.setPower(Math.abs(FORWARD_SPEED));
        leftBack.setPower(Math.abs(FORWARD_SPEED));
        rightBack.setPower(Math.abs(FORWARD_SPEED));

        while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy())) {
            int avgCurrentPosition = (leftFront.getCurrentPosition() + rightFront.getCurrentPosition() + 
                                      leftBack.getCurrentPosition() + rightBack.getCurrentPosition()) / 4;
            telemetry.addData("Target Ticks", -targetTicks);
            telemetry.addData("Current Avg Ticks", avgCurrentPosition);
            telemetry.update();
        }

        STOP();

        RunUsingEn();
    }

    public void driveBackwards(double seconds) {
    
        leftFront.setPower(REVERSE_SPEED);
        rightFront.setPower(REVERSE_SPEED);
        leftBack.setPower(REVERSE_SPEED);
        rightBack.setPower(REVERSE_SPEED);

    
        runtime.reset();
    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Driving Backwards: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        leftFront.setPower(STOP);
        rightFront.setPower(STOP);
        leftBack.setPower(STOP);
        rightBack.setPower(STOP);
    }
    public void strafeRight(double seconds) {
    
        leftFront.setPower(FORWARD_SPEED);
        rightFront.setPower(REVERSE_SPEED);
        leftBack.setPower(REVERSE_SPEED);
        rightBack.setPower(FORWARD_SPEED);

    
        runtime.reset();

    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Strafing Right: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        leftFront.setPower(STOP);
        rightFront.setPower(STOP);
        leftBack.setPower(STOP);
        rightBack.setPower(STOP);
    }

    public void strafeLeft(double seconds) {
    
        leftFront.setPower(REVERSE_SPEED);
        rightFront.setPower(FORWARD_SPEED);
        leftBack.setPower(FORWARD_SPEED);
        rightBack.setPower(REVERSE_SPEED);

    
        runtime.reset();

    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", "Strafing Left: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        leftFront.setPower(STOP);
        rightFront.setPower(STOP);
        leftBack.setPower(STOP);
        rightBack.setPower(STOP);
    }
    
    public void launch(double seconds, boolean SPEED) {

        if (SPEED) {
            launcher.setPower(FIRE_SPEED_HIGH);
            sleep(4500);
            transfer.setPower(TRANSFER_SPEED);
            intake.setPower(INTAKE_SPEED);
        } else {
            launcher.setPower(FIRE_SPEED_LOW);
            sleep(4500);
            transfer.setPower(TRANSFER_SPEED);
            intake.setPower(INTAKE_SPEED);
            
        }

        runtime.reset();

    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", " Scoring: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        intake.setPower(STOP);
        launcher.setPower(STOP);
        transfer.setPower(STOP);
    }
    public void launchRPM(double seconds, double rpm) {
        double targetRPM = 0;
        targetRPM = (/*This value is important*/(rpm*1.0) / 60.0)*28;
        launcher.setVelocity(targetRPM);
        sleep(4500);
        transfer.setPower(TRANSFER_SPEED);
        intake.setPower(INTAKE_SPEED);

        runtime.reset();

    
        while (opModeIsActive() && (runtime.seconds() < seconds)) {
            telemetry.addData("Path", " Scoring: %4.1f S", runtime.seconds());
            telemetry.update();
        }

     // STOP all motors immediately so the robot doesn't drift
        intake.setPower(STOP);
        launcher.setPower(STOP);
        transfer.setPower(STOP);
    }
    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        // transfer config unknown

        leftFront  = hardwareMap.get(DcMotor.class, "fl");
        rightFront = hardwareMap.get(DcMotor.class, "fr");
        rightBack = hardwareMap.get(DcMotor.class, "br");
        leftBack = hardwareMap.get(DcMotor.class, "bl");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        intake = hardwareMap.get(DcMotor.class, "intake");
        transfer = hardwareMap.get(DcMotor.class, "trans");
        
        // add REVERSE and FORWARDs 
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /**//**//**/


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        //Wait for 3 seconds
        /* EX
        *tickDriveBackward(1 //Example, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom)
        */
        
        launchRPM(12.5,600);

        //tick movements need more unknown params
        
        tickDriveForward(10, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        tickDriveBackward(10, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        tickStrafeLeft(5, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        tickDriveBackward(5, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        tickTurnRight(90, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        tickTurnLeft(90, mainWheelTicksPerRev,driveWheelDiameter,driveWheelRatioNom, driveWheelRatioDom);
        
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}           
