package prolab3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.PrintWriter;



public class Prolab3 {
    
   private static void printCommonWordsForUser(OurHashMap<String, User> userHashMap, String username, Set<String> excludeWords) {
        if (userHashMap.containsKey(username)) {
            User user = userHashMap.get(username);
            String[] currentTweets = user.getTweets();
            String allUserTweets = String.join(" ", currentTweets);

            Set<String> commonWords = new HashSet<>(Arrays.asList(allUserTweets.split("\\s+")));
            commonWords.removeAll(excludeWords);
            
            System.out.println(user.getUsername() + " kullanıcısının ortak kelimeleri: " + String.join(", ", commonWords));
        } else {
            System.out.println("Kullanıcı bulunamadı!");
        }
    }

    public static void main(String[] args) {
        OurHashMap<String, User> userHashMap = readTwitterDataFromJson("data_twitter.json");
 
        if (isUserExists(userHashMap, "hluettgen")) {
            User hashMapUser = userHashMap.get("hluettgen");
            System.out.println("Kullanıcı Hash Tablosunda Bulundu!");
            System.out.println("Kullanıcı Bilgileri:");
            System.out.println("Ad: " + hashMapUser.getName());
            System.out.println("Kullanıcı Adı: " + hashMapUser.getUsername());
            System.out.println("Dil: " + hashMapUser.getLanguage());
            System.out.println("Bölge: " + hashMapUser.getRegion());
            System.out.println("Takipçi Sayısı: " + hashMapUser.getFollowers_count());
            System.out.println("Takip Edilen Sayısı: " + hashMapUser.getFollowing_count());
            System.out.println("Takipçiler: " + Arrays.toString(hashMapUser.getFollowers()));
            System.out.println("Takip Edilenler: " + Arrays.toString(hashMapUser.getFollowing()));
            System.out.println("Tweetler: " + Arrays.toString(hashMapUser.getTweets()));
         } else {
            System.out.println("Kullanıcı bulunamadı!");
            
}
       
        UserGraph userGraph = UserGraphBuilder.buildUserGraph(userHashMap);

        try {
            writeUserRelationshipsToFile(userGraph, "graf_sonucu.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       //istediğimiz 2 kullanıcının birbirini takip etme durumu
        String user1 = "ymohr";
        String user2 = "alice85";

        boolean user1FollowsUser2 = userGraph.getFollowers(user1).contains(user2);
        boolean user2FollowsUser1 = userGraph.getFollowers(user2).contains(user1);

        
        if (!user1FollowsUser2 && !user2FollowsUser1) {
            System.out.println(user1 + " ve " + user2 + " birbirini takip etmiyor.");
        } else if (user1FollowsUser2 && !user2FollowsUser1) {
            System.out.println(user1 + ", " + user2 + "'yi takip ediyor, ancak " + user2 + " " + user1 + "'i takip etmiyor.");
        } else if (!user1FollowsUser2 && user2FollowsUser1) {
            System.out.println(user2 + ", " + user1 + "'i takip ediyor, ancak " + user1 + " " + user2 + "'yi takip etmiyor.");
        } else {
            System.out.println(user1 + " ve " + user2 + " birbirini takip ediyor.");
        }
        

        String usernameToCheck = "hluettgen";
 
           // ortak kelimelerde harici tutmak istediklerimiz
        Set<String> excludeWords = new HashSet<>(Arrays.asList("bu", "da", "olarak","hiç","o","şu", "şey", "bir","gibi","çok"));
        printCommonWordsForUser(userHashMap, usernameToCheck, excludeWords);
        
    }

    private static OurHashMap<String, User> readTwitterDataFromJson(String filePath) {
        OurHashMap<String, User> userHashMap = new OurHashMap<>();
        try {
            File file = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(file);

            for (JsonNode userNode : root) {
                User user = new User();
                user.setName(getTextValue(userNode, "name"));
                user.setUsername(getTextValue(userNode, "username"));
                user.setLanguage(getTextValue(userNode, "language"));
                user.setRegion(getTextValue(userNode, "region"));
                user.setFollowers_count(getIntValue(userNode, "followers_count"));
                user.setFollowing_count(getIntValue(userNode, "following_count"));

                String[] followers = getStringArrayValues(userNode, "followers");
                user.setFollowers(followers);

                String[] following = getStringArrayValues(userNode, "following");
                user.setFollowing(following);

                String[] tweets = getStringArrayValues(userNode, "tweets");
                user.setTweets(tweets);

                userHashMap.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userHashMap;}

    private static String getTextValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : null;
    }

    private static int getIntValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asInt() : 0;
    }

    private static String[] getStringArrayValues(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        String[] values = null;

        if (fieldNode != null && fieldNode.isArray()) {
            values = new String[fieldNode.size()];
            for (int i = 0; i < fieldNode.size(); i++) {
                values[i] = fieldNode.get(i).asText();
            }
        }

        return values;
    }

    private static boolean isUserExists(OurHashMap<String, User> userHashMap, String username) 
    {
        return userHashMap.containsKey(username);
    }
   
    private static void writeUserRelationshipsToFile(UserGraph userGraph, String fileName) throws IOException 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName)))) {
            writer.println("User Relationships:");

            for (String user : userGraph.getUsers()) {
                Set<String> followers = userGraph.getFollowers(user);
                writer.println(user + " is followed by: " + followers);
            }
            System.out.println("\n");
            System.out.println("Kullanıcı ilişkileri " + fileName + " dosyasına yazıldı.");
            System.out.println("\n");
        }
    }
    
 
}




