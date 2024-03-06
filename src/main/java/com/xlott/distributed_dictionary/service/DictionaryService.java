package com.xlott.distributed_dictionary.service;

import com.xlott.distributed_dictionary.model.Dictionary;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.rmi.Naming;
import java.util.Map;

@Service
public class DictionaryService {

    private Dictionary dictionary;

    public DictionaryService() {
        try {
            dictionary = (Dictionary) Naming.lookup("rmi://localhost/dictionary_app");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao servidor", e);
        }
    }

    public void add(String word, String definition) {
        try {
            dictionary.add(word, definition);
        } catch (Exception e) {
            throw new RuntimeException("Erro: não foi possível adicionar palavra", e);
        }
    }

    public void remove(String word) {
        try {
            dictionary.remove(word);
        } catch (Exception e) {
            throw new RuntimeException("Erro: não foi possível remover palavra", e);
        }
    }

    public String lookUp(String word) {
        try {
            return dictionary.lookUp(word);
        } catch (Exception e) {
            throw new RuntimeException("Erro: não foi possível pesquisar palavra", e);
        }
    }

    @GetMapping
    public Map<String, String> getDictionary()  {
        try {
            return dictionary.getDictionary();
        } catch (Exception e) {
            throw new RuntimeException("Erro: não foi possível retornar dicionário", e);
        }
    }
}