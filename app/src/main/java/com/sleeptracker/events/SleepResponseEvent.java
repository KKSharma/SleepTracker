package com.sleeptracker.events;

import sleeptracker.com.libfitbit.model.SleepPayload;

/**
 * Created by synerzip on 02/10/16.
 */

public class SleepResponseEvent extends FitbitResponseEvent<SleepPayload> {
    public SleepPayload getSleepResult(){
        SleepPayload result = (SleepPayload)getResult();
        return result;
    }
}
