package com.reddot.core;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.reddot.core");

        noClasses()
            .that()
            .resideInAnyPackage("com.reddot.core.service..")
            .or()
            .resideInAnyPackage("com.reddot.core.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.reddot.core.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
