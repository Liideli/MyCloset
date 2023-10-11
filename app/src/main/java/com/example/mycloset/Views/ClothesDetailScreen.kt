package com.example.mycloset.Views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mycloset.DatabaseWorkingset.ui.theme.MyClosetTheme

/*@Composable
fun clothesDetailScreen(
    navController: NavHostController,
    clothesViewModel:ClothesViewModel,
    barcode: String?
) {

    clothesViewModel.findClothesByBarcode(barcode!!)

   val selectedClothes = clothesViewModel.foundClothes.observeAsState().value;

        if (selectedClothes != null) {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.weight(1f))
                        Row() {
                            Button(
                                onClick = {
                                    /*homeViewModel.deleteEmployee(selectedEmployee)
                                navController.popBackStack()*/
                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Delete Employee",
                                    fontSize = 16.sp,
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "(${selectedClothes.brand})",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    /*navController.navigate(AppScreens.AddEditEmployeeScreen.route + "/" + selectedEmployee.employeeId + "/" + true)*/
                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(text = "Update Details", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
    */
