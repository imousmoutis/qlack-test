# QLACK Spring Boot Console Application

This application tests the following [QLACK](https://github.com/eurodyn/QLACK) modules:
- qlack-fuse-aaa
- qlack-fuse-audit
- qlack-fuse-lexicon
- qlack-fuse-mailing
- qlack-fuse-rules
- qlack-fuse-settings

In order to run the application, run `java -jar qlack-test-cmd-1.0-SNAPSHOT.jar` adding one of the following arguments:
| Module              | Argument        |
|---------------------|-----------------|
| qlack-fuse-aaa      | UserService     |
| qlack-fuse-audit    | AuditService    |
| qlack-fuse-lexicon  | LanguageService |
| qlack-fuse-mailing  | MailService     |
| qlack-fuse-rules    | RulesService    |
| qlack-fuse-settings | SettingsService |