package com.eurodyn.qlack.test.cmd.services.settings;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.settings.dto.SettingDTO;
import com.eurodyn.qlack.fuse.settings.service.SettingsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceTest {

  private final SettingsService settingsService;

  private final SettingDTO settingDTO = createSettingDTO();

  @Autowired
  public SettingsServiceTest(SettingsService settingsService) {
    this.settingsService = settingsService;
  }

  public void createSetting() {
    System.out.println("******************");
    System.out.println("Testing createSetting method.");

    try {
      settingsService.createSetting(settingDTO);
      System.out.println("A new setting has been added.");
    } catch (QAlreadyExistsException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("******************");
  }

  public void getSettings() {
    System.out.println("******************");
    System.out.println("Testing getSettings method.");

    List<SettingDTO> settings = settingsService.getSettings(settingDTO.getOwner(), false);
    System.out.println("Found " + settings.size() + " settings.");

    System.out.println("******************");
  }

  private SettingDTO createSettingDTO() {
    SettingDTO settingDTO = new SettingDTO();
    settingDTO.setKey("Setting Key 1");
    settingDTO.setGroup("Test Group");
    settingDTO.setOwner("Test Owner");
    settingDTO.setPassword(true);
    settingDTO.setCreatedOn(1625145120000L);
    return settingDTO;
  }

}
