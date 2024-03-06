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

    @PostMapping("/deleteEntry")
    public String deleteEntry(@RequestParam String word) {

        try {
            dictionaryService.remove(word);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Redirecionar para página inicial
        return "redirect:/dictionary";
    }
}