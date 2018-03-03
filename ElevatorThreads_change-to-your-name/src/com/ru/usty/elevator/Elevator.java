package com.ru.usty.elevator;

public class Elevator implements Runnable {
	
	/**int numOfPeepInElevator, numOfFloor, maxCapacity;

	public Elevator(int numOfPeepInElevator, int numOfFloor, int maxCapacity) {
		this.numOfPeepInElevator = numOfPeepInElevator; // hvar kemur thradurinn inn
		this.numOfFloor = numOfFloor; // a hvada haed aetlar hann ut
		this.maxCapacity = maxCapacity;
	}
	**/
	public static int floorNumbers = 0;
	public static int currFloor = 0;
	public void run() {
		while (true) {
			
	        if (ElevatorScene.elevatorsMAyDie) {
	            return;
	        }

            if (ElevatorScene.scene.getNumberOfFloors() == currFloor + 1) {
                currFloor = 0;
            }

	        ElevatorScene.numOfFloor = currFloor;
	        
	        for(int i = 0; i<ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
	        	ElevatorScene.semaphore1.release();
	        }
	        
	        try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            for (int i = 0; i<ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++){
                try {
                    ElevatorScene.semaphore1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            currFloor++;
	        ElevatorScene.numOfFloor = currFloor;



	        for(int i = 0; i<ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
	        	ElevatorScene.semaphoreOut.release();
	        }

	        //Test thradur bidur a einni semaphoru og er sleppt af henni, fakelyftan okkar:
	        // tharf ad vera sleep time her til ad stoppa a hverri haed
	        
	        try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*if (ElevatorScene.elevatorsMAyDie) {
				return;
			}

			if (floor != 3) {
			    floor++;
            }
            else {
			    floor = 0;
            }

			for(int i = 0; i<ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++) {
				ElevatorScene.semaphore1.release();
			}

			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i<ElevatorScene.maxCapacity - ElevatorScene.scene.getNumberOfPeopleInElevator(0); i++){
				try {
					ElevatorScene.semaphore1.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ElevatorScene.numOfFloor = 1;*/

	    }
	}
}
