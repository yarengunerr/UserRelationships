package prolab3;

import java.util.HashSet;
import java.util.Set;

class UserGraph 
        
{
    private final OurHashMap<String, Set<String>> userMap;

    public UserGraph() 
    {
        this.userMap = new OurHashMap<>();
    }

    public void addEdge(String follower, String following) 
    {
        Set<String> followers = userMap.getOrDefault(follower, new HashSet<>());
        followers.add(following);
        userMap.put(follower, followers);
    }

    public Set<String> getFollowers(String user) 
    {
        return userMap.getOrDefault(user, new HashSet<>());
    }

    public Set<String> getUsers() {
        return userMap.getAllKeys();
    }
    
}
