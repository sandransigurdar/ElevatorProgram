package com.ru.usty.elevator;

public class Elevator implements Runnable {
	
	/**int numOfPeepInElevator, numOfFloor, maxCapacity;

	public Elevator(int numOfPeepInElevator, int numOfFloor, int maxCapacity) {
		this.numOfPeepInElevator = numOfPeepInElevator; // hvar kemur thradurinn inn
		this.numOfFloor = numOfFloor; // a hvada haed aetlar hann ut
		this.maxCapacity = maxCapacity;
	}
	**/

	public void run() {
		while (true) {
			
	        if (ElevatorScene.elevatorsMAyDie) {
	            return;
	        }
	        
	        ElevatorScene.numOfFloor = 0;
	        
	        for(int i = 0; i<ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
	        	ElevatorScene.semaphore1.release();
	        }
	        
	        try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        ElevatorScene.numOfFloor = 1;
	        
	        for(int i = 0; i<ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
	        	ElevatorScene.semaphoreOut.release();
	        }
	        
	        //Test thradur bidur a einni semaphoru og er sleppt af henni, fakelyftan okkar:
	        // þarf að vera sleep time hér til að stoppa á hverri hæð
	        
	        try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	    }
	}
}
