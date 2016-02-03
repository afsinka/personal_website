package db;

/**
 * explicitly use to add user to DB
 */
public class HashPassword {

	public static void main(String[] args) {

		final String userName = "a2";
		final String myPassword = "b2";
		final byte[] salt = SecureUtil.getNextSalt();
		final byte[] hash = SecureUtil.hash(myPassword.toCharArray(), salt);

		addUser(userName, salt, hash);
	}

	private static void addUser(String userName, byte[] salt, byte[] hash) {
		MongoDBClient.addUser(userName, salt, hash);
	}

}
