package auction_house.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class HexagonalArchitectureFitness {

    @Test
    public void
    business_model_dont_depend_on_infrastructure() {
        JavaClasses auctionHouseBoundedContext = new ClassFileImporter().importPackages("com.codesai.auction_house");

        var dependencyInversionRule = noClasses().that()
                .resideInAPackage("..business..")
                .should().accessClassesThat()
                .resideInAPackage("..infrastructure..");

        dependencyInversionRule.check(auctionHouseBoundedContext);
    }


}
