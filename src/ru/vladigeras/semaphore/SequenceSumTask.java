package ru.vladigeras.semaphore;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * @author vladi_geras on 09.07.2020
 */
public class SequenceSumTask implements Callable<Integer> {
	private final long id;
	private final Semaphore semaphore;
	private final int limit;

	public SequenceSumTask(long id, Semaphore semaphore, int limit) {
		this.id = id;
		this.semaphore = semaphore;
		this.limit = limit;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println(String.format("%s [id = %d] waiting permission...", Thread.currentThread().getName(), id));
		semaphore.acquire();
		System.out.println(String.format("%s [id = %d] got permission", Thread.currentThread().getName(), id));

		System.out.println(String.format("%s [id = %d] start sum calculation...", Thread.currentThread().getName(), id));
		int sum = 0;
		if (limit > 0) {
			for (int i = 0; i < limit; i++) {
				Thread.sleep(1000);
				sum += i;
			}
		}
		System.out.println(String.format("%s [id = %d] end sum calculation", Thread.currentThread().getName(), id));

		semaphore.release();
		System.out.println(String.format("%s [id = %d] released permission", Thread.currentThread().getName(), id));
		return sum;
	}
}
