package db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBClient {

	private static MongoClient mongoClient = null;
	private static MongoDatabase database = null;
	private static final String url = "localhost";
	private static final int port = 27017;
	private static final String dbName = "testdb_1";
	private static final String messagesCollection = "messages";
	private static final String usersCollection = "users";

	private static final Logger logger = LogManager.getLogger(MongoDBClient.class);

	private static void connect() {
		logger.debug("trying to connect MongoDB...");

		mongoClient = new MongoClient(url, port);
		database = mongoClient.getDatabase(dbName);

		logger.debug("connected to MongoDB!");
	}

	private static void close() {
		mongoClient.close();
	}

	public static void save(String name, String message) {

		connect();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());

		MongoCollection<Document> collection = database.getCollection(messagesCollection);
		Document doc = new Document("name", name).append("message", message).append("date", date);
		collection.insertOne(doc);

		close();

	}

	public static void addUser(String name, byte[] salt, byte[] hash) {

		connect();

		String saltStr = Base64.getEncoder().encodeToString(salt);
		String hashStr = Base64.getEncoder().encodeToString(hash);

		MongoCollection<Document> collection = database.getCollection(usersCollection);
		Document doc = new Document("username", name).append("salt", saltStr).append("hash", hashStr);
		collection.insertOne(doc);

		close();

	}

	private static JSONObject getAdmin() {
		connect();
		MongoCollection<Document> collection = database.getCollection(usersCollection);
		Document doc = collection.find().first();
		close();
		JSONObject jsonObject = new JSONObject(doc.toJson());

		return jsonObject;
	}

	public static boolean auth(String username, String password) {
		JSONObject adminJSON = getAdmin();

		String usernameJSON = null;
		String saltJSON = null;
		String hashJSON = null;

		if (adminJSON.has("username") && !adminJSON.isNull("username")) {
			usernameJSON = adminJSON.getString("username");
		}

		if (adminJSON.has("salt") && !adminJSON.isNull("salt")) {
			saltJSON = adminJSON.getString("salt");
		}

		if (adminJSON.has("hash") && !adminJSON.isNull("hash")) {
			hashJSON = adminJSON.getString("hash");
		}

		if (usernameJSON != null && usernameJSON.equals(username) && SecureUtil.isExpectedPassword(
				password.toCharArray(), Base64.getDecoder().decode(saltJSON), Base64.getDecoder().decode(hashJSON))) {
			return true;
		} else {
			return false;
		}

	}

	public static JSONArray getMessages() {
		connect();

		JSONArray jsonArray = new JSONArray();

		MongoCollection<Document> collection = database.getCollection(messagesCollection);
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				jsonArray.put(new JSONObject(cursor.next().toJson()));
			}
		} finally {
			cursor.close();
		}

		close();

		return jsonArray;
	}

}
