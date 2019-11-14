# Comp3301A-Esclator-Simulator-
Comp3301 Escalator simulator Part E.
The policy is in effect, walkers have different pace, and standers can only switch to becoming a walker if there are no walkers left in the queue. </br>
Change variable height for the time it takes from the bottom to the top, default is 20 which is 20 seconds. Change `((double) ((Math.random() * 35 + 40)/100))` to change the min and max speed of a walker, `(int)(Math.random() * 99 + 1);` for the maximum number of people, and `int nonStanders = 100 - standers;` to correspond with the change.
