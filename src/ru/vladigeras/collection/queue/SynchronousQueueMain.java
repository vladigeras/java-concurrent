package ru.vladigeras.collection.queue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * @author vladi_geras on 17.07.2020
 */
public class SynchronousQueueMain {
	public static void main(String[] args) {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();
		new Thread(new Producer(queue)).start();
		new Thread(new Consumer(queue)).start();
		new Thread(new Consumer(queue)).start();
	}

	static class Producer implements Runnable {
		private final SynchronousQueue<Integer> queue;

		Producer(SynchronousQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			Random random = new Random();
			try {
				for (int i = 0; i < 10; i++) {
					System.out.println(String.format("%s waiting for push", Thread.currentThread().getName()));
					Thread.sleep(random.nextInt(2000) + 1);
					queue.put(i);
					System.out.println(String.format("%s push new value: %d [capacity remains = %d]", Thread.currentThread().getName(), i, queue.remainingCapacity()));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class Consumer implements Runnable {
		private final SynchronousQueue<Integer> queue;

		Consumer(SynchronousQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			Random random = new Random();
			try {
				for (int i = 0; i < 10; i++) {
					System.out.println(String.format("%s waiting for pull", Thread.currentThread().getName()));
					Thread.sleep(random.nextInt(2000) + 1);
					int value = queue.take();
					System.out.println(String.format("%s pull new value: %d [capacity remains = %d]", Thread.currentThread().getName(), value, queue.remainingCapacity()));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
