package com.example.seton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val regions = listOf(
        "강원" to listOf("강릉시","고성군","동해시", "삼척시", "양구군", "양양군", "영월군", "원주시","인제군", "정선군", "철원군","춘천시"),
        "대전" to listOf("양구군", "양양군")
    )
    var selectedMainRegion by remember { mutableStateOf<String?>(null) }
    var selectedSubRegion by remember { mutableStateOf<String?>(null) }

    Column (
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,

        ){
        var sliderPosition by remember { mutableFloatStateOf(0f) }
        var landarea by rememberSaveable { mutableIntStateOf(10) }
        Text(
            text = "지역을 선택해주세요",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxSize()) {
            // 메인 리스트
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray)
            ) {
                items(regions.size) { index ->
                    val regionName = regions[index].first

                    Text(
                        text = regionName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { selectedMainRegion = regionName }
                            .background(if (selectedMainRegion == regionName) Color.Gray else Color.Transparent),
                        fontSize = 18.sp
                    )
                }
            }

            // 서브 리스트
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                selectedMainRegion?.let { region ->
                    val sublist = regions.firstOrNull { it.first == region }?.second ?: emptyList()
                    items(sublist.size) { index ->
                        Text(
                            text = sublist[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { selectedSubRegion = sublist[index] }
                                .background(if (selectedSubRegion == sublist[index]) Color.Gray else Color.Transparent),
                            fontSize = 16.sp
                        )
                    }
                }
            }
            Column (
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 16.dp)
            ){
                Text(text = "평수")
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    TextButton(onClick = { landarea -= 1 }) {
                        Text(text = "-")
                    }
                    Text(
                        text = "$landarea"
                    )
                    TextButton(onClick = {landarea += 1}) {
                        Text(text = "+")

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "가격")

                Slider(
                    value = sliderPosition,
                    onValueChange = {sliderPosition = it},
                    steps = 1000,
                    valueRange = 0f .. 999f
                )
                Text(
                    text = "${sliderPosition.toInt()} 만원",
                    modifier = Modifier.padding(top = 8.dp)
                )

            }


        }
    }

}