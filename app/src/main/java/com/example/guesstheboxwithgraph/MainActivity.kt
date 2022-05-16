package com.example.guesstheboxwithgraph

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.guesstheboxwithgraph.databinding.ActivityMainBinding
import com.example.guesstheboxwithgraph.fragments.*
import com.example.guesstheboxwithgraph.fragments.contract.*
import com.example.guesstheboxwithgraph.model.Options

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var currentFragment: Fragment? = null

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment) return
            currentFragment = f
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                as NavHostFragment

        navController = navHost.navController

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        updateUi()
        return true
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()

    override fun showBoxSelectionScreen(options: Options) {
        launchDestination(R.id.boxSelectionFragment, BoxSelectionFragment.createArgs(options))
    }

    override fun showOptionsScreen(options: Options) {
        launchDestination(R.id.optionsFragment, OptionsFragment.createArgs(options))
    }

    override fun showAboutScreen() {
        launchDestination(R.id.aboutFragment)
    }

    override fun showBoxCongratulationsScreen() {
        launchDestination(R.id.boxFragment)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        navController.popBackStack(R.id.menuFragment, false)
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(
            result.javaClass.name,
            bundleOf(KEY_RESULT to result)
        )
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            clazz.name,
            owner
        ) { _, bundle ->
            listener.invoke(bundle.getParcelable(KEY_RESULT)!!)
        }
    }

    private fun updateUi() {
        val fragment = currentFragment

        if (fragment is HasCustomTitle) {
            binding.toolbar.title = getString(fragment.getTitleRes())
        } else {
            binding.toolbar.title = getString(R.string.fragment_navigation_example)
        }

        if (navController.currentDestination?.id == navController.graph.startDestinationId) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        if (fragment is HasCustomAction) {
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            binding.toolbar.menu.clear()
        }
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        val iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
        iconDrawable.setTint(Color.WHITE)

        val menuItem = binding.toolbar.menu.add(action.textRes)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }

    private fun launchDestination(destination: Int, args: Bundle? = null) {
        navController.navigate(
            destination,
            args,
            navOptions {
                anim {
                    enter = R.anim.slide_in
                    exit = R.anim.fade_out
                    popEnter = R.anim.fade_in
                    popExit = R.anim.slide_out

                }
            }
        )
    }

    companion object {
        private const val KEY_RESULT = "RESULT"
    }

}