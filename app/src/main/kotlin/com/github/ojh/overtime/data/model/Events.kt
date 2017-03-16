package com.github.ojh.overtime.data.model

sealed class Events

class WriteEvent(val timeLine: TimeLine) : Events()
class UpdateEvent(val timeLine: TimeLine) : Events()
class DeleteEvent(val timeLineId: Int) : Events()