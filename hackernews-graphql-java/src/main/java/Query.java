import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final DesignRepository designRepository;

    public Query(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    public List<Design> allDesigns() {
        return designRepository.getAllDesigns();
    }
}