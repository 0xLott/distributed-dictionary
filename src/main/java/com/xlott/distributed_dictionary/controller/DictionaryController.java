package com.xlott.distributed_dictionary.controller;

import com.xlott.distributed_dictionary.service.DictionaryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary")
    public String displayTable(Model model) {
        Map<String, String> allEntries = dictionaryService.getDictionary();
        model.addAttribute("dictionary", allEntries);
        return "dictionary";
    }

    @GetMapping("/lookUpEntry")
    public String lookUp(Model model, @RequestParam String lookedUpWord) {
        String result = null;

        try {
            result = dictionaryService.lookUp(lookedUpWord);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        model.addAttribute("lookedUpWord", lookedUpWord);  // Update to "lookedUpWord"
        model.addAttribute("definition", result);  // Update to "definition"
        return "word_definition";
    }


    @PostMapping("/addEntry")
    public String addEntry(@RequestParam String word, String definition) {
        try {
            dictionaryService.add(word, definition);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Redirecionar para página inicial
        return "redirect:/dictionary";
    }

    @PostMapping("/deleteEntry")
    public String deleteEntry(@RequestParam String deletedWord) {
        try {
            dictionaryService.remove(deletedWord);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Redirecionar para página inicial
        return "redirect:/dictionary";
    }
}