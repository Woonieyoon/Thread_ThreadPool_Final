package kr.sys4u.unique.runnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class UniqueNumberExecutor {
	private ExecutorService executor;
	private List<Integer> generated = new ArrayList<>();

	public void init() {
		executor = Executors.newFixedThreadPool(100);
	}

	public void shutdown() {
		executor.shutdown();
		try {
			executor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void execute(List<RandomNumberAdder> generators) {
		generators.stream().forEach(generator -> executor.execute(generator));
	}

	public static void main(String[] args) {
				
		UniqueNumberExecutor uniquenumberExecutor = new UniqueNumberExecutor();
		final List<RandomNumberAdder> generator = new ArrayList<>();
		IntStream.range(0, 100).forEach(i -> generator.add(new RandomNumberAdder(uniquenumberExecutor.generated)));
		 
		uniquenumberExecutor.init();
		long startTime = System.nanoTime(); // 시간을 알고 싶다면 이걸 쓰라
		uniquenumberExecutor.execute(generator);
		uniquenumberExecutor.shutdown();
		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");

		// 시키고 시키고 받아야지 받고 시키고 받고 시키고가 아니다
		
		
		// sorting
		Collections.sort(uniquenumberExecutor.generated);

		// printing

		uniquenumberExecutor.generated.stream().forEach(System.out::println);
		
	}
}
