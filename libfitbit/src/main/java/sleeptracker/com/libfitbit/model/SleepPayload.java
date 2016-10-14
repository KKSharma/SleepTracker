package sleeptracker.com.libfitbit.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sleeptracker.com.libfitbit.model.response.FitbitResult;


public class SleepPayload extends FitbitResult {

    @SerializedName("sleep")
    private List<Sleep> sleep = new ArrayList<Sleep>();

    @SerializedName("summary")
    private Summary summary;

    /**
     * @return The sleep
     */
    public List<Sleep> getSleep() {
        return sleep;
    }

    /**
     * @param sleep The sleep
     */
    public void setSleep(List<Sleep> sleep) {
        this.sleep = sleep;
    }

    /**
     * @return The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public class Sleep {
        @SerializedName("awakeCount")
        private Integer awakeCount;

        @SerializedName("awakeDuration")
        private Integer awakeDuration;

        @SerializedName("awakeningsCount")
        private Integer awakeningsCount;

        @SerializedName("dateOfSleep")
        private String dateOfSleep;

        @SerializedName("duration")
        private Double duration;

        @SerializedName("efficiency")
        private Integer efficiency;

        @SerializedName("isMainSleep")
        private Boolean isMainSleep;

        @SerializedName("logId")
        private Double logId;

        @SerializedName("minuteData")
        private List<MinuteDatum> minuteData = new ArrayList<MinuteDatum>();

        @SerializedName("minutesAfterWakeup")
        private Integer minutesAfterWakeup;

        @SerializedName("minutesAsleep")
        private Integer minutesAsleep;

        @SerializedName("minutesAwake")
        private Integer minutesAwake;

        @SerializedName("minutesToFallAsleep")
        private Integer minutesToFallAsleep;

        @SerializedName("restlessCount")
        private Integer restlessCount;

        @SerializedName("restlessDuration")
        private Integer restlessDuration;

        @SerializedName("startTime")
        private String startTime;

        @SerializedName("timeInBed")
        private Integer timeInBed;

        /**
         * @return The awakeCount
         */
        public Integer getAwakeCount() {
            return awakeCount;
        }

        /**
         * @param awakeCount The awakeCount
         */
        public void setAwakeCount(Integer awakeCount) {
            this.awakeCount = awakeCount;
        }

        /**
         * @return The awakeDuration
         */
        public Integer getAwakeDuration() {
            return awakeDuration;
        }

        /**
         * @param awakeDuration The awakeDuration
         */
        public void setAwakeDuration(Integer awakeDuration) {
            this.awakeDuration = awakeDuration;
        }

        /**
         * @return The awakeningsCount
         */
        public Integer getAwakeningsCount() {
            return awakeningsCount;
        }

        /**
         * @param awakeningsCount The awakeningsCount
         */
        public void setAwakeningsCount(Integer awakeningsCount) {
            this.awakeningsCount = awakeningsCount;
        }

        /**
         * @return The dateOfSleep
         */
        public String getDateOfSleep() {
            return dateOfSleep;
        }

        /**
         * @param dateOfSleep The dateOfSleep
         */
        public void setDateOfSleep(String dateOfSleep) {
            this.dateOfSleep = dateOfSleep;
        }

        /**
         * @return The duration
         */
        public Double getDuration() {
            return duration;
        }

        /**
         * @param duration The duration
         */
        public void setDuration(Double duration) {
            this.duration = duration;
        }

        /**
         * @return The efficiency
         */
        public Integer getEfficiency() {
            return efficiency;
        }

        /**
         * @param efficiency The efficiency
         */
        public void setEfficiency(Integer efficiency) {
            this.efficiency = efficiency;
        }

        /**
         * @return The isMainSleep
         */
        public Boolean getIsMainSleep() {
            return isMainSleep;
        }

        /**
         * @param isMainSleep The isMainSleep
         */
        public void setIsMainSleep(Boolean isMainSleep) {
            this.isMainSleep = isMainSleep;
        }

        /**
         * @return The logId
         */
        public Double getLogId() {
            return logId;
        }

        /**
         * @param logId The logId
         */
        public void setLogId(Double logId) {
            this.logId = logId;
        }

        /**
         * @return The minuteData
         */
        public List<MinuteDatum> getMinuteData() {
            return minuteData;
        }

        /**
         * @param minuteData The minuteData
         */
        public void setMinuteData(List<MinuteDatum> minuteData) {
            this.minuteData = minuteData;
        }

        /**
         * @return The minutesAfterWakeup
         */
        public Integer getMinutesAfterWakeup() {
            return minutesAfterWakeup;
        }

        /**
         * @param minutesAfterWakeup The minutesAfterWakeup
         */
        public void setMinutesAfterWakeup(Integer minutesAfterWakeup) {
            this.minutesAfterWakeup = minutesAfterWakeup;
        }

        /**
         * @return The minutesAsleep
         */
        public Integer getMinutesAsleep() {
            return minutesAsleep;
        }

        /**
         * @param minutesAsleep The minutesAsleep
         */
        public void setMinutesAsleep(Integer minutesAsleep) {
            this.minutesAsleep = minutesAsleep;
        }

        /**
         * @return The minutesAwake
         */
        public Integer getMinutesAwake() {
            return minutesAwake;
        }

        /**
         * @param minutesAwake The minutesAwake
         */
        public void setMinutesAwake(Integer minutesAwake) {
            this.minutesAwake = minutesAwake;
        }

        /**
         * @return The minutesToFallAsleep
         */
        public Integer getMinutesToFallAsleep() {
            return minutesToFallAsleep;
        }

        /**
         * @param minutesToFallAsleep The minutesToFallAsleep
         */
        public void setMinutesToFallAsleep(Integer minutesToFallAsleep) {
            this.minutesToFallAsleep = minutesToFallAsleep;
        }

        /**
         * @return The restlessCount
         */
        public Integer getRestlessCount() {
            return restlessCount;
        }

        /**
         * @param restlessCount The restlessCount
         */
        public void setRestlessCount(Integer restlessCount) {
            this.restlessCount = restlessCount;
        }

        /**
         * @return The restlessDuration
         */
        public Integer getRestlessDuration() {
            return restlessDuration;
        }

        /**
         * @param restlessDuration The restlessDuration
         */
        public void setRestlessDuration(Integer restlessDuration) {
            this.restlessDuration = restlessDuration;
        }

        /**
         * @return The startTime
         */
        public String getStartTime() {
            return startTime;
        }

        /**
         * @param startTime The startTime
         */
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        /**
         * @return The timeInBed
         */
        public Integer getTimeInBed() {
            return timeInBed;
        }

        /**
         * @param timeInBed The timeInBed
         */
        public void setTimeInBed(Integer timeInBed) {
            this.timeInBed = timeInBed;
        }


        public class MinuteDatum {

            @SerializedName("dateTime")

            private String dateTime;
            @SerializedName("value")

            private String value;

            /**
             * @return The dateTime
             */
            public String getDateTime() {
                return dateTime;
            }

            /**
             * @param dateTime The dateTime
             */
            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            /**
             * @return The value
             */
            public String getValue() {
                return value;
            }

            /**
             * @param value The value
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

    public class Summary {

        @SerializedName("totalMinutesAsleep")
        private Integer totalMinutesAsleep;

        @SerializedName("totalSleepRecords")
        private Integer totalSleepRecords;

        @SerializedName("totalTimeInBed")
        private Integer totalTimeInBed;

        /**
         * @return The totalMinutesAsleep
         */
        public Integer getTotalMinutesAsleep() {
            return totalMinutesAsleep;
        }

        /**
         * @param totalMinutesAsleep The totalMinutesAsleep
         */
        public void setTotalMinutesAsleep(Integer totalMinutesAsleep) {
            this.totalMinutesAsleep = totalMinutesAsleep;
        }

        /**
         * @return The totalSleepRecords
         */
        public Integer getTotalSleepRecords() {
            return totalSleepRecords;
        }

        /**
         * @param totalSleepRecords The totalSleepRecords
         */
        public void setTotalSleepRecords(Integer totalSleepRecords) {
            this.totalSleepRecords = totalSleepRecords;
        }

        /**
         * @return The totalTimeInBed
         */
        public Integer getTotalTimeInBed() {
            return totalTimeInBed;
        }

        /**
         * @param totalTimeInBed The totalTimeInBed
         */
        public void setTotalTimeInBed(Integer totalTimeInBed) {
            this.totalTimeInBed = totalTimeInBed;
        }

    }
}
