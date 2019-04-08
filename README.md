# QLACK Spring Boot Console Application

This application tests the following [QLACK](https://github.com/eurodyn/QLACK) modules:
- qlack-fuse-aaa
- qlack-fuse-audit
- qlack-fuse-lexicon
- qlack-fuse-mailing
- qlack-fuse-rules
- qlack-fuse-search
- qlack-fuse-settings
- qlack-fuse-scheduler

In order to run the application, run `java -jar qlack-test-cmd-1.0-SNAPSHOT.jar` adding one of the following arguments:

| Module               | Argument         |
|----------------------|------------------|
| qlack-fuse-aaa       | AAAService       |
| qlack-fuse-audit     | AuditService     |
| qlack-fuse-lexicon   | LexiconService   |
| qlack-fuse-mailing   | MailingService   |
| qlack-fuse-rules     | RulesService     |
| qlack-fuse-scheduler | SchedulerService |
| qlack-fuse-search    | SearchService    |
| qlack-fuse-settings  | SettingsService  |