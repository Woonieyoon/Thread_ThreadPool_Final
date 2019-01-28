package kr.sys4u.unique.runnable;

import java.util.List;
import java.util.Random;

public class RandomNumberAdder implements Runnable {

	private List<Integer> genreated;

	public RandomNumberAdder(List<Integer> generated) {
		this.genreated = generated;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			int randomNumber = new Random().nextInt(100);
			// check - and - insert 두가지를 묶지 않았기 때문에 정상적으로 작업하지 않음
			synchronized (genreated) {
				if (!genreated.contains(randomNumber)) {
					genreated.add(randomNumber);
					return;
				}
			}
		}

	}

}
