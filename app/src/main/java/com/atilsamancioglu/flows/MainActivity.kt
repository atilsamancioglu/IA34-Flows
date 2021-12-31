package com.atilsamancioglu.flows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atilsamancioglu.flows.ui.theme.FlowsTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlowsTheme {
                val viewModel : MyViewModel by viewModels()
                SecondScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FirstScreen(viewModel: MyViewModel) {
    val counter = viewModel.countDownTimerFlow.collectAsState(initial=10)

    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = counter.value.toString(),
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}

@Composable
fun SecondScreen(viewModel : MyViewModel) {

    val liveDataValue = viewModel.liveData.observeAsState()

    val stateFlowValue = viewModel.stateFlow.collectAsState()

    val sharedFlowValue = viewModel.sharedFlow.collectAsState(initial = "")

    Surface(color = MaterialTheme.colors.background) {
      Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(text = liveDataValue.value ?: "")
                Button(onClick = {
                    viewModel.changeLiveDataValue()
                }) {
                    Text(text = "LiveData Button")
                }
                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = stateFlowValue.value ?: "")
                Button(onClick = {
                    viewModel.changeStateFlowValue()
                }) {
                    Text(text = "State Flow Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = sharedFlowValue.value)
                Button(onClick = {
                    viewModel.changeSharedFlowValue()


                }) {
                    Text(text = "Shared Flow Button")
                }

            }
      }
    }
}

