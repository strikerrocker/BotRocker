package com.github.strikerrocker.commands;

import com.github.strikerrocker.MainRunner;

public class CommandPersistent extends Command {
    public CommandPersistent(String cmd, boolean admin) {
        super(cmd, admin);
        MainRunner.INSTANCE.storageCommands.put(cmd, this);
    }

    public void onStart() {
    }

    public void read() {

    }

    public void save() {

    }
}
