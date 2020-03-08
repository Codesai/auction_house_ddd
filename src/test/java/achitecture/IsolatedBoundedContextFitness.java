package achitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class IsolatedBoundedContextFitness {

    @Test
    public void
    auction_house_bounded_context_dont_depends_on_reputation_bounded_context() {
        var boundedContextIsolatedRule = noClasses().that()
                .resideInAPackage("..auction_house..")
                .should().accessClassesThat()
                .resideInAPackage("..reputation..");

        boundedContextIsolatedRule.check(allClasses());
    }

    @Test public void
    reputation_bounded_context_dont_depende_on_auction_house_bounded_context() {
        var boundedContextIsolatedRule = noClasses().that()
                .resideInAPackage("..reputation..")
                .should().accessClassesThat()
                .resideInAPackage("..auction_house..");

        boundedContextIsolatedRule.check(allClasses());
    }

    private JavaClasses allClasses() {
        return new ClassFileImporter().importPackages("com.codesai");
    }
}
