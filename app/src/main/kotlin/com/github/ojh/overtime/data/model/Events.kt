package com.github.ojh.overtime.data.model

class Events {
    class WriteEvent(val timeLine: TimeLine)
    class UpdateEvent(val timeLine: TimeLine)
}