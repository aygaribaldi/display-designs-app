import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.time.Instant;
import java.time.ZoneOffset;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {

    private final DesignRepository designRepository;

    public Mutation(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    public Design createDesign(String url, String description) {
        Design newDesign = new Design(url, description);
        designRepository.saveDesign(newDesign);
        return newDesign;
    }

    // public void editDesign(String id, String url, String description) {
    // // This replaces all old data, but some apps might want partial update.
    // Design currDesign = designRepository.findById(id);
    // System.out.println("CURRENT DESIGN" + currDesign);
    // currDesign.setUrl(url);
    // currDesign.setDescription(description);
    // designRepository.saveDesign(currDesign);
    // }
}