package mcjty.arienteworld.cities;

import com.google.gson.JsonObject;

public interface IAsset {

    String getName();

    void readFromJSon(JsonObject object);
}
