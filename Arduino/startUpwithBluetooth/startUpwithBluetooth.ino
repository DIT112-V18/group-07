#include <Smartcar.h>

const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 7; //D7
const unsigned int MAX_DISTANCE = 100;
char blue;
float speed;
int offset;

SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);                //bluetooth value reciever


BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(18);
DirectionlessOdometer leftOdometer(183);
DirectionlessOdometer rightOdometer(189);

SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup() {
  Serial1.begin(9600);
  speed = 0.0;
  offset = gyroscope.getOffset();
  // Initialize the odometers (they won't work otherwise)
  leftOdometer.attach(LEFT_ODOMETER_PIN, []() {
    leftOdometer.update();
  });
  rightOdometer.attach(RIGHT_ODOMETER_PIN, []() {
    rightOdometer.update();
  });

}

void loop() {
  // Maintain the speed and update the heading
    //car.update();
   
    while (Serial1.available()) blue = Serial1.read(); 
    
  
  
      if(blue == 'w'){            //Checks whether value of Incoming_value is equal to 1 
            int colli = front.getDistance();
          
          if (colli<30 && colli>0){
            if (speed >= 1){
              speed =speed-1;
              delay(20);
              car.setAngle(-5);
              car.setSpeed(speed);
            }
              
        
          } else{
                seeSpeed(colli);
          }
      }
      
      else if(blue == 's'){       //Checks whether value of Incoming_value is equal to 0
          
          car.setSpeed(0);        //stop moving car
      }
  
    
}
void seeSpeed(int colli){
  if (colli >= 30 && colli<40){
    speed = 10;
    car.setAngle(-5);
    car.setSpeed(speed);

  }
  else if (colli >= 40 && colli<50){
    speed = 20;
    car.setAngle(-5);
    car.setSpeed(speed);
    

  }
  else if (colli >= 40 && colli<60){
    speed = 30;
    car.setAngle(-5);
    car.setSpeed(speed);    


  }
  else if (colli >= 60 && colli<70){
    speed = 50;
    car.setAngle(-10);
    car.setSpeed(speed);

  }
  else if (colli >= 70 && colli<80){
    speed = 60;
    car.setAngle(-15);
    car.setSpeed(speed);

  }
  else if (colli >= 80 && colli<90){
    speed = 70;
    car.setAngle(-20);

    car.setSpeed(speed);

  }
  else if (colli >= 90 || colli == 0 ){
    speed = 80;
    car.setAngle(-25);

    car.setSpeed(speed);

  }
   
}
