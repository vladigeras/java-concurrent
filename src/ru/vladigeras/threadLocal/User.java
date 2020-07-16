package ru.vladigeras.threadLocal;

/**
 * @author vladi_geras on 16.07.2020
 */
public class User {
	private final Long id;
	private final String name;

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
