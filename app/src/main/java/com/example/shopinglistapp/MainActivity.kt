package com.example.shopinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shopinglistapp.repository.MainViewModel
import com.example.shopinglistapp.ui.theme.ShopingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopingListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()

                }
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = Utils(context)

    NavHost(navController, startDestination = "shoppinglistscreen"){
        composable("shoppinglistscreen") {
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController ,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }

        dialog("locationscreen"){backstack->
            viewModel.location.value?.let{it1 ->

                LocationSelectionScreen(location = it1, onLocationSelected = {locationdata->
                    viewModel.fetchAddress("${locationdata.latitude},${locationdata.longitude}")
                    navController.popBackStack()
                })
            }
        }
    }
}


/*@Composable
fun ShoppingListUi() {

    var shoppingItem by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }

    var inputItemName by remember { mutableStateOf("") }
    var inputItemQty by remember { mutableStateOf("1") }

    var id = 0

    shoppingItem = shoppingItem + ShoppingItem(0, "demo", 1)
    shoppingItem = shoppingItem + ShoppingItem(0, "demo", 1)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(shoppingItem) { item ->

                if (item.isEditing) {
                    ShoppingItemEditor(item = item) { editedName, editedQuantity ->
                        val itemWhichIsModifying = shoppingItem.find { it.id == item.id }
                        itemWhichIsModifying?.let {
                            it.itemName = editedName
                            it.itemQty = editedQuantity
                        }
                        shoppingItem = shoppingItem.map { it.copy(isEditing = false) }
                    }
                } else {
                    ShoppingListItemUi(item = item, onEditClicked = {
                        shoppingItem = shoppingItem.map { it.copy(isEditing = it.id == item.id) }
                    }) {
                        // Handle delete clicked
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {},
                title = {
                    Text(
                        text = "Add New Shopping Item",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column() {
                        OutlinedTextField(value = inputItemName,
                            onValueChange = { inputItemName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text(text = "Item Name") })

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = inputItemQty,
                            onValueChange = { inputItemQty = it },
                            label = { Text(text = "Qty") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = { showDialog = false }) {
                                Text(text = "Cancel")
                            }
                            Button(onClick = {
                                if (inputItemName.isNotBlank()) {
                                    val item =
                                        ShoppingItem(++id, inputItemName, inputItemQty.toInt())
                                    shoppingItem = shoppingItem + item

                                    inputItemName = ""
                                    inputItemQty = "1"
                                    showDialog = false
                                }
                            }) {
                                Text(text = "Add")
                            }
                        }


                    }
                })
        }

    }
}

@Composable
fun ShoppingListItemUi(
        item: ShoppingItem,
        onEditClicked: () -> Unit,
        onDeleteClicked: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .border(
                    width = 1.dp, color = Color(0xFF6650a4),
                    shape = RoundedCornerShape(20)
                )
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = item.itemName, modifier = Modifier.padding(2.dp))
                Text(text = item.itemQty.toString(), modifier = Modifier.padding(2.dp))

            }
            Spacer(modifier = Modifier.width(50.dp))
            Row {
                IconButton(onClick = { *//*TODO*/
/* }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }

                IconButton(onClick = { *//*TODO*/
/* }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }

    }

@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
        var editedName by remember { mutableStateOf(item.itemName) }
        var editedQuantity by remember { mutableStateOf(item.itemQty.toString()) }
        var isEditing by remember { mutableStateOf(item.isEditing) }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column {
                BasicTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    singleLine = true,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                )
                BasicTextField(
                    value = editedQuantity,
                    onValueChange = { editedQuantity = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                )
            }

            Button(onClick = {
                isEditing = false
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 0)
            }) {
                Text(text = "Save")
            }
        }

    }*/


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    ShopingListAppTheme {
//        ShoppingListApp()
        /*ShoppingListItemUi(
                item = ShoppingItem(1, "demo", 1),
                onEditClicked = { *//*TODO*//* },
                onDeleteClicked = {})
        }*/
    }
}

