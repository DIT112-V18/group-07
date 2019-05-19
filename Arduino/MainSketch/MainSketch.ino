#include <Smartcar.h>

const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 7; //D7
const unsigned int MAX_DISTANCE = 100;
String cmd;                                                //command received from bluetooth
int speed1, speed2;
int speed = 0;                                                  //ACC speed 
int offset;
int sw = 0;
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);                //bluetooth value reciever
bool flag = false;


BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(18);
DirectionlessOdometer leftOdometer(183);
DirectionlessOdometer rightOdometer(189);

SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup() { 
    Serial1.begin(9600);
    Serial.begin(9600);
    speed1 = 0;
    speed2 = 0;
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
    if (Serial1.available()>0){
        char info = Serial1.read();
        cmd.concat(info);
    } 

    
    if (cmd.length()==4 && !cmd.equals("")){
          Serial1.flush();
          Serial.println(cmd);
          switchCases(cmd);
          cmd = "";
    }
}
void switchCases(String command){
    String com = command;
    String cases = com.substring(0,2);
    String sportM= com.substring(2,4);
    String angle = sportM.toInt();                    // same substring is used for tilt angle 
    //if (cases.equals(""))
    


//---------------------------MOBILITY------------------------->>
/*
 *          LF both left wheels forward
            LB both left wheels backward
            RF both right wheels forward
            RB both right wheels backward
            FF left and right forward
            BB left and right backward
            RL right wheels forward left wheels backward
            LR left wheels forward right wheels backward
*/
    if (cases.equals("FF") && sportM.equals("ON")){        
      speed1=100;
      speed2=100;
      car.overrideMotorSpeed(speed1,speed2);
    } 
    
    if (cases.equals("BB") && sportM.equals("ON")){
      speed1=-100;
      speed2=-100;
      car.overrideMotorSpeed(speed1,speed2);
    }
    
    if (cases.equals("LF")&& sportM.equals("ON")){
      speed1=100;
      speed2=0;
      car.overrideMotorSpeed(speed1,speed2);
    }
    
    if (cases.equals("RF")&& sportM.equals("ON")){
      speed1=0;
      speed2=100;
      car.overrideMotorSpeed(speed1,speed2);
    }
    
    if (cases.equals("LB")&& sportM.equals("ON")){
      speed1=-100;
      speed2=0;
      car.overrideMotorSpeed(speed1,speed2);
    }
    
    if (cases.equals("RB")&& sportM.equals("ON")){
      speed1=0;
      speed2=-100;
      car.overrideMotorSpeed(speed1,speed2);
    }
    
    if (cases.equals("RL")&& sportM.equals("ON")){
        speed1=-100; 
        speed2=100;
        car.overrideMotorSpeed(speed1,speed2);
    }

    if (cases.equals("LR")&& sportM.equals("ON")){
        speed1 =  100; 
        speed2 = -100;
        car.overrideMotorSpeed(speed1,speed2);
    }
    //--------------------------Tilt - Control ---------------------------
    //--------------------------------------------------------------------

    if (cases.equals("FR") ){   
      
        speed1=100; 
        speed2=100;   
            
        if (angle > 70) {
          angle = 70;
        }
        int ss2= speed2 - angle;
        car.overrideMotorSpeed(speed1,ss2);
    } 
    
    if (cases.equals("FL") ){
        speed1=100; 
        speed2=100;
        if (angle > 70) {
          angle = 70;
        }
        int ss1= speed1 - angle;
        car.overrideMotorSpeed(ss1,speed2);

    }
    
    if (cases.equals("BR")){
      
        speed1=-100; 
        speed2=-100;
        if (angle > 70) {
          angle = 70;
        }
        int ss2= speed2 - (angle * -1);
        car.overrideMotorSpeed(speed1,ss2);
        
    }
    
    if (cases.equals("BL")){
        speed1=-100; 
        speed2=-100;
        if (angle > 70) {
          angle = 70;
        }
        int ss1= speed1 - (angle * -1);
        car.overrideMotorSpeed(ss1,speed2);
    }


    //-------------------------- Stop Case (Mobility)  ----------------->>
    if (cases.equals("00")||cases.equals("0L")|| (cases.equals("0R"))){
        speed1=0; 
        speed2=0;
        
        car.setAngle(0);
        Serial1.print(speed1);
        car.overrideMotorSpeed(speed1,speed2);
    }

    //------------------------Adaptive cruise Control------------------->>
    if (cases.equals("AC")){
        int minDist = sportM.toInt();     // the value one the second ssubstring is the same as sportMod
        while (true){
            if (Serial1.available()>0){
                break;
            }       
            adaptiveCruise(minDist);
        }
    }

    
    //-----------------------------------------------------------
    //------------------------Static cruise Control------------------->>
    if (cases.equals("SC")){
        int minSpeed = sportM.toInt();     // the value one the second ssubstring is the same as sportMod
        speed = 0;
        while (true){
            if (Serial1.available()>0){
                break;
            }else {
                staticCruiseControl(minSpeed);
            }
        
        }
        car.setSpeed(0);
    }
    //-----------------------------------------------------------
  }


//-----------------------Adaptive - Cruise - Control------------------

  void adaptiveCruise(int safetyDistance){
  
  int colli = front.getDistance();
  
  if (colli<safetyDistance && colli>0 ){
    if (speed >= 1){
      speed =speed-1;
      delay(20);
      car.setAngle(-20);
      car.setSpeed(speed);
    }

  } else{
          seeSpeed(colli);
  }
}

void seeSpeed(int colli){
    car.setAngle(-20);
  if (colli >= 30 && colli<40){
    speed = 10;
    //car.setAngle(-13);
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-16,speed);
  }
  else if (colli >= 40 && colli<50){
    speed = 20;
    //car.setAngle(-13);
    car.setSpeed(speed);
    
    //car.overrideMotorSpeed(speed-16,speed);

  }
  else if (colli >= 40 && colli<60){
    speed = 30;
    //car.setAngle(-13);
    car.setSpeed(speed); 

    //car.overrideMotorSpeed(speed-offset,speed);

  }
  else if (colli >= 60 && colli<70){
    speed = 40;
    //car.setAngle(-13);
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-offset,speed);

  }
  else if (colli >= 70 && colli<80){
    speed = 50;
    car.setSpeed(speed);

  }
  else if (colli >= 80 && colli<90){
    speed = 60;
    car.setSpeed(speed);

  }
  else if (colli >= 90 || colli == 0 ){
    speed = 70;
   
    car.setSpeed(speed);
  }
  
  car.update();
}


//------------------------------------------------------------------
//-----------------------Static - Cruise - Control------------------

void staticCruiseControl(int minSpeed){
        int period = 20;
        unsigned long time_now = 0;

        
        int colli = front.getDistance();        //distance infront
        if (colli>30){
            speed = minSpeed;  //set the car speed to the minimum speed predefined if the collision distance is greater than 30
        }                     
        if (colli<30 && colli>0 ){
          while (speed >= 1){
            
            time_now = millis();

            //delay
            while(millis() < time_now + period){
              speed =speed-1;
            }

            
          }
        } else{
            car.setAngle(-20);
            car.setSpeed(minSpeed);  
        }
    
}
