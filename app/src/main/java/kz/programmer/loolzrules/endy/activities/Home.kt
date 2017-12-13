package kz.programmer.loolzrules.endy.activities

import android.app.Activity
import android.os.Bundle
import kz.programmer.loolzrules.endy.R
import kz.programmer.loolzrules.endy.Tags
import kz.programmer.loolzrules.endy.fragments.Main

class Home : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mainFragment = Main()

        fragmentManager.beginTransaction()
                .replace(R.id.fl_main_fragment_holder, mainFragment, Tags.TAG_MAIN)
                .commit()
    }

}
