package com.example.calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etTotalAmount: EditText
    private lateinit var spTipPercentage: Spinner
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalWithTip: TextView
    private lateinit var tvTotalWithoutTip: TextView
    private lateinit var btnCalculate: Button
    private var selectedTipPercentage: Double = 0.10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTotalAmount = findViewById(R.id.et_total_amount)
        spTipPercentage = findViewById(R.id.sp_tip_percentage)
        tvTipAmount = findViewById(R.id.tv_tip_amount)
        tvTotalWithTip = findViewById(R.id.tv_total_with_tip)
        tvTotalWithoutTip = findViewById(R.id.tv_total_without_tip)
        btnCalculate = findViewById(R.id.btn_calculate)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.tip_percentages,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipPercentage.adapter = adapter
        spTipPercentage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                selectedTipPercentage = getPercentageFromString(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        btnCalculate.setOnClickListener {
            calculateTip()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun calculateTip() {
        val totalAmountString = etTotalAmount.text.toString()

        if (totalAmountString.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el monto total.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val totalAmount = totalAmountString.toDouble()
            val tipAmount = totalAmount * selectedTipPercentage
            val totalWithTip = totalAmount + tipAmount
            val totalWithoutTip = totalAmount // El total sin la propina es simplemente el monto original

            // Mostrar los resultados
            tvTipAmount.text = String.format("Propina: $%.2f", tipAmount)
            tvTotalWithTip.text = String.format("Total con propina: $%.2f", totalWithTip)
            tvTotalWithoutTip.text = String.format("Total sin propina: $%.2f", totalWithoutTip) // Mostramos el total sin la propina
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Formato de monto inválido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPercentageFromString(percentageString: String): Double {
        val cleanString = percentageString.replace("%", "")
        return cleanString.toDouble() / 100
    }
}
