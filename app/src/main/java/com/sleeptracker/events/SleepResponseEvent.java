package com.sleeptracker.events;

import sleeptracker.com.libfitbit.model.SleepPayload;

public class SleepResponseEvent extends FitbitResponseEvent<SleepPayload> {
    public SleepPayload getSleepResult(){
        SleepPayload result = (SleepPayload)getResult();
        return result;
    }
}
