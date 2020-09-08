## How to run

You can run this project by using maven.

Make sure that for the compiling you are using the JDK 11 otherwise it won't work.
I tested it with many different JDK 12  13 and 14 both of them failed to run the the jar file.

Otherwise the code has been compiled in JDK 11  and the maven should run also without jar file.

## General Idea

My general idea was to create a billiard desk with a ball that resemble a 8 ball.
Using this generated a new challenge. The boarders have to match the green walls of the billiard table. This has been achieved with invisible walls that serves as bound for the ball.
Seeing that the  ball goes crazy in the task  description. I have implemented effects on it that should  be small/ not aggressive. This fits more the cartoonist style that fits with the ball and the desk.

## Effects that can be seen

* **Acceleration**: The ball  will get randomly faster or slower. Each time the wanted speed is reached the ball  will get a new speed to reach.
* **ImpactSpeedLost**: The speed gained during the acceleration is lost after a wall hit
* **CartoonBoom:** A cartoonist effect that simulates a hit after the balls hit the wall.
* **FlashHit**: The ball gets a white flash after it hits the wall. This has some kind of gaming effect when an enemy  gets hit. (As an exemple Zelda 2.  When you get hit or  hit an enemy  the color of the character will change for a few seconds)
* **Rotation**: The ball has a 1/200 chance to rotate and on this a 1/2 chance to rotate clockwise
  and 1/2 chance to rotate anti-clockwise. This  might be hard to see since the speed had to be quit fast such that deceleration don't seem lagging.  After a reset the rotation will still continue were it stopped.
* **Sizeincrease**: The ball will increase in size and decrease until it has reached the randomly chosen radius.  Then a new radius to achieve is chosen.(has a predefined maximum and minimum such that it isn't to much) 
* **Autocorrection**: The ball checks  if it will magically get stuck and resets himself to the middle  of the screen.

#### Note  about bugs and how I try to mitigate them.

###### A small  one with an occurrence rate of 1/1.000.000 reflection

While testing it for 12 hour straight I couldn't reproduce the bug. There is a small chance that with a 45° angle on a corner the ball could slip  through the edge if it reaches a certain speed. I added a small reset function here that should fix it. It's just hard to replicate thus hard to patch.

###### Wall having an intersection twice

Sometime the  ball  gets an intersection with a rectangle  twice. Effectpause will reduce  the amount of this bugs by a good  amount.  Depending on the acceleration and the angle.  It might happen that the ball clip itself into the wall. There the ball  will  do  some  sawtooth movement.
The only solution here was to  make a reset function otherwise the ball would leave the area after making really strange movement.