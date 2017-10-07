package com.company.app.presentation;

import android.app.Application;

import com.company.app.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.company.app.presentation.internal.di.components.DaggerApplicationComponent;
import com.company.app.presentation.internal.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
