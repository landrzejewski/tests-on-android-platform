package pl.training.helloworld

import android.content.Intent
import android.widget.Button
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    @Test
    fun testSomething() {
        launch(MainActivity::class.java).onActivity {
            it.findViewById<Button>(R.id.login).performClick()
            val expectedIntent = Intent(it, LoginActivity::class.java)
            val actualIntent = shadowOf(it).nextStartedActivity
            assertEquals(expectedIntent.component, actualIntent.component)
        }
    }

}