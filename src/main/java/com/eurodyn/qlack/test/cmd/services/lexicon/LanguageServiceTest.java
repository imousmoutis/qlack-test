package com.eurodyn.qlack.test.cmd.services.lexicon;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author European Dynamics
 */
@Service
public class LanguageServiceTest {

    private LanguageService languageService;

    @Autowired
    public LanguageServiceTest(LanguageService languageService) {
        this.languageService = languageService;
    }

    public String createLanguageIfNotExists() {
        System.out.println("******************");
        System.out.println("Testing createLanguageIfNotExists method");
        String languageId = null;
        try {
            languageId = languageService.createLanguageIfNotExists(createLanguageDTO());
            System.out.println("Language ith id " + languageId + " has been created.");

        } catch (QAlreadyExistsException e) {
            System.out.println(e.getMessage());

        }

        System.out.println("******************");
        return languageId;
    }

    public LanguageDTO getLanguage() {
        return languageService.getLanguageByLocale("en");
    }

    public void downloadLanguage(String languageId) {
        System.out.println("******************");
        System.out.println("Testing downloadLanguage method");
        byte[] bytes = languageService.downloadLanguage(languageId);
        Path resourceDirectory = Paths.get("target", "eng_translations_generated.xls");
        try {
            Files.write(resourceDirectory, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("******************");
    }

    public void deactivateLanguage() {
        System.out.println("******************");
        System.out.println("Testing deactivateLanguage method");
        languageService.deactivateLanguage(languageService.getLanguageByLocale("gr").getId());
        System.out.println("******************");
    }

    public void getLanguages() {
        System.out.println("******************");
        System.out.println("Testing getLanguages method");
        List<LanguageDTO> languages = languageService.getLanguages(true);
        System.out.println("Language status: ");
        languages.forEach(languageDTO -> System.out.println(languageDTO.getName() + " is active: " + languageDTO.isActive()));
        System.out.println("******************");
    }

    private LanguageDTO createLanguageDTO() {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLocale("gr");
        languageDTO.setName("Greek");
        languageDTO.setActive(true);
        return languageDTO;
    }

}
