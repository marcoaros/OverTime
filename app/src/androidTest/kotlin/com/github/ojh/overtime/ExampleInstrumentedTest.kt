package com.github.ojh.overtime

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.IOException

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.github.ojh.overtime", appContext.packageName)
    }

    @Test
    fun fileDirTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        subDirList(appContext.filesDir.path)
    }


    fun subDirList(source: String) {

        val dir = File(source)
        val fileList = dir.listFiles()
        try {
            for (i in fileList.indices) {
                val file = fileList[i]
                if (file.isFile) {
                    // 파일이 있다면 파일 이름 출력
                    println("파일 - " + file.absolutePath)
                } else if (file.isDirectory) {
                    println("폴더 - " + file.absolutePath)
                    // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                    subDirList(file.canonicalPath.toString())
                }
            }
        } catch (e: IOException) {

        }
    }
}
