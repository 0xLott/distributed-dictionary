package com.xlott.distributed_dictionary.rmi;

import com.xlott.distributed_dictionary.model.Dictionary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DictionaryServant extends UnicastRemoteObject implements Dictionary {

    private Map<String, String> dictionary;
    private ObjectMapper mapper;

    public DictionaryServant() throws RemoteException {
        // Inicializar campos de UnicastRemoteObject e da classe atual
        super();
        this.dictionary = new HashMap<>();
        this.mapper = new ObjectMapper();

        // Ler arquivo JSON para carregar dados do Map dictionary
        try {
            loadFromJson("src/main/resources/data.json");
            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro: não foi possível ler os dados em JSON. + e.getMessage()");
        }
    }

    @Override
    public void add(String word, String definition) throws RemoteException {
        dictionary.put(word, definition);
    }

    @Override
    public void remove(String word) throws RemoteException {
        if (dictionary.containsKey(word))
            dictionary.remove(word);
    }

    @Override
    public String lookUp(String word) throws RemoteException {
        if (dictionary.containsKey(word)) {
            return dictionary.get(word);
        } else {
            return null;
        }
    }

    /**
     * Utiliza a estrutura de dados TreeMap para preservar a ordem alfabética das keys.
     * @return Cópia ordenada do HashMap dictionary
     */
    @Override
    public Map<String, String> getDictionary() throws RemoteException {
        return new TreeMap<>(this.dictionary);
    }

    private void loadFromJson(String filePath) throws IOException {
        JsonNode rootNode = mapper.readTree(new File(filePath));

        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            dictionary.put(entry.getKey(), entry.getValue().asText());
        }
    }
}