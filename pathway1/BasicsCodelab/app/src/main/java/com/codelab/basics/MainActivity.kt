package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                TestApp()
            }
        }
    }
}

//상태 호이스팅 : 컴포저블 함수에서 여러 함수가 읽거나 수정하는 상태는 공통 상위 항목에 위치해야한다.
@Composable
fun OnBoardingScreen(onContinueCLicked: () -> Unit) {
    //by 키워드를 통해 .value를 기입할 필요가 없다.
    var shouldShowOnBoarding by rememberSaveable {
        mutableStateOf(true)
    }

    androidx.compose.material.Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "코드랩에 오신 걸 환영합니다!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { shouldShowOnBoarding = false }) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    //리컴포지션을 방지하므로 상태가 재설정되지 않는다.
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    //배경 색상 설정
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(onClick = {
                expanded = !expanded
            }) {
                Text(if (expanded) "Show less" else "Show more")
            }
//            OutlinedButton(onClick = {
//                expanded.value = !expanded.value
//            }) {
//                Text(if (expanded.value) "Show less" else "Show more")
//            }
        }
    }
}

@Composable
fun SurfacedColumn(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = "Hello, ")
            Text(text = name)
        }
    }
}

@Composable
fun TestColumn(titles: List<String> = listOf("World", "Compose", "Earth")) {
    Column {
        titles.forEach {
            Greeting(name = it)
        }
    }
}

@Composable
fun TestApp() {
    var shouldShowOnBoarding by remember {
        mutableStateOf(true)
    }

    if (shouldShowOnBoarding) {
        OnBoardingScreen(onContinueCLicked = {
            shouldShowOnBoarding = false
        })
    } else {
        TestColumn()
    }
}

@Composable
private fun LazyGreetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

//컴포즈 미리보기
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greeting("Lee Seung Yong")
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DARK"
)
@Preview()
@Composable
fun DarkPreview() {

}