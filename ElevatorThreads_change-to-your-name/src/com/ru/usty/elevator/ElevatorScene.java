package com.ru.usty.elevator;

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
	public static Semaphore semaphore1;
	
	public static ElevatorScene scene;

	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 500;  //milliseconds

	private int numberOfFloors;
	private int numberOfElevators;

	ArrayList<Integer> personCount; //use if you want but
									//throw away and
									//implement differently
									//if it suits you
	ArrayList<Integer> exitedCount = null;
	public static Semaphore exitedCountMutex;

	//Base function: definition must not change
	//Necessary to add your code in this one
	// Elevator thraedir verda til i thessu falli
	// þegar kallað er í restartScene þá er samfóran búin til, ef thad er kallað aftur á restartScene
	// þá er semafóran búin til upp á nytt sem ný semafóra
	public void restartScene(int numberOfFloors, int numberOfElevators) {
		
		scene = this;
		// Eftir af thad er kallad i restartScene er thetta adgengilegt alls stadar fra:
		semaphore1 = new Semaphore(0); // 0 er fjöldi permita sem eru opin i upphafi
		
		// Profa hvort Person thradurinn er ad gera thad sem hann a ad vera ad gera akkurat nuna
		// en vid viljum ekki enda med thetta svona:
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				//Test thradur bidur a einni semaphoru og er sleppt af henni, fakelyftan okkar:
				for(int i=0; i<16; i++) {
					ElevatorScene.semaphore1.release(); //signal
				}
			}				
		}).start();
		
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
	// Kalllað í þetta þegar kerfið kallar og segir: það er ný manneskja er komin í kerfið
	public Thread addPerson(int sourceFloor, int destinationFloor) {

		// person er runnable:
		Thread thread = new Thread(new Person(sourceFloor, destinationFloor));
		thread.start();
		
		
		/**
		 * Important to add code here to make a
		 * new thread that runs your person-runnable
		 * 
		 * Also return the Thread object for your person
		 * so that it can be reaped in the testSuite
		 * (you don't have to join() yourself)
		 */

		//dumb code, replace it!
		// Væri skynsamlegt að hafa sér semaphoru utan um þetta, sér mutual exclution semaphoru
		personCount.set(sourceFloor, personCount.get(sourceFloor) + 1);
		
		//Thetta var inni og er nuna out:
		//return null;  //this means that the testSuite will not wait for the threads to finish
		
		return thread; // Thegar ad thad er tha kallad a restartScene thurfum vid ad ganga fra Elevat
	}

	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {

		//dumb code, replace it!
		return 1;
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {
		
		//dumb code, replace it!
		switch(elevator) {
		case 1: return 1;
		case 2: return 4;
		default: return 3;
		}
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor); //floor er indexid i arrayid
	}

	public void decrementNumberOfPeopleWaitingAtFloor(int floor) {
		personCount.set(floor, (personCount.get(floor) - 1));
	}
	
	//Base function: definition must not change, but add your code if needed
	public int getNumberOfFloors() {
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


}


