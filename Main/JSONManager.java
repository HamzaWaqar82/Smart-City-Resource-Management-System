package Main;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONManager {
    public static final Gson gson = new Gson();


    // Save to File
    public static void save(List<TransportUnit> list, String filename){
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(list, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


// Load from file
public static List<TransportUnit> load(String filename){
    try (FileReader reader = new FileReader(filename)) {

        Type listType = new TypeToken< List<TransportUnit> > () {}.getType();

        return gson.fromJson(reader, listType);

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
