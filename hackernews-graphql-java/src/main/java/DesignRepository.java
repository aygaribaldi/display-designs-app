import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

public class DesignRepository {

    private final MongoCollection<Document> designs;

    public DesignRepository(MongoCollection<Document> designs) {
        this.designs = designs;
    }

    public Design findById(String id) {
        Document doc = designs.find(eq("_id", new ObjectId(id))).first();
        return design(doc);
    }

    public void deleteById(String id) {
        designs.deleteOne(eq("_id", new ObjectId(id)));
    }

    public List<Design> getAllDesigns() {
        List<Design> allDesigns = new ArrayList<>();
        for (Document doc : designs.find()) {
            allDesigns.add(design(doc));
        }
        return allDesigns;
    }

    public void saveDesign(Design design) {
        Document doc = new Document();
        doc.append("url", design.getUrl());
        doc.append("description", design.getDescription());
        designs.insertOne(doc);
    }

    private Design design(Document doc) {
        return new Design(doc.get("_id").toString(), doc.getString("url"), doc.getString("description"));
    }
}