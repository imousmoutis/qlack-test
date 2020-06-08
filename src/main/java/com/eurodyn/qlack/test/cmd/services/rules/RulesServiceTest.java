package com.eurodyn.qlack.test.cmd.services.rules;

import com.eurodyn.qlack.fuse.rules.component.RulesComponent;
import com.eurodyn.qlack.fuse.rules.dto.ExecutionResultsDTO;
import com.eurodyn.qlack.fuse.rules.dto.KnowledgeBaseDTO;
import com.eurodyn.qlack.fuse.rules.service.KnowledgeBaseService;
import com.eurodyn.qlack.fuse.rules.service.KnowledgeSessionService;
import com.eurodyn.qlack.test.cmd.component.AccountComponent;
import com.eurodyn.qlack.test.cmd.model.Account;
import com.eurodyn.qlack.test.cmd.model.AccountStatus;
import com.eurodyn.qlack.test.cmd.repository.AccountRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kie.api.cdi.KContainer;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesServiceTest {

  private final AccountRepository accountRepository;

  private final AccountComponent accountComponent;

  private final RulesComponent rulesComponent;

  @KContainer
  private final KieContainer kieContainer;

  private final KnowledgeBaseService knowledgeBaseService;

  private final KnowledgeSessionService knowledgeSessionService;

  private final List<Account> accounts;

  private final List<String> rules = generateRules();

  @Autowired
  public RulesServiceTest(AccountRepository accountRepository, AccountComponent accountComponent,
      RulesComponent rulesComponent, KnowledgeBaseService knowledgeBaseService,
      KnowledgeSessionService knowledgeSessionService, KieContainer kieContainer) {
    this.accountRepository = accountRepository;
    this.accountComponent = accountComponent;
    this.rulesComponent = rulesComponent;
    this.knowledgeBaseService = knowledgeBaseService;
    this.knowledgeSessionService = knowledgeSessionService;
    this.kieContainer = kieContainer;
    this.accounts = accountRepository.findAll();
  }

  public void fireActivateRulesFromResources() {
    System.out.println("******************");
    System.out.println("Testing fireActivateRulesFromResources.");

    editAccountValues();
    Account account = accounts.get(1);

    KieSession kieSession = kieContainer.newKieSession("ksession-activate-rules");

    kieSession.insert(account);
    kieSession.fireAllRules();

    System.out.println("******************");
    revertAccountValues();
  }

  public void fireDeactivateRulesFromResources() {
    System.out.println("******************");
    System.out.println("Testing fireDeactivateRulesFromResources.");

    editAccountValues();
    Account account = accounts.get(0);

    KieSession kieSession = kieContainer.newKieSession("ksession-deactivate-rules");

    kieSession.setGlobal("accountComponent", accountComponent);
    kieSession.insert(account);
    kieSession.fireAllRules();

    System.out.println("******************");
    revertAccountValues();
  }

  public void statelessExecute(String knowledgeBaseId) {
    System.out.println("******************");
    System.out.println("Testing statelessExecute method.");

    editAccountValues();

    knowledgeSessionService.statelessExecute(null, null, rules, getGlobals(), getFacts(), null);

    System.out.println("******************");
    revertAccountValues();
  }

  public void createKnowledgeBase() {
    System.out.println("******************");
    System.out.println("Testing createKnowledgeBase method.");

    String knowledgeBaseId;

    List<KnowledgeBaseDTO> knowledgeBaseDTOs = getAllKnowledgeBases();
    if (knowledgeBaseDTOs.size() == 0) {
      knowledgeBaseId = knowledgeBaseService.createKnowledgeBase(new ArrayList<>(), rules);
    } else {
      knowledgeBaseId = knowledgeBaseDTOs.get(0).getId();
    }

    System.out.println("******************");
    statelessExecuteFromKnowledgeBase(knowledgeBaseId);
    statelessExecuteFromKnowledgeBaseExclude2Rules(knowledgeBaseId);
    fireRules(knowledgeBaseId);
  }

  public void statelessExecuteFromKnowledgeBase(String knowledgeBaseId) {
    System.out.println("******************");
    System.out.println("Testing statelessExecuteFromKnowledgeBase.");

    editAccountValues();

    knowledgeSessionService
        .statelessExecute(knowledgeBaseId, null, null, getGlobals(), getFacts(), null);

    System.out.println("******************");
    revertAccountValues();
  }

  public void statelessExecuteFromKnowledgeBaseExclude2Rules(String knowledgeBaseId) {
    System.out.println("******************");
    System.out.println("Testing statelessExecuteFromKnowledgeBaseExclude2Rules.");

    editAccountValues();

    knowledgeSessionService.statelessExecute(knowledgeBaseId, null, null, getGlobals(), getFacts(),
        "accountBalanceAtLeast100");

    System.out.println("******************");
    revertAccountValues();
  }

  public void fireRules(String knowledgeBaseId) {
    System.out.println("******************");
    System.out.println("Testing fireRules method.");

    editAccountValues();

    knowledgeSessionService.fireRules(knowledgeBaseId, null, getGlobals(), getFacts());

    System.out.println("******************");
    revertAccountValues();
  }

  public void statelessExecuteGetResults() {
    System.out.println("******************");
    System.out.println("Testing statelessExecuteGetResults.");

    List<String> newRules = new ArrayList<>();
    String newRule1 = "import com.eurodyn.qlack.test.cmd.model.Account\n"
        + "import com.eurodyn.qlack.test.cmd.model.AccountStatus\n"
        + "\n"
        + "rule \"accountHasNoBalance\"\n"
        + "  when\n"
        + "    $account : Account(balance == 0)\n"
        + "  then\n"
        + "    $account.setStatus(AccountStatus.ACTIVE);\n"
        + "    System.out.println(\"The account \" +$account.getId()+ \" has no balance.\");\n"
        + "end";
    newRules.add(newRule1);

    List<Account> newAccounts = new ArrayList<>(accounts);
    newAccounts.get(0).withdraw(newAccounts.get(0).getBalance());
    List<byte[]> newFacts = new ArrayList<>();
    newFacts.add(rulesComponent.serializeObject(newAccounts.get(0)));
    newFacts.add(rulesComponent.serializeObject(newAccounts.get(1)));

    ExecutionResultsDTO results = knowledgeSessionService
        .statelessExecute(null, null, newRules, null, newFacts, null);

    for (byte[] fact : results.getFacts()) {
      Account a = (Account) rulesComponent.deserializeObject(Account.class.getClassLoader(), fact);
      accountRepository.save(a);
    }

    System.out.println("******************");
    revertAccountValues();
  }

  private List<KnowledgeBaseDTO> getAllKnowledgeBases() {
    return knowledgeBaseService.getAllKnowledgeBases();
  }

  private void editAccountValues() {
    accounts.get(0).withdraw(accounts.get(0).getBalance());
    accounts.get(1).deposit(100);
  }

  private void revertAccountValues() {
    accounts.get(0).setStatus(AccountStatus.ACTIVE);
    accounts.get(0).setBalance(100);
    accountRepository.save(accounts.get(0));

    accounts.get(1).setStatus(AccountStatus.INACTIVE);
    accounts.get(1).setBalance(0);
    accountRepository.save(accounts.get(1));
  }

  private List<String> generateRules() {
    List<String> rules = new ArrayList<>();

    String rule1 = "import com.eurodyn.qlack.test.cmd.model.Account\n"
        + "\n"
        + "rule \"accountBalanceAtLeast100\"\n"
        + "  when\n"
        + "    $account : Account(balance < 100)\n"
        + "  then\n"
        + "    System.out.println(\"The balance of the account \" +$account.getId()+ \" is significantly low.\");\n"
        + "end";

    String rule2 = "import com.eurodyn.qlack.test.cmd.model.Account\n"
        + "import com.eurodyn.qlack.test.cmd.model.AccountStatus\n"
        + "\n"
        + "global com.eurodyn.qlack.test.cmd.component.AccountComponent accountComponent;\n"
        + "\n"
        + "rule \"accountIsActive\"\n"
        + "  when\n"
        + "    $account : Account((status == AccountStatus.INACTIVE) && (balance > 0))\n"
        + "  then\n"
        + "    $account.setStatus(AccountStatus.ACTIVE);\n"
        + "    accountComponent.save($account);\n"
        + "    System.out.println(\"The account \" +$account.getId()+ \" has been activated.\");\n"
        + "end";

    String rule3 = "import com.eurodyn.qlack.test.cmd.model.Account\n"
        + "import com.eurodyn.qlack.test.cmd.model.AccountStatus\n"
        + "\n"
        + "global com.eurodyn.qlack.test.cmd.component.AccountComponent accountComponent;\n"
        + "\n"
        + "rule \"accountIsInactive\"\n"
        + "  when\n"
        + "    $account : Account((status == AccountStatus.ACTIVE) && (balance == 0))\n"
        + "  then\n"
        + "    $account.setStatus(AccountStatus.INACTIVE);\n"
        + "    accountComponent.save($account);\n"
        + "    System.out.println(\"The account \" +$account.getId()+ \" has been deactivated.\");\n"
        + "end";

    rules.add(rule1);
    rules.add(rule2);
    rules.add(rule3);

    return rules;
  }

  private List<byte[]> getFacts() {
    List<byte[]> facts = new ArrayList<>();
    facts.add(rulesComponent.serializeObject(accounts.get(0)));
    facts.add(rulesComponent.serializeObject(accounts.get(1)));

    return facts;
  }

  private Map<String, byte[]> getGlobals() {
    Map<String, byte[]> globals = new HashMap<>();
    globals.put("accountComponent", rulesComponent.serializeObject(accountComponent));

    return globals;
  }
}
