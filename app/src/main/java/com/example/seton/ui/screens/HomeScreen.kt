package com.example.seton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seton.R
import com.example.seton.widgets.BottomNavigation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
  Scaffold (
      bottomBar = {
          BottomNavigation(navController)
      }
  ) {
      val filterState = remember {
          mutableStateOf(false)
      }
      val sheetState = rememberModalBottomSheetState()
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(it),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
      ) {

          Box(
              modifier = Modifier
                  .fillMaxSize()
          ) {
              Image(
                  painter = painterResource(id = R.drawable.rural_house),
                  contentDescription = null,
                  modifier = Modifier.align(Alignment.TopCenter)
              )
              //Spacer(modifier = Modifier.padding(vertical = 16.dp))
              Column(
                  modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.TopStart)
                      .padding(top = 170.dp) //이미지와 텍스트 사이 벌리기
              ) {
                  Row {
                      Text(
                          modifier = Modifier.padding(8.dp),
                          textAlign = TextAlign.Left,
                          //fontweignt = ,
                          //fontsize =
                          text = "빈집털이"
                      )
                      Spacer(modifier = Modifier.padding(70.dp))
                      OutlinedButton(onClick = {
                          filterState.value = true
                      },
                          shape = RoundedCornerShape(10.dp)
                      ) {
                          Row {
                              Text("검색필터")
                              Icon(
                                  imageVector = Icons.Filled.ArrowDropDown,
                                  contentDescription = "",

                                  )
                          }

                      }

                  }

                  Column(
                      verticalArrangement = Arrangement.Center,
                      horizontalAlignment = Alignment.CenterHorizontally,

                      ) {

                  }
                  if (filterState.value) {
                      ModalBottomSheet(
                          onDismissRequest = {},
                          sheetState = sheetState
                      ) {
                          FilterDialog()
                      }
                  }
              }

          }



      }
  }
}

@Composable
fun FilterDialog() {

}