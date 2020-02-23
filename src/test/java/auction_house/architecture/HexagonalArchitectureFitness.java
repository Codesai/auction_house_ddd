package auction_house.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class HexagonalArchitectureFitness {

    @Test
    public void
    business_model_dont_depend_on_infrastructure() {
        var dependencyInversionRule = classes().that()
                .resideInAPackage("..business..")
                .should().onlyAccessClassesThat()
                .resideInAnyPackage("..business..", "java..", "org.apache.commons..");

        dependencyInversionRule.check(auctionHouseBoundedContextClasses());
    }

    @Test public void
    repositories_should_depend_on_business_model() {
        var repositoryShouldDependOnModel = classes().that()
                .resideInAPackage("..repository..")
                .should().accessClassesThat()
                .resideInAPackage("..model..");

        repositoryShouldDependOnModel.check(auctionHouseBoundedContextClasses());
    }

    @Test public void
    delivery_mechanism_depends_on_model_and_infrastructure() {
        var deliveryDependsOnModelAnInfrastructure = classes().that()
                .resideInAPackage("..deliverymechanism..")
                .should().accessClassesThat()
                .resideInAnyPackage("..business..", "..infrastructure..");

        deliveryDependsOnModelAnInfrastructure.check(auctionHouseBoundedContextClasses());
    }

    private JavaClasses auctionHouseBoundedContextClasses() {
        return new ClassFileImporter().importPackages("com.codesai.auction_house");
    }

}
