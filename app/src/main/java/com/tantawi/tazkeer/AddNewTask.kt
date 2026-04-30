package com.tantawi.tazkeer

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.Calendar

class AddNewTask : Fragment() {

    private var selectedCategory: String? = null
    private var selectedRepeat: String? = null

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etTime: EditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)
        etTime = view.findViewById(R.id.etTime)
        btnSave = view.findViewById(R.id.btnSave)

        val lightGray = ContextCompat.getColor(requireContext(), R.color.light_gray)
        val activeGreen = ContextCompat.getColor(requireContext(), R.color.active_green)
        val disabledGray = ContextCompat.getColor(requireContext(), R.color.disabled_gray)
        val selectedGreen = ContextCompat.getColor(requireContext(), R.color.selected_green)

        view.findViewById<ImageView>(R.id.btnClose).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, h, m ->
                    etTime.setText(String.format("%02d:%02d", h, m))
                    checkForm()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        val btnStudy = view.findViewById<Button>(R.id.btnStudy)
        val btnWork = view.findViewById<Button>(R.id.btnWork)
        val btnSport = view.findViewById<Button>(R.id.btnSport)
        val btnMedicine = view.findViewById<Button>(R.id.btnMedicine)
        val btnOther = view.findViewById<Button>(R.id.btnOther)

        val categoryButtons = listOf(btnStudy, btnWork, btnSport, btnMedicine, btnOther)

        fun selectCategory(selected: Button, value: String) {
            selectedCategory = value

            categoryButtons.forEach {
                it.setBackgroundColor(lightGray)
            }

            selected.setBackgroundColor(selectedGreen)

            checkForm()
        }

        btnStudy.setOnClickListener { selectCategory(btnStudy, "دراسة") }
        btnWork.setOnClickListener { selectCategory(btnWork, "عمل") }
        btnSport.setOnClickListener { selectCategory(btnSport, "رياضة") }
        btnMedicine.setOnClickListener { selectCategory(btnMedicine, "دواء") }
        btnOther.setOnClickListener { selectCategory(btnOther, "أخرى") }

        val btnOnce = view.findViewById<Button>(R.id.btnOnce)
        val btnDaily = view.findViewById<Button>(R.id.btnDaily)
        val btnCustom = view.findViewById<Button>(R.id.btnCustom)

        val repeatButtons = listOf(btnOnce, btnDaily, btnCustom)

        fun selectRepeat(selected: Button, value: String) {
            selectedRepeat = value

            repeatButtons.forEach {
                it.backgroundTintList = ColorStateList.valueOf(lightGray)
            }

            selected.backgroundTintList =
                ColorStateList.valueOf(activeGreen)

            checkForm()
        }

        btnOnce.setOnClickListener { selectRepeat(btnOnce, "مرة واحدة") }
        btnDaily.setOnClickListener { selectRepeat(btnDaily, "يومي") }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkForm()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etTitle.addTextChangedListener(watcher)
        etDescription.addTextChangedListener(watcher)

        checkForm()

//        btnSave.setOnClickListener {
//            if (btnSave.isEnabled) {
//
//            }
//
//        }

        view.findViewById<Button>(R.id.btnCancel)
            .setOnClickListener { parentFragmentManager.popBackStack() }

        btnCustom.setOnClickListener {

            val days = arrayOf(
                "Saturday", "Sunday", "Monday",
                "Tuesday", "Wednesday", "Thursday", "Friday"
            )

            val selectedDays = BooleanArray(days.size)
            val chosenList = mutableListOf<String>()

            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setTitle("Select Days")

            builder.setMultiChoiceItems(days, selectedDays) { _, which, isChecked ->
                if (isChecked) chosenList.add(days[which])
                else chosenList.remove(days[which])
            }

            builder.setPositiveButton("OK") { _, _ ->
                if (chosenList.isNotEmpty()) {
                    selectedRepeat = chosenList.joinToString(", ")
                    selectRepeat(btnCustom, selectedRepeat!!)
                }
            }

            builder.setNegativeButton("Cancel", null)

            builder.show()
        }
    }

    private fun checkForm() {

        val isValid =
            etTitle.text.toString().trim().isNotEmpty() &&
                    etTime.text.toString().trim().isNotEmpty() &&
                    selectedCategory != null &&
                    selectedRepeat != null

        btnSave.isEnabled = isValid

        val color = if (isValid)
            ContextCompat.getColor(requireContext(), R.color.active_green)
        else
            ContextCompat.getColor(requireContext(), R.color.disabled_gray)

        btnSave.backgroundTintList = ColorStateList.valueOf(color)
    }
}