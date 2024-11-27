/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistance;

import Entities.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author sarai
 */
public class UsersRepository {

    private final MongoDatabase dataBase;
    private final MongoCollection<Document> userCollection;

    public UsersRepository() {
        var client = MongoClients.create("mongodb+srv://sarairestrepo:admin@cluster0.udbe6.mongodb.net/");
        dataBase = client.getDatabase("UsersData");
        userCollection = dataBase.getCollection("User");
    }

    // Read all Users
    public List<User> getUsers() {
        FindIterable<Document> documents = userCollection.find();
        List<User> users = new ArrayList<>();

        for (Document doc : documents) {
            users.add(User.fromDocument(doc));
        }

        return users;
    }

    public Document login(String user, String pass) {

        var filter = Filters.and(
                Filters.or(
                        Filters.eq("email", user),
                        Filters.eq("username", user)
                ),
                Filters.eq("password", pass)
        );

        Document userDoc = userCollection.find(filter).first();

        System.out.println("Hola soy documentooooo: " + userDoc);

        return userDoc;

    }

    // Read Users from username/email
    public String getUserID(String user) {
        var filter = Filters.or(
                Filters.eq("email", user),
                Filters.eq("username", user)
        );
        Document doc = userCollection.find(filter).first();
        System.out.println("Soy el documento: " + doc);
        return doc != null ? doc.getObjectId("_id").toHexString() : null;
    }

    // Create User (Register - Sing up) 
    public void addUser(User user) {
        Document document = user.toDocument();
        userCollection.insertOne(document);
        System.out.println("Usuario added: " + document);

    }

    // Update User (Edit user information) 
    public void updateUser(String id, String name, String pass) {

        ObjectId objectId = new ObjectId(id);
        userCollection.updateOne(
                Filters.eq("_id", objectId), combine(
                set("fullname", name),
                set("password", pass))
        );
        System.out.println("User update: " + name + "pass: " + pass);
    }

    // Delete User (In case if we use an administrator role)
    public void deleteUser(int id) {
        userCollection.deleteOne(Filters.eq("id", id));
        System.out.println("Product deleted: " + id);
    }
}
