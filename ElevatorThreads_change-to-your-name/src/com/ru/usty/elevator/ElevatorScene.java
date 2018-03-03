package com.ru.usty.elevator;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * The base function definitions of this class must stay the same
 * for the test suite and graphics to use.
 * You can add functions and/or change the functionality
 * of the operations at will.
 *
 */

public class ElevatorScene {
	
	//Semaphore sem einn thradur getur bedid a og annar losad, tha gerum vid thessa thar sem allir geta haft adgang ad henni:
	public static Semaphore semaphore1; // in a haed 1
	public static Semaphore semaphoreOut; // ut a haed 2

	public static int maxCapacity = 6;
	public static int numOfPeepInElevator = 0;
	public static int numOfFloor;

	//public static int floorArr[] = new int[ElevatorScene.scene.getNumberOfFloors()];
	//public static Semaphore semaphoresArr[] = new Semaphore[4];

	public static ElevatorScene scene;
	public  static Semaphore personCountMutex;
	public static  Semaphore elevatorWaitMustex;

	public static boolean elevatorsMAyDie;

	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 600;  //milliseconds

	private int numberOfFloors;
	private int numberOfElevators;
    private Thread elevatorThread = null;

	ArrayList<Integer> personCount; //use if you want but
									//throw away and
									//implement differently
									//if it suits you
	ArrayList<Integer> exitedCount = null;
	public static Semaphore exitedCountMutex;

	//Base function: definition must not change
	//Necessary to add your code in this one
	// Elevator thraedir verda til i thessu falli
	// tegar kallad er a restartScene ta er samforan buin til, ef thad er kallad aftur a restartScene
	// ta er semaforan buin til upp a nytt sem na semafora
	public void restartScene(int numberOfFloors, int numberOfElevators) {
        		
	    elevatorsMAyDie = true;

	    if (elevatorThread != null) {
	        if (elevatorThread.isAlive()) {
                try {
                    elevatorThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }    
	    //numOfPeepInElevator = 0;
	    numOfFloor = 0;
	   	 
        elevatorsMAyDie = false;
		scene = this;
		// Eftir af thad er kallad i restartScene er thetta adgengilegt alls stadar fra:
		
		// restarta badum semaphorum
		semaphore1 = new Semaphore(0); // 0 er fj�ldi permita sem eru opin i upphafi
		semaphoreOut = new Semaphore(0);
		
        personCountMutex = new Semaphore(1);
        elevatorWaitMustex = new Semaphore(1);
        
		Thread thread = new Thread(new Elevator()); // gera svipad fyrir Elevator klasann
		thread.start();

		// Profa hvort Person thradurinn er ad gera thad sem hann a ad vera ad gera akkurat nuna
		// en vid viljum ekki enda med thetta svona:
        
		
		/**
		 * Important to add code here to make new
		 * threads that run your elevator-runnables
		 * 
		 * Also add any other code that initializes
		 * your system for a new run
		 * 
		 * If you can, tell any currently running
		 * elevator threads to stop
		 */

		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;

		personCount = new ArrayList<Integer>();
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}

		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		}
		else {
			exitedCount.clear();
		}
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
		exitedCountMutex = new Semaphore(1);
	}

	//Base function: definition must not change
	//Necessary to add your code in this one
	// Allir Person thraedir verda til i thessu falli
	// Kallla� � �etta �egar kerfi� kallar og segir: �a� er n� manneskja er komin � kerfi�
	public Thread addPerson(int sourceFloor, int destinationFloor) {

		// person er runnable:
		Thread thread = new Thread(new Person(sourceFloor, destinationFloor)); // gera svipad fyrir Elevator klasann
		thread.start();
		
		
		/**
		 * Important to add code here to make a
		 * new thread that runs your person-runnable
		 * 
		 * Also return the Thread object for your person
		 * so that it can be reaped in the testSuite
		 * (you don't have to join() yourself)
		 */

		increamentNumberOfPeopleWaitingAtFloor(sourceFloor);
		return thread; // Thegar ad thad er tha kallad a restartScene thurfum vid ad ganga fra Elevat
	}

	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {

		return numOfFloor;
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {

		/*
        try {
            personCountMutex.acquire(elevator);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		*/
		//System.out.println(numOfPeepInElevator);
		return numOfPeepInElevator;
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor); //floor er indexid i arrayid
	}

	public void decrementNumberOfPeopleWaitingAtFloor(int floor) {

        try {
            ElevatorScene.personCountMutex.acquire();
                personCount.set(floor, (personCount.get(floor) - 1));
            ElevatorScene.personCountMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

    public void increamentNumberOfPeopleWaitingAtFloor(int floor) {

        try {
            personCountMutex.acquire();
            personCount.set(floor, (personCount.get(floor) + 1));
            personCountMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	//Base function: definition must not change, but add your code if needed
	public int getNumberOfFloors() {
		System.out.println(numberOfFloors);
		return numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfElevators() {
		return numberOfElevators;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfElevators(int numberOfElevators) {
		this.numberOfElevators = numberOfElevators;
	}

	//Base function: no need to change unless you choose
	//				 not to "open the doors" sometimes
	//				 even though there are people there
	public boolean isElevatorOpen(int elevator) {

		return isButtonPushedAtFloor(getCurrentFloorForElevator(elevator));
	}
	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public boolean isButtonPushedAtFloor(int floor) {

		return (getNumberOfPeopleWaitingAtFloor(floor) > 0);
	}

	//Person threads must call this function to
	//let the system know that they have exited.
	//Person calls it after being let off elevator
	//but before it finishes its run.
	public void personExitsAtFloor(int floor) {
		try {
			
			exitedCountMutex.acquire();
			exitedCount.set(floor, (exitedCount.get(floor) + 1));
			exitedCountMutex.release();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public int getExitedCountAtFloor(int floor) {
		if(floor < getNumberOfFloors()) {
			return exitedCount.get(floor);
		}
		else {
			return 0;
		}
	}
	
	// spurning ad gera foll fyrir ad incrementa og decrementa peopleinelevator and floor
	// thurfum ekki ad hugsa um int elevotor thad er bara 0 allavega i bili
	
	public void incremeantPeopleInElevator(int numOfPeepInElevator) {
		this.numOfPeepInElevator++;
	}
	
	public void decreamentPeopleInElevator(int numOfPeepInElevator) {
		this.numOfPeepInElevator--;
	}

}


