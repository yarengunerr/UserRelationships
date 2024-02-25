package prolab3;

class User 
{
    private String name;
    private String username;
    private String language;
    private String region;
    private int followers_count;
    private int following_count;
    private String[] followers;
    private String[] following;
    private String[] tweets;

    public User() 
    {
        // kurucu metodumuz
    }

    public User(String name, String username, String language, String region,
                int followers_count, int following_count, String[] tweets) {
        this.name = name;
        this.username = username;
        this.language = language;
        this.region = region;
        this.followers_count = followers_count;
        this.following_count = following_count;
        this.tweets = tweets;
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String[] getFollowers() 
    {
        return followers;
    }

    public void setFollowers(String[] followers) 
    {
        this.followers = followers;
    }

    public String[] getFollowing() 
    {
        return following;
    }

    public void setFollowing(String[] following) 
    {
        this.following = following;
    }

    public String[] getTweets() 
    {
        return tweets;
    }

    public void setTweets(String[] tweets) 
    {
        this.tweets = tweets;
    }

    public String getLanguage() 
    {
        return language;
    }

    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getRegion() 
    {
        return region;
    }

    public void setRegion(String region) 
    {
        this.region = region;
    }

    public int getFollowers_count() 
    {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) 
    {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() 
    {
        return following_count;
    }

    public void setFollowing_count(int following_count) 
    {
        this.following_count = following_count;
    }
    
}