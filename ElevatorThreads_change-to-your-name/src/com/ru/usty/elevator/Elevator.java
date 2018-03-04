package com.ru.usty.elevator;

public class Elevator implements Runnable {
	
	/**int numOfPeepInElevator, numOfFloor, maxCapacity;

	public Elevator(int numOfPeepInElevator, int numOfFloor, int maxCapacity) {
		this.numOfPeepInElevator = numOfPeepInElevator; // hvar kemur thradurinn inn
		this.numOfFloor = numOfFloor; // a hvada haed aetlar hann ut
		this.maxCapacity = maxCapacity;
	}
	**/

	public static int currFloor = 0;
	public void run() {
		while (true) {
			
	        if (ElevatorScene.elevatorsMAyDie) {
	            return;
	        }

	        if (ElevatorScene.scene.getNumberOfFloors() == currFloor + 1){
	            currFloor = 0;
            }
            System.out.println(currFloor);
            ElevatorScene.numOfFloor = currFloor;

	        for (int i = 0; i < ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
	            ElevatorScene.semaphoresArrIn[currFloor].release();
            }

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            currFloor++;
            ElevatorScene.numOfFloor = currFloor;

            for (int i = 0; i < ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
                try {
                    ElevatorScene.semaphoresArrIn[currFloor - 1].acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
                ElevatorScene.semaphoresArrOut[currFloor - 1].release();
            }

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(currFloor);

        }
	}
}
