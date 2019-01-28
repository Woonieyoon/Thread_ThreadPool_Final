package kr.sys4u.unique.collable;

import java.util.Random;
import java.util.concurrent.Callable;

public class RandomNumberAdder implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Thread.sleep(10);
		return new Random().nextInt(100);
	}

}
