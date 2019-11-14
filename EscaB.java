import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
public class EscaB{
  /*100 people,standing takes 20seconds to reach top and escalator takes up to 20 people
  sanity check = 68seconds, has 20 steps. 20 for the first pair then 1 second for everyone
  after -> 20 + (98/2) = 20+48 = 68seconds
  This code is used for C).
  */
  public static double mixedEsc(int standers1 ,int nonStanders1){
    Queue<Double> left = new LinkedList<>();
    Queue<Double> right = new LinkedList<>();
    int standers = standers1;
    int nonStanders = nonStanders1;
    int stepsLeft, stepsRight = 0; //steps available to take
    double infrontPersonLeft,infrontPersonRight, secondsLeft,secondsRight;
    infrontPersonLeft = infrontPersonRight = secondsLeft = secondsRight = 0.0;
    System.out.println(standers + " " + nonStanders);
    double seconds = 0.0;
    int counter = 0;
    int standingCounter = 0;
    int nonStandersCounter = 0;
    int stepsAvailable = 0;
    while(true){
      if(left.size() < 20 && counter < 100){
        int randomGen = (int)(Math.random() * 10)%2;
        if(randomGen == 1 && standingCounter < standers){
          left.add(1.0);
          standingCounter++;
          counter++;
        }else if (nonStandersCounter < nonStanders){
          left.add((double)((Math.random() * 60 + 40)/100));
          System.out.println("2");
          nonStandersCounter++;
          counter++;
          //left.add(1.0);
        }
      }
      if(right.size() < 20 && counter < 100){
        int randomGen = (int)(Math.random() * 10)%2;
        if(randomGen == 1 && standingCounter < standers){
          right.add(1.0);
          counter++;
          standingCounter++;
        }else if(nonStandersCounter < nonStanders){
          right.add((double)((Math.random() * 60 + 40)/100));
          System.out.println("1");
          nonStandersCounter++;
          counter++;
          //right.add(1.0);
        }
      }

      if(left.size() == 20 || (counter == 100 && left.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonLeft is the person that got off
        before the head.
        */
        double head = left.remove();
        if(secondsLeft == 0.0){ // first person on left side
          secondsLeft += (head*20);
          infrontPersonLeft = head;
        }else if(head > infrontPersonLeft){ //youre slower than the person infront of you
          //System.out.println("Left: " + ((head - infrontPersonLeft)*10));
          secondsLeft += ((head - infrontPersonLeft));
          infrontPersonLeft = head;
        }else{ // if faster than person ahead of you
          //System.out.println("Left: " + infrontPersonLeft);
          secondsLeft += infrontPersonLeft;
          infrontPersonLeft = head;
        }
      }
      if(right.size() == 20 || (counter == 100 && right.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonRight is the person that got off
        before the head.
        */
        double head = right.remove();
        if(secondsRight == 0.0){ // first person on right side
          secondsRight += (head*20);
          infrontPersonRight = head;
        }else if(head > infrontPersonRight){ //youre slower than the person infront of you
          //System.out.println("Right: " + ((head - infrontPersonRight)*20));
          System.out.println("2 " + head + " " + infrontPersonRight);
          secondsRight += ((head - infrontPersonRight));
          infrontPersonRight = head;
        }else{ // if faster than person ahead of you
          //System.out.println("Right: " + infrontPersonRight);
          secondsRight += infrontPersonRight;
          infrontPersonRight = head;
          System.out.println("1 " + head + " " + infrontPersonRight);
        }
      }
      if(left.size() == 0 && right.size() == 0){
        break;
      }
    }
    if(secondsLeft > secondsRight){
      seconds = secondsLeft;
    }else{
      seconds = secondsRight;
    }
    //seconds = seconds + ((standers/2));
    System.out.println(counter);
    System.out.println(seconds);
    return seconds;
  }

  public static void main(String[] args) throws IOException{
    double average = 0.0;
    PrintWriter writer = new PrintWriter("NEW).txt", "UTF-8");
    for(int i = 0; i < 20; i++){
      //int standers = (int)(Math.random() * 99 + 1);
      //int nonStanders = 100 - standers;
      int standers = 0;
      int nonStanders = 100;
      double returnV = mixedEsc(standers,nonStanders);
      writer.println(returnV);
      writer.println(standers + " " + nonStanders);
      writer.println();
      average += returnV;
    }
    writer.println("Average: " + (average/20));
    writer.close();
    System.out.println("Average: " + average/20);
  }
}
