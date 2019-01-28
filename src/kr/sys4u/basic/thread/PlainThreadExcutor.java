package kr.sys4u.basic.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//바보 같은 놈
public class PlainThreadExcutor {

	// 유저 스레드 메인스레드가 유저 스레드에 관심없으면 먼저 꺼진다.

	public static void main(String[] args) {
		// long startTime = System.currentTimeMillis();//시각을 알고 싶다. UTC 기준을 1970년대 이후
		final List<Integer> generatedIntegers = new ArrayList<>();
		final List<RandomNumberGenerator> generators= new ArrayList<>();
		final List<Thread> threads = new ArrayList<>();
		
		//execute threads
		long startTime = System.nanoTime(); // 시간을 알고 싶다면 이걸 쓰라
		for(int i=0;i<100;i++) {
			RandomNumberGenerator generator = new RandomNumberGenerator();
			generators.add(generator);
			Thread thread = new Thread(generator);
			thread.start(); // 메인스레드가 아닌 유저 스레드

			threads.add(thread);
		}
		
		// waiting
		// 바보 같은 짓
		for (Thread thread : threads) {
			try {
				thread.join(); // 이 스레드를 호출한 스레드는 기다리는 역할
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");
		// 멀티 스레드는 두배정도 걸리네 시간이~~

		// getting
		//기다릴레
		// 유저스레드가 thread sleep 할때 메인에서 돌아버림
		for (RandomNumberGenerator generator : generators) {
			generatedIntegers.add(generator.getNumber());
		}
		
		
		//sorting
		Collections.sort(generatedIntegers);
		
		//printing

		generatedIntegers.stream().forEach(System.out::println);

		// 여기 코드들은 메인 스레드에서 실행됨
	}

}
