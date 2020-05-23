import com.coxautodev.graphql.tools.GraphQLResolver;

public class DesignResolver implements GraphQLResolver<Design> {

    private final UserRepository userRepository;

    public DesignResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User postedBy(Design design) {
        if (design.getUserId() == null) {
            return null;
        }
        return userRepository.findById(design.getUserId());
    }
}