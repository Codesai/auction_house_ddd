package com.codesai.reputation.business.actions;

import com.codesai.reputation.business.actions.commands.UserReputationForAuctionWinnerCommand;
import com.codesai.reputation.business.userReputation.UserReputationRepository;

public class UserReputationForAuctionWinnerAction {
    private final UserReputationRepository userReputationRepository;

    public UserReputationForAuctionWinnerAction(UserReputationRepository userReputationRepository) {
        this.userReputationRepository = userReputationRepository;
    }

    public void execute(UserReputationForAuctionWinnerCommand command) {
        var userReputation = userReputationRepository.getById(command.userId);
        userReputation.accountBidWinner(command.winnerBid);
        userReputationRepository.save(userReputation);
    }
}
