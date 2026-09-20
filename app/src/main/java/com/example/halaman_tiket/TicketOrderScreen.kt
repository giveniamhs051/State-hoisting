package com.example.pemesanantiket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning

private val Biru = Color(0xFF2E90FA)
private val Hijau = Color(0xFF12B76A)
private val Merah = Color(0xFFD92D20)
private val AbuMuda = Color(0xFFF5F7FA)
private val TeksGelap = Color(0xFF1D2939)

enum class OrderStatus { IDLE, EMPTY_NAME, PROCESSING, SUCCESS }

@Composable
fun TicketOrderScreen() {
    var hargaTiket by remember { mutableStateOf(25000) }
    var jumlahTiket by remember { mutableStateOf(1) }
    var namaPembeli by remember { mutableStateOf("") }

    var status by remember { mutableStateOf(OrderStatus.IDLE) }
    var orderTrigger by remember { mutableStateOf(0) }

    //LaunchedEffect: mengatur alur status pemesanan
    LaunchedEffect(orderTrigger) {
        if (orderTrigger == 0) return@LaunchedEffect

        if (namaPembeli.isBlank()) {
            status = OrderStatus.EMPTY_NAME
        } else {
            status = OrderStatus.PROCESSING
            delay(2000)
            status = OrderStatus.SUCCESS
        }
    }

    TicketOrderContent(
        hargaTiket = hargaTiket,
        jumlahTiket = jumlahTiket,
        onJumlahChange = { jumlahTiket = it },
        namaPembeli = namaPembeli,
        onNamaChange = { newValue ->
            namaPembeli = newValue
            if (status == OrderStatus.EMPTY_NAME) status = OrderStatus.IDLE
        },
        status = status,
        onPesanClick = { orderTrigger++ }
    )
}

@Composable
fun TicketOrderContent(
    hargaTiket: Int,
    jumlahTiket: Int,
    onJumlahChange: (Int) -> Unit,
    namaPembeli: String,
    onNamaChange: (String) -> Unit,
    status: OrderStatus,
    onPesanClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AbuMuda)
    ) {
        // HEADER BIRU
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Biru)
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ConfirmationNumber,
                contentDescription = "Ikon Tiket",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // CARD FORM
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    // Input Nama
                    Text(text = "Nama", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TeksGelap)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = namaPembeli,
                        onValueChange = onNamaChange,
                        placeholder = { Text("Masukkan nama Anda") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Jumlah Tiket
                    Text(text = "Jumlah Tiket", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TeksGelap)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BulatIconButton(
                            icon = Icons.Default.Remove,
                            onClick = { if (jumlahTiket > 1) onJumlahChange(jumlahTiket - 1) }
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(AbuMuda)
                                .padding(horizontal = 28.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$jumlahTiket", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        BulatIconButton(
                            icon = Icons.Default.Add,
                            onClick = { onJumlahChange(jumlahTiket + 1) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Ringkasan Harga
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Harga per tiket", fontSize = 13.sp, color = Color.Gray)
                        Text(text = formatRupiah(hargaTiket), fontSize = 13.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = formatRupiah(hargaTiket * jumlahTiket),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Biru
                        )
                    }
                }
            }

            // TOMBOL PESAN TIKET
            Button(
                onClick = onPesanClick,
                enabled = status != OrderStatus.PROCESSING,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Biru)
            ) {
                Text(text = "Pesan Tiket", fontWeight = FontWeight.Bold)
            }

            // STATUS
            StatusBox(status = status)
        }
    }
}

@Composable
fun StatusBox(status: OrderStatus) {
    val abuTua = Color(0xFF475467)

    val (bgColor, messageColor, message) = when (status) {
        OrderStatus.IDLE -> Triple(AbuMuda, abuTua, "Silakan pesan tiket")
        OrderStatus.EMPTY_NAME -> Triple(Color(0xFFFEF3F2), Merah, "Nama harus diisi")
        OrderStatus.PROCESSING -> Triple(Color(0xFFEFF8FF), Biru, "Memproses pesanan...")
        OrderStatus.SUCCESS -> Triple(Color(0xFFECFDF3), Hijau, "Tiket berhasil dipesan!")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (status) {
            OrderStatus.EMPTY_NAME -> Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Merah,
                modifier = Modifier.size(18.dp)
            )
            OrderStatus.PROCESSING -> CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = Biru
            )
            OrderStatus.SUCCESS -> Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Hijau,
                modifier = Modifier.size(18.dp)
            )
            OrderStatus.IDLE -> {}
        }

        if (status != OrderStatus.IDLE) {
            Spacer(modifier = Modifier.width(8.dp))
        }

        Row {
            Text(
                text = "Status: ",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = messageColor
            )
            Text(
                text = message,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = messageColor
            )
        }
    }
}

@Composable
fun BulatIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(AbuMuda)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = TeksGelap)
    }
}

private fun formatRupiah(jumlah: Int): String {
    val format = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return "Rp${format.format(jumlah)}"
}

@Preview(showBackground = true)
@Composable
fun PreviewTicketOrderScreen() {
    TicketOrderScreen()
}