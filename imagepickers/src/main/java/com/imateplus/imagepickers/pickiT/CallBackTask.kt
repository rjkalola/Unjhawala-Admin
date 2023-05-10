package com.imateplus.imagepickers.pickiT

public interface CallBackTask {
    fun PickiTonUriReturned()
    fun PickiTonPreExecute()
    fun PickiTonProgressUpdate(progress: Int)
    fun PickiTonPostExecute(
        path: String?,
        wasDriveFile: Boolean,
        wasSuccessful: Boolean,
        reason: String?
    )
}