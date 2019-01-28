package kr.sys4u.basic.threadpoolcollable;

import java.util.Random;
import java.util.concurrent.Callable;

public class CallableRandomNumberGenerator implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Thread.sleep(10);
		return new Random().nextInt(100);
	}

}
