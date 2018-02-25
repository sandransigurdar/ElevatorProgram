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

		    ElevatorScene.elevatorWaitMustex.acquire();
			ElevatorScene.semaphore1.acquire(); // wait
            ElevatorScene.elevatorWaitMustex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(src);
		
		System.out.println("Person Thread released");
	}

}
