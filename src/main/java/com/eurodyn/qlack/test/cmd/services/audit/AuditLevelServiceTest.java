package com.eurodyn.qlack.test.cmd.services.audit;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.audit.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.audit.service.AuditLevelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLevelServiceTest {

  private AuditLevelService auditLevelService;

  private final AuditLevelDTO auditLevelDTO = createAuditLevelDTO();

  @Autowired
  public AuditLevelServiceTest(AuditLevelService auditLevelService) {
    this.auditLevelService = auditLevelService;
  }

  public void addLevelIfNotExists() {
    System.out.println("******************");
    System.out.println("Testing addLevelIfNotExists method.");

    try {
      String levelId = auditLevelService.addLevelIfNotExists(auditLevelDTO);
      System.out.println("AuditLevel with id " + levelId + " has been created.");
    } catch (QAlreadyExistsException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("******************");
  }

  public void listAuditLevels() {
    System.out.println("******************");
    System.out.println("Testing listAuditLevels method.");

    List<AuditLevelDTO> auditLevels = auditLevelService.listAuditLevels();
    System.out.println("Found " + auditLevels.size() + " audit levels.");

    System.out.println("******************");
  }

  public void getAuditLevelByName() {
    System.out.println("******************");
    System.out.println("Testing getAuditLevelByName method.");

    AuditLevelDTO existingAuditLevel = auditLevelService
        .getAuditLevelByName(auditLevelDTO.getName());
    if (existingAuditLevel == null) {
      System.out.println("Audit level " + auditLevelDTO.getName() + " is not found.");
    } else {
      System.out.println(
          "Audit level " + auditLevelDTO.getName() + " has id " + existingAuditLevel.getId());
    }

    System.out.println("******************");
  }

  private AuditLevelDTO createAuditLevelDTO() {
    AuditLevelDTO auditLevelDTO = new AuditLevelDTO();
    auditLevelDTO.setId("8a882f69-147b-4d4c-a39e-76221b408644");
    auditLevelDTO.setName("Back End");
    auditLevelDTO.setDescription("Operations performed from the back end.");
    auditLevelDTO.setPrinSessionId("sessionId");
    auditLevelDTO.setCreatedOn(1625145120000L);
    return auditLevelDTO;
  }
}
