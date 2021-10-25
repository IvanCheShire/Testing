package ru.geekbrains.testing.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import com.geekbrains.tests.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "Number of results: 42")
    }

    @Test
    fun test_TotalCountTextView_InvisibleAfterSearchButtonPressed_IfSearchEditTextEmpty() {
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()

        val totalCountTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertNull(totalCountTextView)

    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "detailsTotalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_DetailsTotalCountTextView_DisplaysCorrectCount() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()

        val mainPageTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val mainPageCount = mainPageTextView.text.toString()

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val detailsPageTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "detailsTotalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(detailsPageTextView.text.toString(), mainPageCount)
    }

    @Test
    fun test_IncrementButton_IncrementDetailsTotalCountTextView() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val detailsPageTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "detailsTotalCountTextView")),
                TIMEOUT
            )
        val incrementButton: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "incrementButton"
            ))
        for (i in 0..4) incrementButton.click()
        Assert.assertEquals(detailsPageTextView.text.toString(), "Number of results: 5")
    }

    @Test
    fun test_DecrementButton_DecrementDetailsTotalCountTextView() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val detailsPageTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "detailsTotalCountTextView")),
                TIMEOUT
            )
        val decrementButton: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "decrementButton"
            ))
        for (i in 0..4) decrementButton.click()
        Assert.assertEquals(detailsPageTextView.text.toString(), "Number of results: -5")
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}