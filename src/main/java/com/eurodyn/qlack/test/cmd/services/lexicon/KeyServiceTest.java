package com.eurodyn.qlack.test.cmd.services.lexicon;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author European Dynamics
 */

@Service
public class KeyServiceTest {

    private KeyService keyService;
    private LanguageService languageService;
    private GroupService groupService;


    @Autowired
    public KeyServiceTest(KeyService keyService, LanguageService languageService, GroupService groupService) {
        this.keyService = keyService;
        this.languageService = languageService;
        this.groupService = groupService;
    }

    public void createKey() {
        System.out.println("******************");
        System.out.println("Testing createKey method.");
        try {
            String key = keyService.createKey(createKeyDTO(), false);
            System.out.println("Created key with id:" + key);
        } catch (QAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("******************");
    }

    public void updateTranslation() {
        String UPDATED_TRANSLATION = "Add attachment description here";
        keyService.updateTranslation(
            keyService.getKeyByName("attach_desc", groupService.getGroupByName("ui").getId(), false).getId(),
            languageService.getLanguageByLocale("en").getId(), UPDATED_TRANSLATION);
    }

    private KeyDTO createKeyDTO() {
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setName("contact_us");

        Map<String, String> translations = new HashMap<>();
        translations.put(languageService.getLanguageByLocale("en").getId(), "Please contact our team");
        translations.put(languageService.getLanguageByLocale("fr").getId(), "S'il vous plaît contacter notre équipe");
        keyDTO.setTranslations(null);
        return keyDTO;
    }

    public void getTranslationsForLocale() {
        System.out.println("******************");
        System.out.println("Testing getTranslationsForLocale method.");

        Map<String, String> englishTranslations = keyService.getTranslationsForLocale("en");
        System.out.println("Printing english translations:");
        englishTranslations.forEach((s, s2) -> System.out.println(s + " : " + s2));
        System.out.println("******************");
    }
}
