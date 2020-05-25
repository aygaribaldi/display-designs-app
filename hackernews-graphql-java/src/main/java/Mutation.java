import com.coxautodev.graphql.tools.GraphQLRootResolver;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.ZoneOffset;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {

    private final DesignRepository designRepository;
    private final UserRepository userRepository;

    public Mutation(DesignRepository designRepository, UserRepository userRepository) {
        this.designRepository = designRepository;
        this.userRepository = userRepository;
    }

    public Design createDesign(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        System.out.println("context: " + context);
        System.out.println("getUser: " + context.getUser());
        System.out.println("getID: " + context.getUser().getId());
        Design newDesign = new Design(url, description, context.getUser().getId());
        designRepository.saveDesign(newDesign);
        return newDesign;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
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