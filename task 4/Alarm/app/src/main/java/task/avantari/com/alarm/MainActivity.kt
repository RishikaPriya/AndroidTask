package task.avantari.com.alarm

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alarm_list_layout.*

import task.avantari.com.alarm.model.AlarmDetails
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.content_main.*
import android.view.ViewAnimationUtils
import android.animation.Animator

class MainActivity : AppCompatActivity() {

    var alarmList = ArrayList<AlarmDetails>()
    var alarm_listView : ListView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAlarmData()
        var alarmAdapter = AlarmAdapter(this, alarmList)
        alarm_list.adapter = alarmAdapter

        alarm_listView = findViewById(R.id.alarm_list)

    }

    private fun addAlarmData() {
        alarmList.add(AlarmDetails("06:30", intArrayOf(1, 0, 1, 1, 0, 1, 1), true))
        alarmList.add(AlarmDetails("09:30", intArrayOf(0, 0, 0, 1, 0, 0, 0), false))
        alarmList.add(AlarmDetails("15:30", intArrayOf(1, 0, 0, 0, 0, 0, 0), true))
    }

    data class AlarmAdapter(private val activity: Activity, private val alarmList: ArrayList<AlarmDetails>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = View.inflate(activity, R.layout.alarm_item, null)

            val alarmIcon = view.findViewById(R.id.alarm_icon) as ImageView
            val tvTime = view.findViewById(R.id.alarm_time) as TextView
            val tvSun = view.findViewById(R.id.sunday) as TextView
            val tvMon = view.findViewById(R.id.monday) as TextView
            val tvTue = view.findViewById(R.id.tuesday) as TextView
            val tvWed = view.findViewById(R.id.wednesday) as TextView
            val tvThurs = view.findViewById(R.id.thursday) as TextView
            val tvFri = view.findViewById(R.id.friday) as TextView
            val tvSat = view.findViewById(R.id.saturday) as TextView
            val switch = view.findViewById(R.id.enable_switch) as SwitchCompat

            tvTime.text = alarmList[position].time
            println(alarmList[position].enableAlarm)
            if (!alarmList[position].enableAlarm) {
                alarmIcon.setImageResource(R.drawable.ic_icon_alarm_toggle_off)
                switch.isChecked = false
                tvTime.setTextColor(getGrayTextColor())
            }

            val days = alarmList[position].enableDays
            setDayTextColor(days, 0, tvSun)
            setDayTextColor(days, 1, tvMon)
            setDayTextColor(days, 2, tvTue)
            setDayTextColor(days, 3, tvWed)
            setDayTextColor(days, 4, tvThurs)
            setDayTextColor(days, 5, tvFri)
            setDayTextColor(days, 6, tvSat)

            return view
        }

        private fun setDayTextColor(days: IntArray, index: Int, tv: TextView) {
            if (days[index] == 1) {
                tv.setTextColor(getBlueTextColor())
            } else {
                tv.setTextColor(getGrayTextColor())
            }
        }

        private fun getGrayTextColor() = ContextCompat.getColor(activity, R.color.light_gray)

        private fun getBlueTextColor() = ContextCompat.getColor(activity, R.color.thumb_blue)

        override fun getItem(position: Int): Any {
            return alarmList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return alarmList.size
        }

    }

    fun onChartView(v: View) {

        fab.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_down_anim))
        fab.visibility = View.GONE

        val cx = frame2.width / 2
        val cy = frame2.height / 2

        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(frame2, cx, cy, 0f, finalRadius)

        frame1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_left))

        frame1.visibility = View.GONE
        frame2.visibility = View.VISIBLE

        anim.start()

    }

    fun doBack(v: View) {

        val cx = frame2.width / 2
        val cy = frame2.height / 2

        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(frame2, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                frame2.visibility = View.GONE
                fab.visibility = View.VISIBLE
                frame1.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.slide_from_left))
                frame1.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                fab.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.move_up_anim))
            }
        })

        anim.start()
    }
}
