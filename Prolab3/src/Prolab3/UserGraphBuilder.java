package prolab3;

public class UserGraphBuilder {

    public static UserGraph buildUserGraph(OurHashMap<String, User> userHashMap) {
        UserGraph userGraph = new UserGraph();

        for (String username : userHashMap.getAllKeys()) {
            User user = userHashMap.get(username);

            for (String following : user.getFollowing()) {
                userGraph.addEdge(username, following);
            }

            for (String follower : user.getFollowers()) {
                userGraph.addEdge(follower, username);
            }
        }

        return userGraph;
    }

    
}
