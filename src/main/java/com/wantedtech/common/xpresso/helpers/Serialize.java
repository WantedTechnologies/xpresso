package com.wantedtech.common.xpresso.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.wantedtech.common.xpresso.types.HappyFile;

public class Serialize {
	static void dump(Serializable object,HappyFile file) throws FileNotFoundException, IOException{
        ObjectOutputStream out = new ObjectOutputStream(file.getOutputStream());
        out.writeObject(object);
        out.close();
	}
	
	static <T> Object load(HappyFile file) throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(file.getInputStream());
        @SuppressWarnings("unchecked")
		T object = (T)in.readObject();
        in.close();
        return object;
	}
}
