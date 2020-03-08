package com.codesai.reputation.infrastructure;

import com.codesai.reputation.business.actions.Action;
import com.codesai.reputation.business.actions.UserReputationForAuctionWinnerAction;
import com.codesai.reputation.business.actions.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher {

    static List<Action> actions = new ArrayList<>() {{
       this.add(new UserReputationForAuctionWinnerAction(new InMemoryUserReputationRepository()));
    }};

    public static void executeActionFor(Command command) {
        actions.stream()
                .filter(action -> action.canExecute(command))
                .findFirst()
                .ifPresent(action -> action.execute(command));
    }
}
