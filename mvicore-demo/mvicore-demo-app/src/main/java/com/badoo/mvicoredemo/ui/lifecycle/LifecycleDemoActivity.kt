package com.badoo.mvicoredemo.ui.lifecycle

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.badoo.binder.Binder
import com.badoo.binder.named
import com.badoo.mvicore.android.lifecycle.CreateDestroyBinderLifecycle
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.android.lifecycle.StartStopBinderLifecycle
import com.badoo.mvicoredemo.R
import init
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navigationView
import kotlinx.android.synthetic.main.activity_main.toolbar

class LifecycleDemoActivity : AppCompatActivity() {

    private val events = PublishSubject.create<String>()
    private val dummyConsumer = Consumer<String> {
        Log.d("LifecycleDemo", it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_demo)

        Binder(CreateDestroyBinderLifecycle(lifecycle))
            .bind(events to dummyConsumer named "Lifecycle#CreateDestroy")

        Binder(StartStopBinderLifecycle(lifecycle))
            .bind(events to dummyConsumer named "Lifecycle#StartStop")

        Binder(ResumePauseBinderLifecycle(lifecycle))
            .bind(events to dummyConsumer named "Lifecycle#ResumePause")

        events.onNext("onCreate")
        setupDrawer()
    }

    private fun setupDrawer() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.init(drawerLayout, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onStart() {
        super.onStart()

        events.onNext("onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        events.onNext("onRestoreState")
    }

    override fun onResume() {
        super.onResume()

        events.onNext("onResume")
    }

    override fun onPostResume() {
        super.onPostResume()

        events.onNext("onPostResume")
    }

    override fun onPause() {
        super.onPause()

        events.onNext("onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        events.onNext("onSaveState")
    }

    override fun onStop() {
        super.onStop()

        events.onNext("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        events.onNext("onDestroy")
    }
}
