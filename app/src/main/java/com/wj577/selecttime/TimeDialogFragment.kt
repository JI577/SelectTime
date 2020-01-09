package com.wj577.selecttime

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import com.contrarywind.adapter.WheelAdapter
import com.contrarywind.view.WheelView
import kotlinx.android.synthetic.main.time_picker.*

import java.util.Calendar


class TimeDialogFragment : BaseDialogFragment {
    private var wheelMin: WheelView? = null
    private var wheelHour: WheelView? = null
    private var wheelDay: WheelView? = null
    private var hourList: List<String>? = null
    private var twoHourList: List<String>? = null
    private var dayList: List<String>? = null
    private var minList: List<String>? = null
    private var curDay = 0
    private var sbContent: StringBuffer? = null
    private var hourAdapter: MyAdapter? = null
    private var minAdapter: MyAdapter? = null

    var timeNum = 0//时间间隔  分钟


    @SuppressLint("ValidFragment")
    constructor(time: Int) {
        timeNum = time
    }


    private fun listener() {
        tv_cancel.setOnClickListener { dialog!!.dismiss() }
        tv_finish.setOnClickListener {
            try {
                sbContent = StringBuffer("")
                val currentDay = wheelDay!!.currentItem
                val currentMin = wheelHour!!.currentItem
                val currentSecont = wheelMin!!.currentItem

                if (curDay == 0) {
                    if (currentMin == 0) {
                        sbContent!!.append("立即送达")
                    } else {
                        var hour = hourList!![currentMin]
                        if (minList == null) {
                            return@setOnClickListener
                        }
                        var min = minList!![currentSecont]
                        hour = hour.replace("点", "")
                        min = min.replace("分", "")
                        sbContent!!.append(dayList!![currentDay] + hour + ":" + min)
                    }
                } else {
                    var hour = twoHourList!![currentMin]
                    if (minList == null) {
                        return@setOnClickListener
                    }
                    var min = minList!![currentSecont]
                    hour = hour.replace("点", "")
                    min = min.replace("分", "")
                    sbContent!!.append(dayList!![currentDay] + hour + ":" + min)
                }
                Log.e("jrq", "--------sbContent--------" + sbContent.toString())
                dialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        listener()
    }


    private fun initData() {
        dayList = StringHelper.getDay2()
        wheelDay!!.adapter = MyAdapter(dayList!!)
        val c = Calendar.getInstance()
        hourList =
            StringHelper.getHourTime(c.get(Calendar.HOUR_OF_DAY), timeNum, c.get(Calendar.MINUTE))

        hourAdapter = MyAdapter(hourList!!)
        wheelHour!!.adapter = hourAdapter

        wheelDay!!.setOnItemSelectedListener { index ->
            curDay = index

            wheelHour!!.currentItem = 0
            wheelMin!!.currentItem = 0
            if (index == 0) {//当天
                hourAdapter = null
                val currentHour = wheelHour!!.currentItem
                if (currentHour == 0) {
                    wheelMin!!.visibility = View.GONE
                } else {
                    wheelMin!!.visibility = View.VISIBLE
                }
                hourAdapter = MyAdapter(hourList!!)
                wheelHour!!.adapter = hourAdapter
            } else {//第二天
                val currHour = c.get(Calendar.HOUR_OF_DAY)
                //                    currHour = 23;
                val hour = timeNum / 60
                if (currHour + hour > 23) {//第二天需要动态修改
                    hourAdapter = null
                    wheelMin!!.visibility = View.VISIBLE
                    twoHourList = StringHelper.getTwoHourTime(currHour + hour - 24)
                    hourAdapter = MyAdapter(twoHourList!!)
                    wheelHour!!.adapter = hourAdapter
                    minAdapter = null
                    minList = StringHelper.getMin(false, timeNum, c.get(Calendar.MINUTE))
                    minAdapter = MyAdapter(minList!!)
                    wheelMin!!.adapter = minAdapter
                    wheelMin!!.visibility = View.VISIBLE
                } else {//第二天数据正常
                    hourAdapter = null
                    wheelMin!!.visibility = View.VISIBLE
                    twoHourList = StringHelper.getHourAll()
                    hourAdapter = MyAdapter(twoHourList!!)
                    wheelHour!!.adapter = hourAdapter
                    minAdapter = null
                    minList = StringHelper.getMin(true, 0, 0)
                    minAdapter = MyAdapter(minList!!)
                    wheelMin!!.adapter = minAdapter
                    wheelMin!!.visibility = View.VISIBLE
                }

            }
        }
        wheelHour!!.setOnItemSelectedListener { index ->
            if (curDay == 0) {//今天
                if (index == 0) {
                    wheelMin!!.visibility = View.GONE
                } else if (index == 1) {
                    minAdapter = null
                    minList = StringHelper.getMin(false, timeNum, c.get(Calendar.MINUTE))
                    minAdapter = MyAdapter(minList!!)
                    wheelMin!!.adapter = minAdapter
                    wheelMin!!.visibility = View.VISIBLE
                } else {
                    minAdapter = null
                    minList = StringHelper.getMin(true, 0, 0)
                    minAdapter = MyAdapter(minList!!)
                    wheelMin!!.adapter = minAdapter
                    wheelMin!!.visibility = View.VISIBLE
                }
            } else {//明天
                val currHour = c.get(Calendar.HOUR_OF_DAY)
                //                    currHour = 23;
                val hour = timeNum / 60
                if (currHour + hour > 23) {//第二天需要动态修改
                    if (index == 0) {
                        minAdapter = null
                        minList = StringHelper.getMin(false, timeNum, c.get(Calendar.MINUTE))
                        minAdapter = MyAdapter(minList!!)
                        wheelMin!!.adapter = minAdapter
                        wheelMin!!.visibility = View.VISIBLE
                    } else {
                        minAdapter = null
                        minList = StringHelper.getMin(true, 0, 0)
                        minAdapter = MyAdapter(minList!!)
                        wheelMin!!.adapter = minAdapter
                        wheelMin!!.visibility = View.VISIBLE
                    }

                } else {
                    minAdapter = null
                    minList = StringHelper.getMin(true, 0, 0)
                    minAdapter = MyAdapter(minList!!)
                    wheelMin!!.adapter = minAdapter
                    wheelMin!!.visibility = View.VISIBLE
                }

            }
        }
    }


    private fun initView() {
        wheelDay = mView.findViewById(R.id.wheelView)
        wheelHour = mView.findViewById(R.id.wheelView_1)
        wheelMin = mView.findViewById(R.id.wheelView_2)
        wheelDay!!.setDividerColor(Color.WHITE)
        wheelHour!!.setDividerColor(Color.WHITE)
        wheelMin!!.setDividerColor(Color.WHITE)
        wheelDay!!.setCyclic(false)
        wheelHour!!.setCyclic(false)
        wheelMin!!.setCyclic(false)
    }

    override fun getContentView(): Int {
        return R.layout.time_picker
    }

    override fun getLocation(): Int {
        return Gravity.BOTTOM
    }

    override fun getAimate(): Int {
        return R.style.animate_dialog
    }


    internal inner class MyAdapter(var list: List<String>) : WheelAdapter<String> {
        override fun getItemsCount(): Int {
            return list.size
        }

        override fun getItem(index: Int): String {
            return list[index]
        }

        override fun indexOf(o: String): Int {
            return 0
        }
    }
}