package com.psc.bumpy;


import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Save
{
    static void saveText(String data)
    {
        File myFile = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Enregistrements",Long.toString(new Date().getTime())+".csv");     //on déclare notre futur fichier

        File myDir = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Enregistrements");                                                //pour créer le repertoire dans lequel on va mettre notre fichier
        Boolean success=true;
        if (!myDir.exists()) {
            success = myDir.mkdir();                                                                //on crée le répertoire (s'il n'existe pas!)
        }
        if (success) {
            try {
                FileOutputStream output = new FileOutputStream(myFile, true);                       //le true est pour écrire en fin de fichier, et non l'écraser
                output.write(data.getBytes());
                Toast.makeText(WebManager.context, "Enregistré", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
            }
        }
    }
    static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                     //dd/mm/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
