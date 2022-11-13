package com.example.almin

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var layout: LinearLayout
    private lateinit var depositButton: Button
    private lateinit var withdrawalButton: Button
    private lateinit var depositField: EditText
    private lateinit var withdrawalField: EditText

    private lateinit var myRV: RecyclerView

    private lateinit var textLedger: ArrayList<String>
    private lateinit var numericLedger: ArrayList<Int>

    var currentBalanceNumber = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textLedger = ArrayList()
        numericLedger = ArrayList()

        myRV = findViewById(R.id.ledgerRV)
        myRV.adapter = RecyclerViewAdapter(textLedger)
        myRV.layoutManager = LinearLayoutManager(this)


        depositButton = findViewById(R.id.depositButton)
        depositField = findViewById(R.id.depositField)

        withdrawalButton = findViewById(R.id.withdrawalButton)
        withdrawalField = findViewById(R.id.withdrawalField)

        layout = findViewById(R.id.layout)

        depositButton.setOnClickListener { deposit() }
        withdrawalButton.setOnClickListener { withdrawal() }
    }

    //menu to clear the ledger

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clearLedger -> {
                var size = textLedger.size
                textLedger.clear()
                myRV.adapter?.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // will start adding functions here
    // Deposit function
    @SuppressLint("NotifyDataSetChanged")
    private fun deposit() {
        val depositedAmount = depositField.text.toString()
        if (depositedAmount.isNotEmpty()) {
            if (depositedAmount.toInt() > 0) {
                textLedger.add("Deposit: $depositedAmount")
                numericLedger.add(depositedAmount.toInt())
                scrollDown()
            } else {
                Toast.makeText(this, "Please Enter a valid amount", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You cannot deposit nothing!", Toast.LENGTH_LONG).show()
        }
        depositField.text.clear()
        depositField.clearFocus()
        myRV.adapter?.notifyDataSetChanged()
        var currentBalanceNumber = numericLedger.sum()
        currentBalance.text = "Current Balance: $currentBalanceNumber.00 SAR"
    }
    // Withdrawal function

    private fun withdrawal() {
        val withdrawnAmount = withdrawalField.text.toString()
        if (numericLedger.sum() > 0) {
            if (withdrawnAmount.toInt() > numericLedger.sum()) {
                textLedger.add("Withdrawal: $withdrawnAmount")
                textLedger.add("Over Draft Charge of 20.00SAR is added")
                scrollDown()
                numericLedger.add(((withdrawnAmount.toInt()) + 20) * -1)
                currentBalanceNumber = numericLedger.sum()
                currentBalance.setTextColor(ContextCompat.getColor(applicationContext, R.color.red))
                currentBalance.text = "Current Balance: $currentBalanceNumber.00 SAR"

            } else {
                textLedger.add("Withdrawal: $withdrawnAmount")
                scrollDown()
                numericLedger.add((withdrawnAmount.toInt()) * -1)
                currentBalanceNumber = numericLedger.sum()
                currentBalance.text = "Current Balance: $currentBalanceNumber.00 SAR"
            }
        } else {
            Toast.makeText(this, "Your balance is Zero or below", Toast.LENGTH_LONG).show()
        }
        depositField.text.clear()
        depositField.clearFocus()
        myRV.adapter?.notifyDataSetChanged()
    }

    //Scroll down function
    private fun scrollDown() { // To make Automatically scroll to the bottom of the Recycler View
        myRV.smoothScrollToPosition(textLedger.size - 1)
    }
}