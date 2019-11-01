package ca.uvic.k4ncelled.Data;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.uvic.k4ncelled.Backend.Fridge;

public class Data {
    private final String FILENAME = "fridgeData";

    private Context context;

    public Data(Context context){
        this.context = context;
    }

    public void save(Fridge fridge){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(fridge);
            os.close();
            fos.close();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public Fridge load(){
        Fridge fridge = new Fridge();
        if((new File(context.getFilesDir(), FILENAME)).exists()) {
            try {
                FileInputStream fis = context.openFileInput(FILENAME);
                ObjectInputStream is = new ObjectInputStream(fis);
                fridge = (Fridge) is.readObject();
                is.close();
                fis.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return fridge;
    }
}
