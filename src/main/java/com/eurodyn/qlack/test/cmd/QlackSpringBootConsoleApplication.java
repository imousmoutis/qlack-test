package com.eurodyn.qlack.test.cmd;

import com.eurodyn.qlack.test.cmd.services.aaa.UserServiceTest;
import com.eurodyn.qlack.test.cmd.services.audit.AuditLevelServiceTest;
import com.eurodyn.qlack.test.cmd.services.audit.AuditServiceTest;
import com.eurodyn.qlack.test.cmd.services.lexicon.KeyServiceTest;
import com.eurodyn.qlack.test.cmd.services.lexicon.LanguageServiceTest;
import com.eurodyn.qlack.test.cmd.services.mailing.InternalMessageServiceTest;
import com.eurodyn.qlack.test.cmd.services.mailing.MailServiceTest;
import com.eurodyn.qlack.test.cmd.services.rules.RulesServiceTest;
import com.eurodyn.qlack.test.cmd.services.scheduler.SchedulerServiceTest;
import com.eurodyn.qlack.test.cmd.services.search.AdminServiceTest;
import com.eurodyn.qlack.test.cmd.services.search.IndexingServiceTest;
import com.eurodyn.qlack.test.cmd.services.search.SearchServiceTest;
import com.eurodyn.qlack.test.cmd.services.settings.SettingsServiceTest;
import com.eurodyn.qlack.test.cmd.services.workflow.WorkflowServiceTest;
import com.eurodyn.qlack.test.cmd.services.workflow.WorkflowTaskServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories({
    "com.eurodyn.qlack.fuse.aaa.repository",
    "com.eurodyn.qlack.fuse.audit.repository",
    "com.eurodyn.qlack.fuse.lexicon.repository",
    "com.eurodyn.qlack.fuse.mailing.repository",
    "com.eurodyn.qlack.fuse.rules.repository",
    "com.eurodyn.qlack.fuse.settings.repository",
    "com.eurodyn.qlack.fuse.workflow.repository",
    "com.eurodyn.qlack.test.cmd.repository"
})
@EntityScan({
    "com.eurodyn.qlack.fuse.aaa.model",
    "com.eurodyn.qlack.fuse.audit.model",
    "com.eurodyn.qlack.fuse.lexicon.model",
    "com.eurodyn.qlack.fuse.mailing.model",
    "com.eurodyn.qlack.fuse.rules.model",
    "com.eurodyn.qlack.fuse.settings.model",
    "com.eurodyn.qlack.fuse.workflow.model",
    "com.eurodyn.qlack.test.cmd.model"
})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.fuse.aaa",
    "com.eurodyn.qlack.fuse.audit",
    "com.eurodyn.qlack.fuse.lexicon",
    "com.eurodyn.qlack.fuse.mailing",
    "com.eurodyn.qlack.fuse.rules",
    "com.eurodyn.qlack.fuse.scheduler",
    "com.eurodyn.qlack.fuse.settings",
    "com.eurodyn.qlack.fuse.search",
    "com.eurodyn.qlack.fuse.security",
    "com.eurodyn.qlack.fuse.workflow",
    "com.eurodyn.qlack.test.cmd",
    "com.eurodyn.qlack.fuse.crypto.service"
})

@EnableElasticsearchRepositories({
    "com.eurodyn.qlack.test.cmd.repository"
})

public class QlackSpringBootConsoleApplication implements CommandLineRunner {

  private final ConfigurableApplicationContext context;
  private final UserServiceTest userServiceTest;
  private final AuditServiceTest auditServiceTest;
  private final AuditLevelServiceTest auditLevelServiceTest;
  private final SettingsServiceTest settingsServiceTest;
  private final MailServiceTest mailServiceTest;
  private final InternalMessageServiceTest internalMessageServiceTest;
  private final LanguageServiceTest languageServiceTest;
  private final KeyServiceTest keyServiceTest;
  private final AdminServiceTest adminServiceTest;
  private final IndexingServiceTest indexingServiceTest;
  private final SearchServiceTest searchServiceTest;
  private final SchedulerServiceTest schedulerServiceTest;
  private final RulesServiceTest rulesServiceTest;
  private final WorkflowServiceTest workflowServiceTest;
  private final WorkflowTaskServiceTest workflowTaskServiceTest;

  @Autowired
  public QlackSpringBootConsoleApplication(ConfigurableApplicationContext context,
      @Lazy UserServiceTest userServiceTest, AuditServiceTest auditServiceTest,
      AuditLevelServiceTest auditLevelServiceTest, SettingsServiceTest settingsServiceTest,
      MailServiceTest mailServiceTest, InternalMessageServiceTest internalMessageServiceTest,
      LanguageServiceTest languageServiceTest, KeyServiceTest keyServiceTest,
      AdminServiceTest adminServiceTest, IndexingServiceTest indexingServiceTest,
      SearchServiceTest searchServiceTest, SchedulerServiceTest schedulerServiceTest,
      RulesServiceTest rulesServiceTest, @Lazy WorkflowServiceTest workflowServiceTest,
      @Lazy WorkflowTaskServiceTest workflowTaskServiceTest) {
    this.context = context;
    this.userServiceTest = userServiceTest;
    this.auditServiceTest = auditServiceTest;
    this.auditLevelServiceTest = auditLevelServiceTest;
    this.settingsServiceTest = settingsServiceTest;
    this.mailServiceTest = mailServiceTest;
    this.internalMessageServiceTest = internalMessageServiceTest;
    this.languageServiceTest = languageServiceTest;
    this.keyServiceTest = keyServiceTest;
    this.adminServiceTest = adminServiceTest;
    this.indexingServiceTest = indexingServiceTest;
    this.searchServiceTest = searchServiceTest;
    this.schedulerServiceTest = schedulerServiceTest;
    this.rulesServiceTest = rulesServiceTest;
    this.workflowServiceTest = workflowServiceTest;
    this.workflowTaskServiceTest = workflowTaskServiceTest;
  }

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(QlackSpringBootConsoleApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  public void run(String... args) {
    if (args.length == 0) {
      System.out.println("Please provide the service name you want to test as the first argument.");
    } else {

      switch (args[0]) {
        case "AAAService":
          userServiceTest.createUser();
          //userServiceTest.updateUser();
          //userServiceTest.getUserByName();
          //userServiceTest.deleteUser();
          break;
        case "AuditService":
          auditLevelServiceTest.addLevelIfNotExists();
          auditLevelServiceTest.listAuditLevels();
          auditLevelServiceTest.getAuditLevelByName();
          auditServiceTest.audit();
          auditServiceTest.getAuditLogs();
          break;
        case "LexiconService":
          languageServiceTest.createLanguageIfNotExists();
          languageServiceTest.downloadLanguage(languageServiceTest.getLanguage().getId());
          languageServiceTest.deactivateLanguage();
          languageServiceTest.getLanguages();
          keyServiceTest.createKey();
          keyServiceTest.getTranslationsForLocale();
          break;
        case "MailingService":
          mailServiceTest.queueEmail();
          internalMessageServiceTest.sendInternalMail();
          break;
        case "RulesService":
          rulesServiceTest.fireActivateRulesFromResources();
          rulesServiceTest.fireDeactivateRulesFromResources();
          rulesServiceTest.statelessExecute(null);
          rulesServiceTest.createKnowledgeBase();
          rulesServiceTest.statelessExecuteGetResults();
          break;
        case "SchedulerService":
          schedulerServiceTest.listAllJobs();
          schedulerServiceTest.scheduleJobs();
          schedulerServiceTest.triggerJob();
          schedulerServiceTest.deleteJob();
          break;
        case "SearchService":
          if (adminServiceTest.checkIsUp()) {
            adminServiceTest.createIndex();
            adminServiceTest.openAndCloseIndex();

            indexingServiceTest.indexDocument();
            indexingServiceTest.indexByRepo();

            indexingServiceTest.deleteFromRepo();
            indexingServiceTest.unindexDocument();

            searchServiceTest.searchQueryRange();
          } else {
            System.out.println("Elastic cluster is down.");
          }
          break;
        case "SettingsService":
          settingsServiceTest.createSetting();
          settingsServiceTest.getSettings();
          break;
        case "WorkflowService":
          workflowServiceTest.startWorkflowInstance();
          workflowTaskServiceTest.testProcedure();
          workflowServiceTest.getProcessInstancesByProcessId();
          workflowServiceTest.getProcessHistory("two-tasks-process");
          workflowServiceTest.updateProcessesFromResources();
          break;
        default:
          System.out.println("Service " + args[0] + " is not found :(");
      }
    }

    SpringApplication.exit(context);
    System.exit(0);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
