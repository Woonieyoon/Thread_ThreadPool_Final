package kr.sys4u.basic.threadpoolcollable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CallableExecutor {
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

	public void execute(List<CallableRandomNumberGenerator> generators) {
		long startTime = System.nanoTime(); // 시간을 알고 싶다면 이걸 쓰라
		List<Future<Integer>> futureIntegers = null;
		try {
			futureIntegers = executor.invokeAll(generators, 5, TimeUnit.MINUTES); // 전체 받는데 걸리는 시간)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");
		
		// 일 끝난거 다 가지고 와봐
		futureIntegers.stream().forEach(future -> {
			try {
				generated.add(future.get());
			} catch (Exception e) {

			}
		});
	}

	public static void main(String[] args) {

		final List<CallableRandomNumberGenerator> generator = new ArrayList<>();

		IntStream.range(0, 100).forEach(i -> generator.add(new CallableRandomNumberGenerator()));

		CallableExecutor callableExecutor = new CallableExecutor();
		callableExecutor.init();
		callableExecutor.execute(generator);
		callableExecutor.shutdown();

		// 시키고 시키고 받아야지 받고 시키고 받고 시키고가 아니다

		// sorting
		Collections.sort(callableExecutor.generated);

		// printing
		callableExecutor.generated.stream().forEach(System.out::println);
		
	}
}
