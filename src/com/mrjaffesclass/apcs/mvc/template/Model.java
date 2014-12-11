package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
import java.util.Arrays;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;

  // Model's data variables
  
  //points
  private int variable1;
  //lives
  private int variable2;
  public static int y;
  public static int[] array1 = new int[64];

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    mvcMessaging.subscribe("view:changeButton", this);
    mvcMessaging.subscribe("view:BombHit", this);
    mvcMessaging.subscribe("view:BlankHit", this);
    setVariable1(0);
    setVariable2(3);
    y=0;
    Arrays.fill(array1,0);
    addBombs();
  }
  
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
    if(messageName.equals("view:Buttonclick")) {
     mvcMessaging.notify("model:ArraySent", array1[y], true);
    }
    if(messageName.equals("view:BombHit")) {
     mvcMessaging.notify("model:variable2Changed", (variable2=variable2-1), true);
     y=y+1;
    }
    if(messageName.equals("view:BlankHit")) {
     mvcMessaging.notify("model:variable1Changed", (variable1=variable1+1), true);
     y=y+1;
    }
  }

  /**
   * Getter function for variable 1
   * @return Value of variable1
   */
  public int getVariable1() {
    return variable1;
  }
  
  public int[] getArray1(){
     return array1;
  }

  /**
   * Setter function for variable 1
   * @param v New value of variable1
   */
  public void setVariable1(int v) {
    variable1 = v;
    // When we set a new value to variable 1 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:variable1Changed", variable1, true);
  }
  
  /**
   * Getter function for variable 1
   * @return Value of variable2
   */
  public int getVariable2() {
    return variable2;
  }
  
  /**
   * Setter function for variable 2
   * @param v New value of variable 2
   */
  public void setVariable2(int v) {
    variable2 = v;
    // When we set a new value to variable 2 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:variable2Changed", variable2, true);
  }

  public void addBombs(){
      for(int x=0;x<=10;x++){
        array1[((int)((Math.random()*64)))-1]=1;
    }
  }
}
