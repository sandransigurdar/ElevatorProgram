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

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            int people = ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            ElevatorScene.semaphoresArrOut[ElevatorScene.scene.getCurrentFloorForElevator(0)].release(people);


            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            people = ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            try {
                ElevatorScene.semaphoresArrOut[ElevatorScene.scene.getCurrentFloorForElevator(0)].acquire(people);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            int spaces1 = ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            ElevatorScene.semaphoresArrIn[ElevatorScene.scene.getCurrentFloorForElevator(0)].release(spaces1);
            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            spaces1 = ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0);
            try {
                ElevatorScene.semaphoresArrIn[ElevatorScene.scene.getCurrentFloorForElevator(0)].acquire(spaces1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME/5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ElevatorScene.scene.changeCurrentFloot();
        }
	}
}
