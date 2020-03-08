package com.codesai.reputation.business.actions;

import com.codesai.reputation.business.actions.commands.Command;

public interface Action<T extends Command> {
    void execute(T c);
    boolean canExecute(Command c);
}
