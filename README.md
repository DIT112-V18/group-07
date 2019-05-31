# group-07
## What are we going to make?
- We aim to create a remote controlled car emphasizing on hassle-free use and automation. 

## Obstacle detection and manoeuvring:
* Alert for detection of obstacle
* Automatic braking
* Drive around obstacle

## Mobility:
* Forward
* Reverse
* Stop
* Turn left and right

## Cruise control:
* Static
* Adaptive

## Autonomous parking:
* Parallel parking

## Tilt control: 
* Basic mobility 

## Autopilot driving: 
* Driving on a single line

## Tail lamps and head lamps
* Tail lamps will turn on upon braking
* The user will be able to toggle the head lamps on and off

## Why will we make it?
* The obstacle detection feature ensures the safety of the car and it's surroundings. It will make sure that the car doesn't crash into stationary or moving objects.
* The cruise control feature will be added to minimise the need of repeated user input to perform basic straight line mobility. 
* The car needs to be able to manoeuvre forward, backwards, left ,right and also be able to stop in order to provide the staple functionalities of a car. 
* The gesture control feature provides a more intuitive way of operating the car. It eradicates the problem of undesired tapping of buttons on the remote. It is essentially a smarter way of registering user input.
* Parking esentially follows a general algorithm. We have employed this algorithm to automate parking of the car, hence reducing redundant work-load on the user. 
* Tail lamps and brake lamps increase visibility of the car in it's surroundings, thus adding to the factor of safety. 

## How will it be made?
* Obstacle detection and manoeuvring will be made by using infrared sensors, ultrasonic sensors and a servo motor (to mount the ultrasonic sensor).
* Mobility will be implemented through the smartcar library and the gyroscope.
* Cruise control, autopilot and autonomous parking will be managed by an ultrasonic sensor.
* Raspberry Pi and the OpenCV library will be used to implement the gesture control feature.



