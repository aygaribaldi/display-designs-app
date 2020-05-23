
import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final DesignRepository designRepository;
    private static final UserRepository userRepository;

    static {
        // Change to `new MongoClient("<host>:<port>")`
        // if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("designs");
        designRepository = new DesignRepository(mongo.getCollection("images"));
        userRepository = new UserRepository(mongo.getCollection("users"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser().file("schema.graphqls")
                .resolvers(new Query(designRepository), new Mutation(designRepository, userRepository),
                        new SigninResolver(), new DesignResolver(userRepository))
                .build().makeExecutableSchema();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
            Optional<HttpServletResponse> response) {
        User user = request.map(req -> req.getHeader("Authorization")).filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", "")).map(userRepository::findById).orElse(null);
        return new AuthContext(user, request, response);
    }

}