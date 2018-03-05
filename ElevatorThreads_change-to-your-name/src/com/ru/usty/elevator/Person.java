package com.ru.usty.elevator;

public class Person implements Runnable {
	
	int src, dst;
	public Person(int src, int dst) {
		this.src = src; // hvar kemur thradurinn inn
		this.dst = dst; // a hvada haed aetlar hann ut
	}

	@Override
	public void run() {
		// Tjekkum her a semaphorunni:
		try {
			ElevatorScene.semaphoresArrIn[src].acquire(); // wait
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(src);
		ElevatorScene.scene.incremeantPeopleInElevator(0);
		
		//aquire semaphore2
		try {

			ElevatorScene.semaphoresArrOut[dst].acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ElevatorScene.scene.decreamentPeopleInElevator(0);
		ElevatorScene.scene.personExitsAtFloor(dst);

		System.out.println("Person Thread released");
	}

}
