#include <Smartcar.h>



#define sensorPinL 27
#define sensorPinC 25
#define sensorPinR 23

const unsigned short LEFT_ODOMETER_PIN = 2;
const unsigned short RIGHT_ODOMETER_PIN = 3;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const int TRIGGER_PIN = 6; //D6 front
const int ECHO_PIN = 7; //D7 front
const int TRIGGER_PIN_L = 52; //D52 left
const int ECHO_PIN_L = 50 ; //D50 left
const int TRIGGER_PIN_R = 48 ; //D48 right
const int ECHO_PIN_R = 46 ; //D46 right
const unsigned int MAX_DISTANCE = 100;
String cmd;                                                //command received from bluetooth
int speed1, speed2;
int speed = 0;                                                  //ACC speed
int offset;

SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);                //bluetooth value reciever front
SR04 right(TRIGGER_PIN_R, ECHO_PIN_R, MAX_DISTANCE);                //bluetooth value reciever right
SR04 left(TRIGGER_PIN_L, ECHO_PIN_L, MAX_DISTANCE);                //bluetooth value reciever left
bool finishedTurning = false;


int frontLED1 = 39;
int frontLED2 = 41;
int backLEDs = 37;

BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(18);
DirectionlessOdometer leftOdometer(183);
DirectionlessOdometer rightOdometer(189);
bool turnmood,step1,step2,step3;
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

bool rightS;
bool leftS;
int side = 0;
int prevColli=0;


void setup() {
    // AD
    pinMode(frontLED1,OUTPUT);
    pinMode(frontLED2,OUTPUT);
    pinMode(backLEDs,OUTPUT);
    //Lights
    pinMode(frontLED1,OUTPUT);
    pinMode(frontLED2,OUTPUT);
    pinMode(backLEDs,OUTPUT);


    Serial1.begin(9600);
    Serial.begin(9600);
    speed1 = 0;
    speed2 = 0;

    rightS = false;
    leftS = false;
    turnmood = false;
    step1 = false;
    step2 = false;
    step3 = false;
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
    Serial1.write(speed);

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
    int angle = sportM.toInt();                    // same substring is used for tilt angle

//--------------------------------HEAD LIGHTS TURNING ON AND OFF-----------------------
/*
            HO headlights turn ON
            HF headlights turn OFF
*/
    if(com.equals("LION")){
      digitalWrite(frontLED1,HIGH);
      digitalWrite(frontLED2,HIGH);
    }

    if(com.equals("LIOF")){
      digitalWrite(frontLED1,LOW);
      digitalWrite(frontLED2,LOW);
    }

//---------------------------MOBILITY Sport mode ON------------------------->>
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
        //-------------------------Mobility SportMode Off---------------------

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

    if (cases.equals("FF") && sportM.equals("OF")){
        speed=0;
        while(speed<=70){
          if(Serial1.available()>0){
            break;
            }
            speed=speed+1;   
            Serial1.print((char)speed);
            car.setSpeed(speed);
            delay(20);
            
          }
    }

    if (cases.equals("BB") && sportM.equals("OF")){
      speed=0;
        while(speed >=-70){
          if(Serial1.available()>0){
            break;
            }
            speed=speed-1;
            Serial1.print(speed);

            car.setSpeed(speed);
            delay(20);
          }
    }

    if (cases.equals("LF")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed1 <= 70){
            if(Serial1.available()>0){
                break;
            }
            speed1=speed1+1;
            Serial1.print(speed1);

            car.overrideMotorSpeed(speed1,speed2);
            delay(20);
          }


    }

    if (cases.equals("RF")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed2<=70){
        if(Serial1.available()>0){
          break;
          }
          speed2=speed2+1;
          Serial1.print(speed2);

          car.overrideMotorSpeed(speed1,speed2);
          delay(20);
          }
    }

    if (cases.equals("LB")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed1 >= -70){
          
            speed1=speed1-1;            
            Serial1.print(speed1);

            car.overrideMotorSpeed(speed1,speed2);
            if(Serial1.available()>0){
            break;
            }
            delay(20);
          }
    }

    if (cases.equals("RB")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed2 >= -70){
        if(Serial1.available()>0){
          break;
          }
          speed2=speed2-1;            
          Serial1.print(speed2);

          car.overrideMotorSpeed(speed1,speed2);
          delay(20);
          }

    }

    if (cases.equals("RL")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed1>=-70 && speed2 <= 70){
        speed1=speed1 - 1;
        speed2=speed2 + 1;
        Serial1.print(speed2);

        car.overrideMotorSpeed(speed1,speed2);
        delay(20);

        }
    }

    if (cases.equals("LR")&& sportM.equals("OF")){
      speed1=0;
      speed2=0;
      while(speed1 <= 70 && speed2>=-70){
        speed1=speed1+1;
        speed2=speed2-1;            
        Serial1.print(speed1);

        car.overrideMotorSpeed(speed1,speed2);
        delay(20);

        }
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
        breakLightON();
        int period = 20;
        unsigned long time_now = 0;
        speed1=0;
        speed2=0;

        car.setAngle(0);
        Serial1.print(speed1);
        car.overrideMotorSpeed(speed1,speed2);

    }else{
        breakLightOFF();
    }

    //------------------------Adaptive cruise Control------------------->>
    if (cases.equals("AC")){
        int minDist = sportM.toInt();     // the value one the second ssubstring is the same as sportMod
        while (true){
            if (Serial1.available()>0){
                break;
            }else {
                adaptiveCruise(minDist);
            }
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
    //------------------------Obstacle Manouvering------------------->>
    if (cases.equals("OM")){
        rightS = false;
        leftS = false;
        turnmood = false;
        step1 = false;
        step2 = false;
        step3 = false;
        finishedTurning=false;        
        speed = 0;
        while (finishedTurning == false){
            turnFunction();
        }
        finishedTurning=true;
    }
    //-----------------------------------------------------------
    //------------------------ Autonomous Driving ------------------->>
    if (cases.equals("AD")){
      
        speed1 =35;
        speed2 = 35;
        speed = 0;
        while (true){
            if (obstacleDetection()==true){
              car.setSpeed(0);
            }
            else {

                int LSensorVal = digitalRead(sensorPinL);
                int RSensorVal = digitalRead(sensorPinR);
                int CSensorVal = digitalRead(sensorPinC);


                if ((LSensorVal== 1) && (CSensorVal==0) && (RSensorVal==1)){ moveForward(); }

                if ((LSensorVal ==0) && (CSensorVal==1) &&(RSensorVal==1)){ moveLeft();}
                if ((LSensorVal == 0) && (CSensorVal == 0) && (RSensorVal==1)){ moveLeft();}

                if((LSensorVal==1) && (CSensorVal ==1) && (RSensorVal==0)){ moveRight();}
                if((LSensorVal==1) && (CSensorVal ==0) && (RSensorVal==0)){ moveRight();}

                if((LSensorVal ==0) && (CSensorVal==0) && (RSensorVal==0)){ stopCar();}
                if((LSensorVal ==1) && (CSensorVal==1) && (RSensorVal==1)){ stopCar();}
            }
            if (Serial1.available()>0){
                break;
            }
        }
    }

    //---------------------------END Switch Cases--------------------------
  }

//-----------------------Adaptive - Cruise - Control------------------

void adaptiveCruise(int safetyDistance){

  seeSpeed(safetyDistance);

}

void seeSpeed(int safetyDistance){
    car.setAngle(-20);  
    int colli = front.getDistance();

  if (colli<safetyDistance && colli>0 ){
    if (speed >= 1){
      speed =speed-10;
      delay(20);
      car.setAngle(-20);
      car.setSpeed(speed);
    }

  }else if (colli >= 30 && colli<40 ){
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
    speed = 25;
    //car.setAngle(-13);
    car.setSpeed(speed);

    //car.overrideMotorSpeed(speed-offset,speed);

  }
  else if (colli >= 60 && colli<70 ){
    speed = 40;
    //car.setAngle(-13);
    car.setSpeed(speed);
    //car.overrideMotorSpeed(speed-offset,speed);

  }
  else if (colli >= 70 && colli<80 ){
    speed = 50;
    car.setSpeed(speed);

  }
  else if (colli >= 80 && colli<90 ){
    speed = 60;
    car.setSpeed(speed);

  }
  else if (colli >= 90 || colli == 0 ){
    speed = 70;

    car.setSpeed(speed);
  }

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
  
                speed =speed-1;
            }
        } else{
            car.setAngle(-20);
            car.setSpeed(minSpeed);
        }

}

//-----------------------------------------------------------------
//--------------------Manouvering -----------
void turnFunction(){

    int colli = front.getDistance();
    int rightColli = right.getDistance();
    int leftColli= left.getDistance();

    if (turnmood == false){
        if (speed == 0){
          turnmood = true;
          prevColli = colli;

          //------------checking which sides are empty-----
          //myservo.write(0);             //watch the right side

          Serial.println(rightColli);
          if (rightColli > (50) || rightColli == 0){
            rightS = true;
          }
          if (leftColli  > (50) || leftColli == 0){
            leftS = true;
          }

          //myservo.write(90);

          //-------------------------------------------------

          if (leftS == true){          //turning to right
            turnLeft();
            side = 4;             //left side starter

          }
          if (rightS == true){                       //turning to left
            turnRight();
            side = 1;             // . right side starter
          }
        }
    }
    //turnmode == true---->
    else {
          if (side == 1){           //right side trigger

              leftColli = left.getDistance();

              if (leftColli  <= (prevColli+10) && leftColli > 0){
                 car.setSpeed(40);
              }
              else {
                car.setSpeed(0);
                prevColli = leftColli;
                delay(100);
                turnLeft();
                car.setSpeed(40);
                delay(1500);
                car.setSpeed(0);

                side = 2;
              }
          }
          if (side == 2){

              leftColli = left.getDistance();

              if (leftColli  <= (prevColli+10) && leftColli > 0){
                   car.setSpeed(40);
              }else {
                car.setSpeed(0);
                delay(100);
                turnLeft();
                car.setSpeed(40);
                delay(800);
                car.setSpeed(0);

                side = 3;
              }

          }

          if (side == 3){
                turnRight();
                delay(100);
                turnmood = false;
                finishedTurning = true;
          }


          if (side == 4){           //right side trigger

              rightColli = right.getDistance();

              if (rightColli  <= (prevColli+10) && rightColli > 0){
                 car.setSpeed(40);
              }
              else {
                car.setSpeed(0);
                prevColli = rightColli;
                delay(100);
                turnRight();
                car.setSpeed(40);
                delay(1500);
                car.setSpeed(0);

                side = 5;
              }


          }
          if (side == 5){

              rightColli = right.getDistance();

              if (rightColli  <= (prevColli+10) && rightColli > 0){
                   car.setSpeed(40);
              }else {
                car.setSpeed(0);
                delay(100);
                turnRight();
                car.setSpeed(40);
                delay(800);
                car.setSpeed(0);

                side = 6;
              }

          }

          if (side == 6){
                turnLeft();
                delay(100);
                turnmood = false;
                finishedTurning = true;
          }
    }
}


//-----------------------------------------------------------------
//------------------------Turning Functions ------------
void turnRight(){
    car.setSpeed(40);
    car.setAngle(90);
    //car.overrideMotorSpeed(30,0);
    delay(900);
    car.setSpeed(0);
    delay(10);
    car.setAngle(-18);
}
void turnLeft(){
    car.setSpeed(40);
    car.setAngle(-90);
    //car.overrideMotorSpeed(0,30);
    delay(900);
    car.setSpeed(0);
    delay(10);
    car.setAngle(-18);
}
//------------------------------------------------------
//--------------------------------Break LIGHTS TURNING ON AND OFF-----------------------
void breakLightON(){
    digitalWrite(backLEDs,HIGH);
}

void breakLightOFF(){
    digitalWrite(backLEDs,LOW);
}
//------------------------------------------------------------------
//---------------methods for autonomous car -------------------



void moveForward(){
  int speed1 =35;
   int speed2 = 35;
  car.overrideMotorSpeed(speed1,speed2);
  Serial.println(" move Forward");

//delay(50);

  }


void moveLeft(){
   Serial.println(" move Left");
 int speed1 =0;
   int speed2 = 35;
  car.overrideMotorSpeed(speed1,speed2);
  //delay(50);
  
  
  }

void moveRight(){
   Serial.println(" move Right");
  
  int speed1 =35;
   int speed2 = 0;
  car.overrideMotorSpeed(speed1,speed2);
 
}
 

void stopCar(){
   Serial.println(" Stop ");
     int speed1 =0;
   int speed2 = 0;
  car.overrideMotorSpeed(speed1,speed2);

  
}

//-----------------------Basic obstacle detection -------
bool obstacleDetection(){
  int colli = front.getDistance();
  if (colli <30 && colli >0){
    return true;
  }else{
    return false;
  }
}
