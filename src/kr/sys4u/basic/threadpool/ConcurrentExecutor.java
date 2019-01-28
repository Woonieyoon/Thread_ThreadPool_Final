package kr.sys4u.basic.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentExecutor {

	public static void main(String[] args) {
		int count = 100;
		ExecutorService executor = Executors.newFixedThreadPool(50); // context switching 비용이 증가함 , 갯수를 적게 했을 경우
		List<Integer> generated = new ArrayList<>();
		List<RandomNumberGenerator> generators = new ArrayList<>();

		long startTime = System.nanoTime(); // 시간을 알고 싶다면 이걸 쓰라
		for (int i = 0; i < count; i++) {
			RandomNumberGenerator generator = new RandomNumberGenerator();
			generators.add(generator);
			executor.execute(generator);
		}

		executor.shutdown();
		try {
			// 5분까지는 기다려줄께
			executor.awaitTermination(5 * 60, TimeUnit.SECONDS); // 내부적으로 조인이 일어난다.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");
		// 동시성 문제에 대해 잘 됬는지 안됬는지도 체크 해줘야함

		// getting
		for (RandomNumberGenerator generator : generators) {
			generated.add(generator.getNumber());
		}
		// =generators.stream().forEach(generator ->
		// generated.add(generator.getNumber()));

		// sorting
		Collections.sort(generated);

		// printing

		generated.stream().forEach(System.out::println);

	}

}
