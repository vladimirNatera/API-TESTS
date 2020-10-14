package org.natera.core.settings;

import org.natera.api.classes.Triangles;

import java.util.Set;

public class Config {

    public interface CoreSettings {
        String defaultEnvironment = Environments.natera.production();

        int basicTimeout = 2;
        int waitingTimeout = 25;

        boolean docker = false;
        boolean video = false;

        boolean verbose = true;

        boolean multiSessionMode = false;
        boolean fullScreenMode = false;
        boolean doCleanupAfterTestRun = true;
        boolean doCleanupBeforeTestRun = true;
        boolean restIgnoreProperties = true;

    }
}
