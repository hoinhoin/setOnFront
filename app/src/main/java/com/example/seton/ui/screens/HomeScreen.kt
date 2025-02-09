package com.example.seton.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.seton.R
import com.example.seton.data.House
import com.example.seton.di.RetrofitInstance
import com.example.seton.presentation.housedetail.HouseDetailActivity
import com.example.seton.presentation.registration.RegistrationActivity
import com.example.seton.widgets.BottomNavigation
import com.google.android.material.chip.Chip

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
      val context = LocalContext.current
      val scope = rememberCoroutineScope()
      var houses by remember { mutableStateOf<List<House>>(emptyList()) }
      var isLoading by remember { mutableStateOf(true) }
      var error by remember { mutableStateOf<String?>(null) }
      val filterState = remember {
          mutableStateOf(false)
      }
      val sheetState = rememberModalBottomSheetState()

      // 데이터 로드 함수
      LaunchedEffect(key1 = true) {
          try {
              val response = RetrofitInstance.getApiService(context).getHouses()
              if (response.isSuccessful) {
                  houses = response.body() ?: emptyList()
              } else {
                  error = "데이터를 불러오는데 실패했습니다."
              }
          } catch (e: Exception) {
              error = e.localizedMessage
          } finally {
              isLoading = false
          }
      }


      //필터 조건 저장용 상태
      var selectedMainRegion by remember { mutableStateOf<String?>(null) }
      var selectedSubRegion by remember { mutableStateOf<String?>(null) }
      var selectedLandArea by remember { mutableIntStateOf(0) }
      var selectedPrice by remember { mutableIntStateOf(0) }

      /*
      // 필터링된 데이터
      val filteredData = sampleData.filter { item ->
          (selectedMainRegion == null || item.contains(selectedMainRegion!!)) &&
                  (selectedSubRegion == null || item.contains(selectedSubRegion!!)) &&
                  (item.contains("${selectedLandArea}평") || selectedLandArea == 0) &&
                  (item.contains("${selectedPrice}만원") || selectedPrice == 0)
      }*/

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
                  Row(
                      //modifier = Modifier.padding(),
                      verticalAlignment = Alignment.CenterVertically,

                      ) {
                      Spacer(modifier = Modifier.width(14.dp))
                      Image(painter = painterResource(id = R.drawable.group),
                          contentDescription = "")
                      Text(
                          modifier = Modifier.padding(8.dp),
                          textAlign = TextAlign.Left,
                          //fontweignt = ,
                          //fontsize =
                          text = "빈집털이"
                      )
                      Spacer(modifier = Modifier.width(90.dp))
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
                    /*
                  Column(
                      verticalArrangement = Arrangement.Bottom,
                      horizontalAlignment = Alignment.CenterHorizontally,

                      ){*/

                  //}
                  if (filterState.value) {
                      ModalBottomSheet(
                          onDismissRequest = {},
                          sheetState = sheetState
                      ) {
                          FilterDialog{ mainRegion, subRegion, landarea, price ->
                              selectedMainRegion = mainRegion
                              selectedSubRegion = subRegion
                              selectedLandArea = landarea
                              selectedPrice = price
                              filterState.value = false
                          }
                      }
                  }
                  Spacer(modifier = Modifier.height(16.dp))
                    /*
                  LazyColumn {
                      items(filteredData) { item ->
                          Text(
                              text = item,
                              modifier = Modifier.padding(8.dp)
                          )

                      }
                  }*/
                  when {
                      isLoading -> {
                          CircularProgressIndicator(
                              modifier = Modifier.align(Alignment.CenterHorizontally)
                          )
                      }
                      error != null -> {
                          Text(
                              text = error ?: "알 수 없는 오류가 발생했습니다.",
                              color = Color.Red,
                              modifier = Modifier.padding(16.dp)
                          )
                      }
                      else -> {
                          LazyColumn(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .weight(1f)
                          ) {
                              items(houses) { house ->
                                  HouseItem(
                                      house = house,
                                      onItemClick = { houseId ->
                                          val intent = Intent(context, HouseDetailActivity::class.java).apply {
                                              putExtra("houseId", houseId)
                                          }
                                          context.startActivity(intent)
                                      }
                                  )
                              }
                          }
                      }
                  }

              }
              OutlinedButton(
                  onClick = { 
                      // RegistrationActivity로 이동
                        val intent = Intent(context, RegistrationActivity::class.java)
                        startActivity(context, intent, null)
                  },
                  //shape = RoundedCornerShape(10.dp),
                  modifier = Modifier
                      .align(Alignment.BottomCenter)
                      .padding(bottom = 8.dp)
              ){
                  Text(text = "글쓰기")
              }

          }



      }
  }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HouseItem(house: House,
              onItemClick: (Long) -> Unit // 클릭 핸들러 추가
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(house.houseId)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 썸네일 이미지
            AsyncImage(
                model = house.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),

                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 제목
            Text(
                text = house.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // 주소
            Text(
                text = house.address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            // 가격과 평수
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${house.price}만원",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${house.size}평",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // 태그들
            FlowRow(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                house.tags.forEach { tag ->
                    AssistChip(
                        onClick = { },
                        label = { Text(tag) },
                        modifier = Modifier.padding(end = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun FilterDialog(
    onApplyFilter: (String?, String?, Int, Int) -> Unit //콜백추가
) {

    val regions = listOf(
        "강원" to listOf("강릉시","고성군","동해시", "삼척시", "양구군", "양양군", "영월군", "원주시","인제군", "정선군", "철원군","춘천시"),
        "대전" to listOf("대덕구", "동구", "서구", "유성구", "중구"),
        "세종" to listOf("세종시"),
        "충남" to listOf("계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군"),
        "충북" to listOf("괴산군"),
        "울산" to listOf("남구", "동구", "북구", "울주군", "중구"),
        "경남" to listOf("거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시","의령군", "진주시", "창년군", "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"),
        "경북" to listOf("경산시", "경주시", "고령군", "구미시", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"),
        "대구" to listOf("군위군", "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"),
        "광주" to listOf("광산구", "남구", "동구", "북구", "서구"),
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
                    valueRange = 100f .. 999f
                )
                Text(
                    text = "${sliderPosition.toInt()} 만원",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ){
                    OutlinedButton(
                        onClick = { onApplyFilter(selectedMainRegion, selectedSubRegion, landarea, sliderPosition.toInt())},
                        shape = RoundedCornerShape(10.dp),
                        //modifier = Modifier.align(Alignment.BottomCenter)
                    ){
                        Text(text = "적용하기")
                    }
                }


            }


        }
    }

}