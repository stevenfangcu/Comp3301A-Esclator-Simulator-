if(standingCounter == standers && nonStanders == nonStandingCounter){
  randomGen = 2;
  randomGen2 = 2;
  if(stepsAvailableLeftList.size() > stepsAvailableRightList.size()){
    //stepsAvailableLeftList.remove(stepsAvailableLeftList.size() - 1);
  }else if(stepsAvailableLeftList.size() < stepsAvailableRightList.size()){
    //stepsAvailableRightList.remove(stepsAvailableRightList.size() - 1);
  }
}else if(standingCounter == standers){ // all the people standing have gone, only walkers
  randomGen = 0;
  randomGen2 = 2;
}else if(nonStanders == nonStandingCounter ){ // all the people walking have gone, only standers
  randomGen = 1;
  randomGen2 = 2;
}
//first person of the two
System.out.println(randomGen + ":" + randomGen2);
if(randomGen == 1 && standingCounter < standers){
  left.add(1.0);
  standingCounter++;
  if(standingCounter <= standers){
    stepsAvailableLeftList.add(0);
  }
  if(counter == 0 && randomGen == randomGen2){
    stepsAvailableRightList.add(1);
  }
}else if(randomGen == 0 && nonStandingCounter < nonStanders){
  right.add((double)((Math.random() * 35 + 40)/100));
  nonStandingCounter++;
  if(nonStandingCounter <= nonStanders){
    stepsAvailableRightList.add(0);
  }
  if(counter == 0 && randomGen == randomGen2){
    stepsAvailableLeftList.add(1);
  }
}
// second person can go on the step at the same time only if its opposite of first
if(randomGen == randomGen2){ // one only person goes on escalator
  if(randomGen2 == 1 && standingCounter <= standers){
    int endOfList = stepsAvailableRightList.size();
    if(endOfList > 1){ //not the first instance
      int val = (stepsAvailableRightList.get(endOfList-1)); //incrementing steps ahead
      if(val != 0){
          val++;
          System.out.println(endOfList-1 + " " + endOfList + " " + stepsAvailableLeftList.get(endOfList-1));
          stepsAvailableRightList.set(endOfList-1,val);
          //stepsAvailableRightList.add(val);
      }else{
      //stepsAvailableRightList.add(val); // setting the val
        if(endOfList > 0){
          //stepsAvailableRightList.set(endOfList-1,val);
          stepsAvailableRightList.add(val);
        }else{
          val++;
          stepsAvailableRightList.add(val);
        }
      }
    }
  }else if(randomGen2 == 0 && nonStandingCounter <= nonStanders){
    int endOfList = stepsAvailableLeftList.size();
    if(endOfList > 1){ // not the first instance
      int val = 0;
      val = (stepsAvailableLeftList.get(endOfList-1)); //incrementing steps ahead
      if(val != 0){
        val++;
        stepsAvailableLeftList.set(endOfList-1,val);
        //stepsAvailableLeftList.add(val);
      }else{
        //stepsAvailableLeftList.add(val); // setting the val
        if(endOfList > 0){
          stepsAvailableLeftList.set(endOfList-1,val);
        }else{
          val++;
          stepsAvailableLeftList.add(val);
        }
      }
    }
  }
}else if(randomGen2 == 1 && standingCounter < standers){ // the other one
  left.add(1.0);
  standingCounter++;
  System.out.println("???");
  stepsAvailableLeftList.add(0);
}else if(randomGen2 == 0 && nonStandingCounter < nonStanders){
  right.add((double)((Math.random() * 35 + 40)/100)); // the other one
  nonStandingCounter++;
  System.out.println("????");
  stepsAvailableRightList.add(0);
}



      System.out.println(stepsAvailableLeftList + " " + stepsAvailableRightList);
      if(left.size() == 20 || (standingCounter == standers && left.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonLeft is the person that got off
        before the head.
        */
        double head = left.remove();
        if(secondsLeft == 0.0){ // first person on left side
          secondsLeft += 20;
          secondsRight += 20;
        }else{
          secondsLeft += 1; //the step the person is on
          secondsLeft += 0;
        }
      }

      if(right.size() == 20 || (nonStandingCounter == nonStanders && right.size() > 0)){
        /*
        head is the person that is getting off, infrontPersonRight is the person that got off
        before the head.
        */
        double head = right.remove();
        if(secondsRight == 0.0){ // first person on right side
          secondsRight += (head*20);
          secondsLeft += (head*20);
          infrontPersonRight = head;
        }else if(head > infrontPersonRight){ //youre slower than the person infront of you
          //System.out.println("Right: " + ((head - infrontPersonRight)*20));
          secondsRight += (head - infrontPersonRight);
          infrontPersonRight = head;
        }else{ // if faster than person ahead of you
          //System.out.println("Right: " + infrontPersonRight);
          secondsRight += ((infrontPersonRight*(20))/20);
          infrontPersonRight = head;
        }
      }
      if(left.size() == 0 && right.size() == 0){
        break;
      }
      counter++;
    }
    if(secondsLeft > secondsRight){
      seconds = secondsLeft;
    }else{
      seconds = secondsRight;
    }
    System.out.println(stepsAvailableLeftList + " " + stepsAvailableRightList);
    System.out.println(secondsLeft + " " + secondsRight);
    //seconds = seconds + ((standers/2));
    System.out.println("E: " + seconds);
    return seconds;
