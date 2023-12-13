package com.example.propertypro.utilScreens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.propertypro.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel
) {

    val property= propertyViewModel.propertyDetails
    val pagerState = rememberPagerState(initialPage = 0)
    val ctx = LocalContext.current

    var imageSlider = remember { mutableSetOf<String>( ) }
    var imguri =  property["Images"]  as List<String>
    imageSlider.addAll(imguri)

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text ="Property Details",
                    modifier= Modifier.padding(horizontal = 5.dp),
                    color = Color.Black)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(imageVector =Icons.Filled.ArrowBack,
                        contentDescription =null)
                }
            })
        },
        ) {paddingValues ->
        Column(  modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
           // .padding(start = 8.dp)
            .verticalScroll(rememberScrollState()))
        {

            Column(modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)) {
                HorizontalPager(
                    count = imageSlider.size,
                    state = pagerState,
                    //contentPadding = PaddingValues(horizontal = 20.dp),
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                ) { page ->
                    Card(
                        shape = RectangleShape,
                        modifier = Modifier
                         //   .fillMaxWidth()
                            .graphicsLayer {
                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            }
                    ) {
                        AsyncImage(
                            model = imageSlider.toList()[page],
                            placeholder = painterResource(id = R.drawable.holderimage),
                            error = painterResource(id = R.drawable.holderimage),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale= ContentScale.FillWidth
                        )
                    }
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount=imageSlider.size,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }



            Text(
                text = "Property for " + property["Purpose"] + " in " + property["City"],
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))


//
//            sectionTitle("Key Information")
//            propertyDetailRow("Price", property["Price"].toString())
//            propertyDetailRow("Purpose", property["Purpose"].toString())
//            propertyDetailRow("Type", property["Type"].toString())
//            propertyDetailRow("Area Details", property["Area Details"].toString())
//            propertyDetailRow("Bed Room", property["Room"].toString())
//            propertyDetailRow("Bath Room", property["Bath Room"].toString())
//
//            propertyDetailRow("Floor Detail", property["Floor Detail"].toString())
//            propertyDetailRow("Furnishing Room", property["Furnishing Room"].toString())
//            propertyDetailRow("Open Parking", property["Open Parking"].toString())
//            propertyDetailRow("Covered Parking", property["Covered Parking"].toString())
//            propertyDetailRow("Balconies", property["Balconies"].toString())
//            propertyDetailRow("House.No/st name", property["Apartment"].toString())
//            propertyDetailRow("Locality", property["Locality"].toString())
//            propertyDetailRow("City", property["City"].toString())
//            propertyDetailRow("Contact", property["Contact"].toString())


            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Description",

                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 15.dp,),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = property["Description"].toString(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                modifier = Modifier.padding(start = 15.dp, end = 5.dp),
                color = Color.Black.copy(alpha = 0.8f),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Key Information",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Price",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Price"].toString(),
                    maxLines = 1,
                    color = Color.Red.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Type",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Type"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Area Details",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Area Details"].toString(),
                    maxLines = 2,
                    color = Color.Red.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Bed Room",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Room"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Bath Room",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Bath Room"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Other Room",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Other Room"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Area Details",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Area Details"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Floor Detail",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Floor Detail"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Furnishing Room",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Furnishing Room"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Open Parking",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Open Parking"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Covered Parking",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Covered Parking"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Balconies",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Balconies"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Address",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))

                if(property["Apartment"] != null&&  property["Locality"] != null){
                    Text(
                        text =""+property["Apartment"]+" \n"+ property["Locality"]+" \n"+property["City"].toString(),
                        maxLines = 3,
                        color = Color.Black.copy(alpha = 0.5f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }else{
                    Text(
                        text =property["City"].toString(),
                        maxLines = 3,
                        color = Color.Black.copy(alpha = 0.5f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Contact",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Contact"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Availability",
                    maxLines = 1,
                    color = Color.Black.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = property["Availability"].toString(),
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.5f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.`package` = "com.whatsapp"
                    intent.putExtra(Intent.EXTRA_TEXT, property.toString())
                    ctx.startActivity(intent)

                },
                modifier = Modifier
                    .padding(start = 25.dp, end = 40.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                enabled = true,
                shape = RectangleShape,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Green.copy(alpha = 0.6f),
                    contentColor = Color.White
                )
            )
            {
                Text(text = "Share Contact Via WhatsApp", color = Color.White)
            }

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            val u = Uri.parse("tel:" + property["Contact"].toString())
                            val i = Intent(Intent.ACTION_DIAL, u)
                            try {
                                ctx.startActivity(i)
                            } catch (s: SecurityException) {
                                Toast.makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .padding(start = 5.dp, end = 20.dp, bottom = 20.dp)
                            .fillMaxWidth(),
                        enabled = true,
                        shape = RectangleShape,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color(0xFF5A98EB),
                            contentColor = Color.White
                        )
                    )
                    {
                        Text(text = "Call Now", color = Color.White)
                    }
                }

        }
    }
}



@Composable
fun sectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 10.dp)
    )
}

@Composable
fun propertyDetailRow(key: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(1.dp, Color.Gray) // Add border to each row
    ) {
        Text(
            text = key,
            maxLines = 1,
            textAlign= TextAlign.Center,
            color = Color.Black.copy(alpha = 0.8f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )
        Text(
            text = if(!value.isNullOrEmpty()){
                value
               }else{
             "-"
               },
            maxLines = 2,
            textAlign= TextAlign.Center,
            color = Color.Black.copy(alpha = 0.5f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )
    }
}

