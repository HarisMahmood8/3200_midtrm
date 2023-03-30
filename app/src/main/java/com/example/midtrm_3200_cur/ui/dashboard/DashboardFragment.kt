package com.example.midtrm_3200_cur.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midtrm_3200_cur.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var seconds = 0
    private var isTimerRunning = false
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        handler = Handler(Looper.getMainLooper())

        val startButton: View = binding.startButtonDashboard
        startButton.setOnClickListener {
            startTimer()
        }

        val stopButton: View = binding.stopButtonDashboard
        stopButton.setOnClickListener {
            stopTimer()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startTimer() {
        isTimerRunning = true
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60

                val timeString = String.format("%02d:%02d:%02d", hours, minutes, secs)
                binding.textDashboard.text = timeString

                if (isTimerRunning) {
                    seconds++
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun stopTimer() {
        isTimerRunning = false
    }
}
