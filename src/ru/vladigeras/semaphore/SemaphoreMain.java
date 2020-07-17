package ru.vladigeras.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SemaphoreMain {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Semaphore semaphore = new Semaphore(3, true);

		int threads = 10;
		Random random = new Random();
		ExecutorService executorService = Executors.newFixedThreadPool(threads);

		List<Future<Integer>> tasks = new ArrayList<>();
		for (int i = 0; i < threads; i++) {
			tasks.add(executorService.submit(new SequenceSumTask(i, semaphore, random.nextInt(7) + 1)));
		}

		for (Future<Integer> task : tasks) {
			System.out.println(String.format("Task return sum %d", task.get()));
		}

		executorService.shutdown();
	}
}
