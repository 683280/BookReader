package com.carljay.cjlibrary.uitls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils{
	public static byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }   
		public static Object toObject(byte[] bytes) {      
			ByteArrayInputStream bos = new ByteArrayInputStream(bytes);      
			try {        
				ObjectInputStream oos = new ObjectInputStream(bos);   
				Object readObject = oos.readObject();
				oos.close();         
				bos.close();
				return readObject;
			} catch (IOException ex) {        
				ex.printStackTrace();   
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}      
			return null;    
		}   
}
