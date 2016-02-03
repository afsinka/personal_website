package db;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBClient {

	private static MongoClient mongoClient = null;
	private static MongoDatabase database = null;
	private static final String url = "localhost";
	private static final int port = 27017;
	private static final String dbName = "testdb_1";
	private static final String messagesCollection = "messages";

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

	private static JSONObject getAdmin() {
		connect();
		MongoCollection<Document> collection = database.getCollection("users");
		Document doc = collection.find().first();
		close();
		JSONObject jsonObject = new JSONObject(doc.toJson());

		return jsonObject;
	}

	public static boolean auth(String username, String password) {
		JSONObject adminJSON = getAdmin();

		String usernameJSON = null;
		String passwordJSON = null;

		if (adminJSON.has("username") && !adminJSON.isNull("username")) {
			usernameJSON = adminJSON.getString("username");
		}

		if (adminJSON.has("password") && !adminJSON.isNull("password")) {
			passwordJSON = adminJSON.getString("password");
		}

		if (usernameJSON != null && usernameJSON.equals(username) && passwordJSON != null
				&& passwordJSON.equals(password)) {
			return true;
		} else {
			return false;
		}

	}


}
