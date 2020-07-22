package reputation.unit.business.actions;

import com.codesai.reputation.business.actions.UserReputationForAuctionWinnerAction;
import com.codesai.reputation.business.actions.commands.UserReputationForAuctionWinnerCommand;
import com.codesai.reputation.business.userReputation.UserReputation;
import com.codesai.reputation.business.userReputation.UserReputationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AccountReputationForAuctionWinnerActionShould {
    private static final String ANY_USER_ID = "any_user_id";

    UserReputationRepository userReputationRepository = mock(UserReputationRepository.class);
    UserReputationForAuctionWinnerAction accountUserReputation = new UserReputationForAuctionWinnerAction(userReputationRepository);

    ArgumentCaptor<UserReputation> captor = ArgumentCaptor.forClass(UserReputation.class);

    @Test
    public void
    add_1_reputation_point_to_user_score_for_each_100_euros_spend_in_the_auction() {
        when(userReputationRepository.getById(ANY_USER_ID)).thenReturn(aUserWithReputation(0));

        accountUserReputation.execute(new UserReputationForAuctionWinnerCommand(ANY_USER_ID, 320));

        verify(userReputationRepository).save(captor.capture());
        assertThat(captor.getValue().reputation).isEqualTo(3);
    }

    private UserReputation aUserWithReputation(int reputation) {
        return new UserReputation(ANY_USER_ID, reputation);
    }

}
