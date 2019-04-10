#include <Smartcar.h>

const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 7; //D7
const unsigned int MAX_DISTANCE = 100;
char blue;
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);                //bluetooth value reciever


BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(37);
DirectionlessOdometer leftOdometer(100);
DirectionlessOdometer rightOdometer(100);

SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup() {
  Serial.begin(9600);
  // Initialize the odometers (they won't work otherwise)
  leftOdometer.attach(LEFT_ODOMETER_PIN, []() {
    leftOdometer.update();
  });
  rightOdometer.attach(RIGHT_ODOMETER_PIN, []() {
    rightOdometer.update();
  });

  car.enableCruiseControl();
 // car.setSpeed(1.5);
}

void loop() {
  // Maintain the speed and update the heading
    car.update();
   
    while (Serial.available()) blue = Serial.read(); 
    
    //Serial.println("obstacle at  : "+ colli);
  
  
      if(blue == '1'){            //Checks whether value of Incoming_value is equal to 1 

          int colli = front.getDistance();        //distance measured by the ultrasonic sensors

          if (colli<30 && colli>0){
            car.setSpeed(0);        //stop moving car
            
          }  
          else{
            car.setSpeed(1.5);
            car.getHeading();
            car.getDistance();
            
          }
          
      }
      
      else if(blue == '0'){       //Checks whether value of Incoming_value is equal to 0
          
          car.setSpeed(0);        //stop moving car
      }
  
    
}
