package com.xlott.distributed_dictionary.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Dictionary extends Remote {
    public void add(String word, String definition) throws RemoteException;
    public void remove(String word) throws RemoteException;
    public String lookUp(String word) throws RemoteException;
    public Map<String, String> getDictionary() throws RemoteException;
}
