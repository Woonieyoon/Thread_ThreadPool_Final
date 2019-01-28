package kr.sys4u.basic.thread;

import java.util.Random;

public class RandomNumberGenerator implements Runnable {

	private int number;

	@Override
	public void run() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			// ignored
		}

		number = new Random().nextInt(100); // 시드는 씨앗
	}

	public int getNumber() {
		return this.number;
	}

}
