#include <Smartcar.h>
#include <Servo.h>


//Servo myservo;  // create servo object to control a servo
const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 7; //D7
const unsigned int MAX_DISTANCE = 100;
float speed;
float speed2;
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);                //bluetooth value reciever


BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);
int offset;
GY50 gyroscope(12);
DirectionlessOdometer leftOdometer(183);
DirectionlessOdometer rightOdometer(183);

//SimpleCar car(control);
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);


void setup() {
  speed = 0.0;
  speed2 = 0.0;
  offset = gyroscope.getOffset();
  //myservo.attach(8);  // attaches the servo on pin 9 to the servo object
  //myservo.write(110);
  Serial.begin(9600);
  // Initialize the odometers (they won't work otherwise)
  leftOdometer.attach(LEFT_ODOMETER_PIN, []() {
    leftOdometer.update();
  });
  rightOdometer.attach(RIGHT_ODOMETER_PIN, []() {
    rightOdometer.update();
  });
  
  //car.enableCruiseControl();
}

void loop() {
  //car.update();
  int colli = front.getDistance();
  //offset = gyroscope.getOffset();

  Serial.print("Collision : ");
  Serial.println(colli);
  
  /*Serial.print("Speed : ");
  Serial.println(speed);*/
  
  if (colli<30 && colli>0){
    if (speed > 1){
      speed =speed-1;
      delay(20);
    }
    car.overrideMotorSpeed(speed,speed);
    
    
  }else{
    /*car.update();
    if (speed <= 1.0){
      speed = speed+0.01;
      delay(50);
      
    }
    car.setSpeed(speed);*/
    setSpeed(colli);
  } 

}
//----------------------------------------------------------
void setSpeed(int colli){
  if (colli >= 30 && colli<40){
    speed = 10;
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed,speed);

  }
  else if (colli >= 40 && colli<50){
    speed = 20;
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-16.5,speed);

  }
  else if (colli >= 40 && colli<60){
    speed = 30;
    car.setSpeed(speed);
 //   car.overrideMotorSpeed(speed-16.5,speed);

  }
  else if (colli >= 60 && colli<70){
    speed = 50;
    car.setSpeed(speed);
   // car.overrideMotorSpeed(speed-16.5,speed);

  }
  else if (colli >= 70 && colli<80){
    speed = 60;
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-16.5,speed);

  }
  else if (colli >= 80 && colli<90){
    speed = 70;
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-16.5,speed);

  }
  else if (colli >= 90 || colli == 0 ){
    
    car.overrideMotorSpeed(100,100);
    speed = 80;
    delay(20);
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-16.5,speed);

  }
   
}
