package kr.sys4u.unique.collable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UniqueNumberExecutor {

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(100);

		long startTime = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			Future<List<Integer>> s = executor.submit(new RandomNumberAdder(list));
			try {
				list = s.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println((System.nanoTime() - startTime) / 1000_000 + "ms");
		Collections.sort(list);
		list.stream().forEach(System.out::println);
		
	}
}
