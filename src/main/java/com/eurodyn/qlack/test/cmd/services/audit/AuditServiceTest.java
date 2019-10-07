package com.eurodyn.qlack.test.cmd.services.audit;

import com.eurodyn.qlack.fuse.audit.dto.AuditDTO;
import com.eurodyn.qlack.fuse.audit.dto.AuditTraceDTO;
import com.eurodyn.qlack.fuse.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceTest {

  private final AuditService auditService;

  private final AuditDTO auditDTO = createAuditDTO();

  @Autowired
  public AuditServiceTest(AuditService auditService) {
    this.auditService = auditService;
  }

  public void audit() {
    System.out.println("******************");
    System.out.println("Testing audit method.");

    String auditId = auditService.audit(auditDTO);
    System.out.println("Audit with id " + auditId + " has been created.");

    System.out.println("******************");
  }

  public void getAuditLogs() {
    System.out.println("******************");
    System.out.println("Testing getAuditLogs method.");

    Page<AuditDTO> audits = auditService.getAuditLogs(PageRequest.of(0, 10), null);
    System.out.println("Found " + audits.getTotalElements() + " audits.");

    deleteAudit(audits.get().findFirst().get().getId());

    System.out.println("******************");
  }

  private void deleteAudit(String auditId) {
    System.out.println("******************");
    System.out.println("Testing deleteAudit method.");

    auditService.deleteAudit(auditId);
    System.out.println("Audit with id " + auditId + " is deleted.");

    System.out.println("******************");
  }

  private AuditTraceDTO createAuditTraceDTO() {
    AuditTraceDTO auditTraceDTO = new AuditTraceDTO();
    auditTraceDTO.setTraceData("{\n" +
        "\tcolor: \"red\",\n" +
        "\tvalue: \"#f00\"\n" +
        "}");
    return auditTraceDTO;
  }

  private AuditDTO createAuditDTO() {
    AuditDTO auditDTO = new AuditDTO();
    auditDTO.setLevel("Back End");
    auditDTO.setEvent("System Check");
    auditDTO.setShortDescription("Daily user check of the system.");
    auditDTO.setGroupName("BackEnd Audit group");
    auditDTO.setPrinSessionId("537925a3-80ae-447d-837c-e092a2e8f38e");
    auditDTO.setOpt1("new users: 3");
    auditDTO.setOpt2("deleted users: 0");
    auditDTO.setOpt3("expired users: 1");
    auditDTO.setReferenceId("1111");
    auditDTO.setCreatedOn(1625145120000L);
    auditDTO.setTrace(createAuditTraceDTO());

    return auditDTO;
  }
}
