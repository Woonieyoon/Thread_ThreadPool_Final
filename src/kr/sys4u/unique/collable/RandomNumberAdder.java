package kr.sys4u.unique.collable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class RandomNumberAdder implements Callable<List<Integer>> {

	private List<Integer> genreated;

	public RandomNumberAdder(List<Integer> generated) {
		this.genreated = generated;
	}

	@Override
	public List<Integer> call() throws Exception {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {
			int randomNumber = new Random().nextInt(100);
			if (!genreated.contains(randomNumber)) {
				genreated.add(randomNumber);
				return genreated;
			}
		}
	}

}
