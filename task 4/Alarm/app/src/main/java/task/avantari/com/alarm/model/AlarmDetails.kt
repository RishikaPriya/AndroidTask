package task.avantari.com.alarm.model

class AlarmDetails {

    var time: String? = null
    var enableDays = IntArray(7)
    var enableAlarm: Boolean = true

    constructor(time: String?, enableDays: IntArray, enableAlarm: Boolean) {
        this.time = time
        this.enableDays = enableDays
        this.enableAlarm = enableAlarm
    }
}