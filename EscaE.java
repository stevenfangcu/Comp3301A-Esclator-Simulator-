import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
public class EscaE{
  /*100 people,standing takes 20seconds to reach top and escalator takes up to 20 people
  sanity check = 68seconds, has 20 steps. 20 for the first pair then 1 second for everyone
  after -> 20 + (98/2) = 20+48 = 68seconds
  This code is used for C).
  */
  public static double mixedEsc(int standers ,int nonStanders) throws IndexOutOfBoundsException{
    System.out.println(standers + " " + nonStanders);
    int total = standers+nonStanders;
    Queue<Double> left = new LinkedList<>();
    Queue<Double> right = new LinkedList<>();
    int stepsLeft, stepsRight = 0; //steps available to take
    double infrontPersonLeft,infrontPersonRight, secondsLeft,secondsRight;
    infrontPersonLeft = infrontPersonRight = secondsLeft = secondsRight = 0.0;
    double seconds = 0.0;
    int counter = 0;
    int standingCounter = 0;
    int nonStandingCounter = 0;
    int height = 5;
    ArrayList<Integer> stepsAvailableLeftList = new ArrayList<Integer>(); //walking
    ArrayList<Integer> stepsAvailableRightList = new ArrayList<Integer>(); //standing
    stepsAvailableRightList.add(0);
    stepsAvailableLeftList.add(0);
    while(true){
      int randomGen = (int)(Math.random() * 10)%2; // first person, walker = 0, stander = 1
      int randomGen2 = (int)(Math.random() * 10)%2; // second person ''
      if((nonStandingCounter+standingCounter) == total){
        randomGen = 3;
        randomGen2 = 3;
        nonStanders = nonStandingCounter;
        standers = standingCounter;
      }else if(nonStanders == nonStandingCounter &&  standers >= standingCounter+2){ //all walkers have went
        nonStanders++;
        standers--;
        randomGen = 1;
        randomGen2 = 0;
      }else if(standingCounter == standers && nonStanders <= nonStandingCounter){
        randomGen = 0;
        randomGen2 = 2;
      }else if(nonStanders == nonStandingCounter &&  standers <= standingCounter){
        randomGen = 1;
        randomGen2 = 2;
      }
      if(randomGen == 1 && randomGen2 == randomGen && standingCounter < standers){ // both standing
        System.out.println(randomGen + "-" + randomGen2);
        left.add(1.0);
        standingCounter++;
        int valueStatement = 0;
        if(!(stepsAvailableRightList.isEmpty())){
          valueStatement = stepsAvailableRightList.get(stepsAvailableRightList.size()-1);
          valueStatement += 1;
          stepsAvailableRightList.set(stepsAvailableRightList.size()-1,valueStatement);
        }
        stepsAvailableLeftList.add(0);
      }else if(randomGen == 0 && randomGen2 == randomGen && nonStandingCounter < nonStanders){ // both walking
        System.out.println(randomGen + "-" + randomGen2);
        right.add((double)((Math.random() * 35 + 40)/100));
        nonStandingCounter++;
        int valueStatement = 0;
        if(stepsAvailableLeftList.isEmpty()){

        }else{
          valueStatement = stepsAvailableLeftList.get(stepsAvailableLeftList.size()-1);
          valueStatement += 1;
          stepsAvailableLeftList.set(stepsAvailableLeftList.size()-1,valueStatement);
        }
        stepsAvailableRightList.add(0);
      }else if((randomGen == 0 && randomGen2 == 1) || (randomGen == 1 && randomGen2 == 0)){ // both can get on
        System.out.println(randomGen + "-" + randomGen2);
        nonStandingCounter++;
        standingCounter++;
        left.add(1.0);
        right.add((double)((Math.random() * 35 + 40)/100));
        stepsAvailableRightList.add(0);
        stepsAvailableLeftList.add(0);
      }else if(randomGen == 1 && randomGen2 == 2){
        System.out.println(randomGen + "-" + randomGen2);
        left.add(1.0);
        stepsAvailableLeftList.add(0);
        standingCounter++;
      }else if(randomGen == 0 && randomGen == 2){
        System.out.println(randomGen + "-" + randomGen2);
        right.add((double)((Math.random() * 35 + 40)/100));
        stepsAvailableRightList.add(0);
        nonStandingCounter++;
      }
      System.out.println(randomGen + "-" + randomGen2);
      System.out.println(stepsAvailableLeftList + " " + stepsAvailableRightList);
      System.out.println(standers + " " + nonStanders);
      System.out.println("1: " + standingCounter + " " + nonStandingCounter);
/*
the above is the code to keep track of steps taken and adding people to the queue
code below is calculating the time it takes
*/

      if(left.size() == height || (standingCounter == standers && left.size() > 0)){ //left side is full
        double head = left.remove();
        int secondsDiff = 0;
        if(stepsAvailableRightList.isEmpty()){
          secondsDiff = 0;
        }else{
          secondsDiff = stepsAvailableRightList.get(0);
          stepsAvailableRightList.remove(0);
        }
        if(secondsLeft == 0.0){ // first person off is on left side, base timer
          secondsLeft += height+(secondsDiff);
          secondsRight += height+(secondsDiff);
          System.out.println("init right: " + secondsLeft);
        }else{
          secondsLeft += 1; //the step the person is on
          secondsLeft += secondsDiff; //amount of steps available infront
        }
      }

      if(right.size() == height || (nonStandingCounter == nonStanders && right.size() > 0)){ //right side is full
        double head = right.remove();
        int stepsAvailable = 0;
        if(stepsAvailableLeftList.isEmpty()){
          stepsAvailable = 0;
        }else{
          stepsAvailable = stepsAvailableLeftList.get(0); // person's speed getting off
          stepsAvailableLeftList.remove(0);
        }
        int heightSub = height - stepsAvailable;
        if(secondsRight == 0.0){ //first person off is on the right side, base timer
          secondsRight += (head*height);
          secondsLeft += (head*height);
          System.out.println("init left: " + secondsRight);
          infrontPersonRight = head; //keep track of the last person that got off.
        }else if(head < infrontPersonRight){ // your steps per second is lower (therefore faster)
          double secondsUsed = (head * stepsAvailable);
          double stepsNotAvailabe = (heightSub * infrontPersonRight);
          double sum = (height) - (secondsUsed) - (stepsNotAvailabe);
          sum = (sum/height);
          secondsRight += sum;
          infrontPersonRight = head;
        }else{ //slower than the person ahead of you.
          secondsRight += head;
          infrontPersonRight = head;
        }
      }

      if( (standingCounter >= standers) && (nonStandingCounter >= nonStanders)){ //&& (left.size() == 0)){
        if(left.size() == 0){
          break;
        }
        if(right.size() == 0){
          break;
        }
      }

    }
    System.out.println(secondsLeft + " " + secondsRight);
    if(secondsLeft > secondsRight){
      return secondsLeft;
    }else{
      return secondsRight;
    }
  }

  public static void main(String[] args) throws IOException{
    double average = 0.0;
    int i = 0;
    PrintWriter writer = new PrintWriter("E).txt", "UTF-8");
    for(i = 0; i < 20; i++){
      int standers = (int)(Math.random() * 99 + 1);
      int nonStanders = 100 - standers;
      //int standers = 5;
      //int nonStanders = 5;
      double returnV = mixedEsc(standers,nonStanders);
      writer.println("value: " + returnV);
      writer.println(standers + " " + nonStanders);
      writer.println();
      average += returnV;
    }
    writer.println("Average: " + (average/i));
    writer.close();
    System.out.println("Average: " + average/i);
  }
}
