package com.codesai.reputation.business.userReputation;

public interface UserReputationRepository {
    UserReputation getById(String anyUserId);

    void save(UserReputation userReputation);
}
