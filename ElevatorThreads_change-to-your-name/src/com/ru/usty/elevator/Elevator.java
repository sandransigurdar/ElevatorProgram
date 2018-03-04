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

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int people = ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            ElevatorScene.semaphoresArrOut[currFloor].release(people);

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int spaces1 = ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            /*people = ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            try {
                ElevatorScene.semaphoresArrIn[currFloor].acquire(spaces1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            ElevatorScene.semaphoresArrIn[currFloor].release(spaces1);

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            spaces1 = ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            try {
                ElevatorScene.semaphoresArrIn[currFloor].acquire(spaces1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            changeFloor(currFloor);
            ElevatorScene.numOfFloor = currFloor;
        }
	}

    public void changeFloor(int floor) {
        if (currFloor == ElevatorScene.scene.getNumberOfFloors() - 1) {
            Elevator.currFloor = 0;
        }
        else {
            Elevator.currFloor++;
        }

    }
}
