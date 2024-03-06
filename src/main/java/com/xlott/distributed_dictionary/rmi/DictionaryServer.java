package com.xlott.distributed_dictionary.rmi;

import com.xlott.distributed_dictionary.model.Dictionary;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DictionaryServer {

    public DictionaryServer() {
        try {
            // 1099: porta default do RMI
            LocateRegistry.createRegistry(1099);

            Dictionary dict = new DictionaryServant();

            // "Binding" do objeto remoto para o objeto servant `dict`
            Naming.rebind("rmi://localhost/dictionary_app", dict);
            System.out.println("Server em execução.");

        } catch (Exception e) {
            System.out.println("Erro: Não foi possível inicializar o dicionário." + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DictionaryServer();
    }
}