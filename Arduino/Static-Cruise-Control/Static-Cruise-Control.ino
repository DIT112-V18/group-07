#include <Smartcar.h>

const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const unsigned int MAX_DISTANCE = 100;
const float speed = 0.25;

BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(37);
DirectionlessOdometer leftOdometer(100);
DirectionlessOdometer rightOdometer(100);

SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup() {
 Serial.begin(9600);
 leftOdometer.attach(LEFT_ODOMETER_PIN, []() {
    leftOdometer.update();
  });
  rightOdometer.attach(RIGHT_ODOMETER_PIN, []() {
    rightOdometer.update();
  });
  car.setSpeed(0.0);  //0.5m/sec 
}

//Input for static speed
void cruiseControl(float speed){
  car.setSpeed(speed);
}

void loop() {
  // put your main code here, to run repeatedly:
  // Maintain the speed and update the heading
  car.update();
}
