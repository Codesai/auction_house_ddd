package com.codesai.reputation.infrastructure;

import com.codesai.reputation.business.userReputation.UserReputation;
import com.codesai.reputation.business.userReputation.UserReputationRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserReputationRepository implements UserReputationRepository {
    Map<String, UserReputation> users = new HashMap();

    @Override
    public UserReputation getById(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        return new UserReputation(userId, 0);
    }

    @Override
    public void save(UserReputation userReputation) {
        users.put(userReputation.userId, userReputation);
    }
}
