import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
public class EscaB{
  /*100 people,standing takes 20seconds to reach top and escalator takes up to 20 people
  sanity check = 68seconds, has 20 steps. 20 for the first pair then 1 second for everyone
  after -> 20 + (98/2) = 20+48 = 68seconds
  This code is used for C).
  */
  public static double mixedEsc(int standers ,int nonStanders){
    int total = standers+nonStanders;
    Queue<Double> left = new LinkedList<>();
    Queue<Double> right = new LinkedList<>();
    int stepsLeft, stepsRight = 0; //steps available to take
    double infrontPersonLeft,infrontPersonRight, secondsLeft,secondsRight;
    infrontPersonLeft = infrontPersonRight = secondsLeft = secondsRight = 0.0;
    double seconds = 0.0;
    int counter = 0;
    int standingCounter = 0;
    int nonStandersCounter = 0;
    int stepsAvailable = 0;
    int height = 20;
    while(true){
      if(left.size() < height && counter < total){
        int randomGen = (int)(Math.random() * 10)%2;
        if(randomGen == 1 && standingCounter < standers){
          left.add(1.0);
          standingCounter++;
          counter++;
        }else if (nonStandersCounter < nonStanders){
          left.add((double)((Math.random() * 35 + 40)/100));
          nonStandersCounter++;
          counter++;
          //left.add(1.0);
        }
      }
      if(right.size() < height && counter < total){
        int randomGen = (int)(Math.random() * 10)%2;
        if(randomGen == 1 && standingCounter < standers){
          right.add(1.0);
          counter++;
          standingCounter++;
        }else if(nonStandersCounter < nonStanders){
          right.add((double)((Math.random() * 35 + 40)/100));
          nonStandersCounter++;
          counter++;
          //right.add(1.0);
        }
      }

      if(left.size() == height || (counter == total && left.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonLeft is the person that got off
        before the head.
        */
        double head = left.remove();
        if(secondsLeft == 0.0){ // first person on left side
          secondsLeft += (head*height);
          infrontPersonLeft = head;
        }else if(head > infrontPersonLeft){ //youre slower than the person infront of you
          //System.out.println("Left: " + ((head - infrontPersonLeft)*10));
          double valu = (infrontPersonLeft*height) - (head*height);
          secondsLeft += (valu/height);
          infrontPersonLeft = head;
        }else{ // if faster than person ahead of you
          //System.out.println("Left: " + infrontPersonLeft);
          secondsLeft += head;
          infrontPersonLeft = head;
        }
      }
      if(right.size() == height || (counter == total && right.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonRight is the person that got off
        before the head.
        */
        double head = right.remove();
        if(secondsRight == 0.0){ // first person on right side
          secondsRight += (head*height);
          infrontPersonRight = head;
        }else if(head < infrontPersonRight){ //youre faster than the person infront of you
          //System.out.println("Right: " + ((head - infrontPersonRight)*20));
          double valu = (infrontPersonRight*height) - (head*height);
          secondsRight += (valu/height);
          infrontPersonRight = head;
        }else{ // if slower than person ahead of you
          //System.out.println("Right: " + infrontPersonRight);
          secondsRight += head;
          infrontPersonRight = head;
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
    return seconds;
  }

  public static void main(String[] args) throws IOException{
    double average = 0.0;
    PrintWriter writer = new PrintWriter("B).txt", "UTF-8");
    for(int i = 0; i < 20; i++){
      int standers = (int)(Math.random() * 99 + 1);
      int nonStanders = 100 - standers;
      //int standers = 0;
      //int nonStanders = 100;
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
