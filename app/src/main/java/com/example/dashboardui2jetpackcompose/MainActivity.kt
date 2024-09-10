package com.example.dashboardui2jetpackcompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.dashboardui2jetpackcompose.ui.theme.DashboardUI2JetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            DashboardUI2JetPackComposeTheme {
                MyUi()
            }
        }
    }
}

@Preview
@Composable
fun MyUi() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        bottomBar = {
            MyBottomBar()
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(state = rememberScrollState())
                .padding(paddingValues = it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchRow()
            Banner()
            Categories()
            PopularCourses()
            ItemList()
        }
    }
}

@Composable
fun MyBottomBar() {
    val bottomMenuItemList = PrepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Profile")
    }
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color(android.graphics.Color.parseColor("#f8f8f8")),
        elevation = 3.dp
    ) {
        bottomMenuItemList.forEachIndexed { index, bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = { selectedItem == bottomMenuItem.label },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                    )
                },
                label = {
                    Text(
                        text = bottomMenuItem.label,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                alwaysShowLabel = true,
                enabled = true,
            )
        }
    }
}

@Composable
fun PrepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Explorer",
            icon = painterResource(id = R.drawable.btn_1),
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "WishList",
            icon = painterResource(id = R.drawable.btn_2),
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Explorer",
            icon = painterResource(id = R.drawable.btn_3),
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "My Course",
            icon = painterResource(id = R.drawable.btn_4),
        )
    )


    return bottomMenuItemList
}

data class BottomMenuItem(
    val label: String,
    val icon: Painter,
)

data class Items(
    val title: String,
    val name: String,
    val price: Int,
    val score: Double,
    val picUrl: Int,
)

@Composable
fun ItemList() {
    val people: List<Items> = listOf(
        Items("Quick Learn C# Language", "Jammie young", 128, 4.6, R.drawable.pic1),
        Items("Full Course android Kotlin", "Alex Alba", 368, 4.2, R.drawable.pic2),
        Items("Full Course android Kotlin", "Alex Alba", 368, 4.2, R.drawable.pic2),
    )

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(people) { item: Items ->
            Column(modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .clickable {
                    println("Clicked on: ${item.name}")
                }) {
                ConstraintLayout(modifier = Modifier.height(IntrinsicSize.Max)) {
                    val (topImg, title, owner, ownerIcon, price, score, scoreIcon) = createRefs()
                    Image(
                        painter = painterResource(id = item.picUrl),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .constrainAs(topImg) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .background(Color(android.graphics.Color.parseColor("#90000000")))
                            .fillMaxWidth()
                            .padding(6.dp)
                            .constrainAs(title) {
                                bottom.linkTo(topImg.bottom)
                                start.linkTo(parent.start)
                            },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Image(painter = painterResource(id = R.drawable.profile),
                        contentDescription = "",
                        modifier = Modifier
                            .constrainAs(ownerIcon) {
                                start.linkTo(parent.start)
                                top.linkTo(topImg.bottom)
                            }
                            .padding(start = 16.dp, top = 16.dp))
                    Text(
                        text = "${item.name}",
                        modifier = Modifier
                            .constrainAs(owner) {
                                start.linkTo(ownerIcon.end)
                                top.linkTo(ownerIcon.top)
                                bottom.linkTo(ownerIcon.bottom)
                            }
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "$${item.price}",
                        fontWeight = FontWeight.Bold,
                        color = Color(android.graphics.Color.parseColor("#521c98")),
                        modifier = Modifier
                            .constrainAs(price) {
                                start.linkTo(ownerIcon.start)
                                top.linkTo(ownerIcon.bottom)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(start = 16.dp, top = 8.dp)
                    )
                    Text(
                        text = item.score.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .constrainAs(score) {
                                end.linkTo(parent.end)
                                top.linkTo(price.top)
                                bottom.linkTo(price.bottom)
                            }
                            .padding(end = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "",
                        modifier = Modifier
                            .constrainAs(scoreIcon) {
                                end.linkTo(score.start)
                                top.linkTo(score.top)
                                bottom.linkTo(score.bottom)
                            }
                            .padding(end = 8.dp)
                    )

                }

            }
        }

    }
}

@Composable
fun PopularCourses() {
    Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {

        Text(
            text = "Popular Courses",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "See all",
            fontWeight = FontWeight.SemiBold,
            color = Color(android.graphics.Color.parseColor("#521c98")),
            fontSize = 16.sp
        )
    }
}

@Composable
fun Categories() {
    Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {

        Text(
            text = "Category",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "See all",
            fontWeight = FontWeight.SemiBold,
            color = Color(android.graphics.Color.parseColor("#521c98")),
            fontSize = 16.sp
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = painterResource(id = R.drawable.cat1),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Business",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color(android.graphics.Color.parseColor("#521c98"))
            )
        }

        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = painterResource(id = R.drawable.cat2),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Creative",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color(android.graphics.Color.parseColor("#521c98"))
            )
        }

        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = painterResource(id = R.drawable.cat3),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Coding",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color(android.graphics.Color.parseColor("#521c98"))
            )
        }

        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = painterResource(id = R.drawable.cat4),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Gaming",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color(android.graphics.Color.parseColor("#521c98"))
            )
        }
    }
}

@Composable
fun Banner() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            .height(160.dp)
            .background(
                color = Color(android.graphics.Color.parseColor("#521c98")),
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        val (img, text, button) = createRefs()
        Image(painter = painterResource(id = R.drawable.girl2),
            contentDescription = "",
            modifier = Modifier.constrainAs(img) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)

            })
        Text(text = "Advanced Certification\n Program in AI",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(text = "Buy Now",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(android.graphics.Color.parseColor("#521c98")),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .constrainAs(button) {
                    top.linkTo(text.bottom)
                    bottom.linkTo(parent.bottom)

                }
                .background(
                    Color(android.graphics.Color.parseColor("#f0e9fa")),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp))
    }
}

@Composable
fun SearchRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Searching...", fontStyle = FontStyle.Italic) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "",
                    modifier = Modifier.size(23.dp),
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White, focusedLabelColor = Color.Transparent,
                // unfocusedLabelColor = Color.Transparent,
                textColor = Color(
                    android.graphics.Color.parseColor("#5e5e5e")
                ), unfocusedBorderColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    1.dp,
                    Color(android.graphics.Color.parseColor("#521c98")),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White, CircleShape)
        )
        Image(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "",
            modifier = Modifier.padding(16.dp),

            )
    }
}


