package com.example.myapplication.ui.theme

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

data class Product(val name: String, val price: String, val image: Int)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen() {
    val products = listOf(
        Product("Классические штаны", "$50", R.drawable.classicpants),
        Product("Куртка", "$120", R.drawable.jacket),
        Product("Туфли", "$80", R.drawable.shoes),
        Product("Кожаный пиджак", "$50", R.drawable.leatherjacket),
        Product("Юбка", "$120", R.drawable.skirt),
        Product("Кофта", "$80", R.drawable.sweather)
    )
    val cart = remember { mutableStateListOf<Product>() }
    var showCart by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Каталог одежды") }) },
        bottomBar = {
            if (!showCart) {
                BottomAppBar {
                    Button(
                        onClick = { showCart = true },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) { Text("Перейти в корзину (${cart.size})") }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            val filteredProducts = products.filter { it.name.contains(searchQuery, ignoreCase = true) }
            LazyColumn {
                items(filteredProducts) { product ->
                    ProductCard(product = product, onAddToCart = { cart.add(it) })
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: (Product) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(targetValue = if (expanded) 12.dp else 6.dp, label = "Elevation")
    val backgroundColor by animateColorAsState(
        targetValue = if (expanded) Color(0xFFE3F2FD) else Color.White,
        label = "Background"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
            .background(backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = product.price, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { onAddToCart(product) }) {
                Text("Купить")
            }
        }
    }
}

