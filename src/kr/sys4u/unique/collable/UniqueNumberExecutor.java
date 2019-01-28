package kr.sys4u.unique.collable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UniqueNumberExecutor {

	public static void main(String[] args) {
		Set<Integer> list = new TreeSet<>();

		long startTime = System.nanoTime();

		while (list.size() < 100) {
			List<Future<Integer>> futures = new ArrayList<>();
			ExecutorService executor = Executors.newFixedThreadPool(100);
			futures.add(executor.submit(new RandomNumberAdder()));

			for (int i = 0; i < 100; i++) {
				futures.add(executor.submit(new RandomNumberAdder()));
			}

			executor.shutdown();
			try {
				executor.awaitTermination(5, TimeUnit.MINUTES);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			for (Future<Integer> future : futures) {
				if (list.size() >= 100) {
					break;
				}

				try {
					list.add(future.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}


		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");
		list.stream().forEach(System.out::println);
	}
}
