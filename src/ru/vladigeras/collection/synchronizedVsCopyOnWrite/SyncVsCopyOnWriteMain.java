package ru.vladigeras.collection.synchronizedVsCopyOnWrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author vladi_geras on 17.07.2020
 */
public class SyncVsCopyOnWriteMain {
	public static void main(String[] args) {
		List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
		List<Integer> copyOnWriteList = new CopyOnWriteArrayList<>();

		fill(synchronizedList, 100);
		fill(copyOnWriteList, 100);

		System.out.println("Synchronized list: ");
		check(synchronizedList);

		System.out.println("CopyOnWrite list: ");
		check(copyOnWriteList);
	}

	private static void check(List<Integer> list) {
		CountDownLatch latch = new CountDownLatch(1);
		ExecutorService service = Executors.newFixedThreadPool(2);

		Future<Long> task1 = service.submit(new ListRunner(0, 50, list, latch));
		Future<Long> task2 = service.submit(new ListRunner(50, 100, list, latch));

		latch.countDown();
		try {
			System.out.println(String.format("Thread 1: %d", task1.get() / 1000));
			System.out.println(String.format("Thread 2: %d", task2.get() / 1000));
			service.shutdown();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static void fill(List<Integer> list, int count) {
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			list.add(random.nextInt(50) + 1);
		}
	}

	static class ListRunner implements Callable<Long> {
		private final int start;
		private final int end;
		private final List<Integer> list;
		private final CountDownLatch latch;

		public ListRunner(int start, int end, List<Integer> list, CountDownLatch latch) {
			this.start = start;
			this.end = end;
			this.list = list;
			this.latch = latch;
		}

		@Override
		public Long call() throws Exception {
			latch.await();

			long startTime = System.nanoTime();
			for (int i = start; i < end; i++) {
				list.get(i);
			}
			return System.nanoTime() - startTime;
		}
	}
}
