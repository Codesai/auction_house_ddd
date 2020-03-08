package reputation.unit.business.actions;

import com.codesai.reputation.business.actions.UserReputationForAuctionWinnerAction;
import com.codesai.reputation.business.actions.commands.UserReputationForAuctionWinnerCommand;
import com.codesai.reputation.business.userReputation.UserReputation;
import com.codesai.reputation.business.userReputation.UserReputationRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AccountReputationForAuctionWinnerActionShould {
    private static final String ANY_USER_ID = "any_user_id";
    UserReputationRepository userReputationRepository = mock(UserReputationRepository.class);

    @Test
    public void
    add_1_reputation_point_to_user_score_for_each_100_euros_spend_in_the_auction() {
        when(userReputationRepository.getById(ANY_USER_ID)).thenReturn(new UserReputation(ANY_USER_ID, 0));

        var accountUserReputation = new UserReputationForAuctionWinnerAction(userReputationRepository);
        accountUserReputation.execute(new UserReputationForAuctionWinnerCommand(ANY_USER_ID, 320));

        verify(userReputationRepository).save(new UserReputation(ANY_USER_ID, 3));
    }
}
