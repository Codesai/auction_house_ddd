package com.codesai.reputation.business.actions;

import com.codesai.reputation.business.actions.commands.Command;
import com.codesai.reputation.business.actions.commands.UserReputationForAuctionWinnerCommand;
import com.codesai.reputation.business.userReputation.UserReputationRepository;

public class UserReputationForAuctionWinnerAction implements Action<UserReputationForAuctionWinnerCommand> {
    private final UserReputationRepository userReputationRepository;

    public UserReputationForAuctionWinnerAction(UserReputationRepository userReputationRepository) {
        this.userReputationRepository = userReputationRepository;
    }

    @Override
    public void execute(UserReputationForAuctionWinnerCommand command) {
        var userReputation = userReputationRepository.getById(command.userId);
        userReputation.accountBidWinner(command.winnerBid);
        userReputationRepository.save(userReputation);
        System.out.println("update user reputation: " + userReputation);
    }

    @Override
    public boolean canExecute(Command c) {
        return c instanceof UserReputationForAuctionWinnerCommand;
    }
}
