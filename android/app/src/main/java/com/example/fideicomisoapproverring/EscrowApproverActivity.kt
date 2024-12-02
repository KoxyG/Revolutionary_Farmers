package com.example.fideicomisoapproverring



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import kotlinx.coroutines.launch
import org.stellar.walletsdk.Wallet
import org.stellar.walletsdk.StellarConfiguration
import android.widget.Toast

class EscrowApproverActivity : AppCompatActivity() {
    private lateinit var wallet: Wallet
    private lateinit var connectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escrow_approver)

        // Initialize Stellar Wallet
        wallet = Wallet(StellarConfiguration.Testnet)

        connectButton = findViewById(R.id.connectButton)
        setupWalletConnection()
    }

    private fun setupWalletConnection() {
        connectButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // Create a new key pair
                    val keyPair = wallet.stellar().account().createKeyPair()

                    // Store the connected state
                    val sharedPrefs = getSharedPreferences("WalletPrefs", MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean("isWalletConnected", true).apply()

                    Toast.makeText(this@EscrowApproverActivity,
                    "Wallet connected successfully",
                            Toast.LENGTH_SHORT).show()

                    // Return to MainActivity
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@EscrowApproverActivity,
                    "Connection failed: ${e.message}",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}