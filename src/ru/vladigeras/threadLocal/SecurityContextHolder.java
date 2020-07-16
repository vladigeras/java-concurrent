package ru.vladigeras.threadLocal;

/**
 * @author vladi_geras on 16.07.2020
 */
public class SecurityContextHolder {
	private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

	private SecurityContextHolder() {
	}

	public static User getCurrentUser() {
		return CURRENT_USER.get();
	}

	public static void setCurrentUser(User user) {
		if (getCurrentUser() == null) {
			System.out.println(String.format("Security context hasn't user for %s", Thread.currentThread().getName()));
		}
		CURRENT_USER.set(user);
		System.out.println(String.format("Security context now has user %s for %s", user, Thread.currentThread().getName()));
	}
}
