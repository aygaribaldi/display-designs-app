import com.coxautodev.graphql.tools.GraphQLRootResolver;

import org.jetbrains.annotations.Nullable;

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

    public boolean deleteDesign(String id) {
        designRepository.deleteById(id);
        return true;
    }

    public Design editDesign(String id, String url, String description) {
        Design updatedDesign = new Design(id, url, description);
        if (!url.isEmpty())
            designRepository.updateUrl(id, url);
        if (!description.isEmpty())
            designRepository.updateDescription(id, description);
        return updatedDesign;
    }
}